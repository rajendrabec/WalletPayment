package com.acme.payment.service;

import com.acme.payment.domain.LedgerEntry;
import com.acme.payment.domain.MerchantOrder;
import com.acme.payment.domain.OrderHeader;
import com.acme.payment.domain.OrderItem;
import com.acme.payment.dto.InitiateRequest;
import com.acme.payment.dto.InitiateResponse;
import com.acme.payment.repository.LedgerRepository;
import com.acme.payment.repository.MerchantOrderRepository;
import com.acme.payment.repository.OrderHeaderRepository;
import com.acme.payment.repository.OrderItemRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
public class PaymentService {
    private final OrderHeaderRepository headers;
    private final OrderItemRepository items;
    private final LedgerRepository ledgers;
    private final MerchantOrderRepository merchantOrders;
    private final RestTemplate rest;
    @Value("${svc.wallet.url}")
    private String walletSvc;
    @Value("${svc.merchant.url}")
    private String merchantSvc;
    @Value("${svc.notify.url}")
    private String notifySvc;

    public PaymentService(OrderHeaderRepository h, OrderItemRepository i, LedgerRepository l, MerchantOrderRepository m, RestTemplate r) {
        this.headers = h;
        this.items = i;
        this.ledgers = l;
        this.merchantOrders = m;
        this.rest = r;
    }

    private boolean isSupportedCurrency(String c) {
        return Arrays.asList("INR", "USD", "EUR").contains(c != null ? c.toUpperCase() : "");
    }

    @Transactional
    public InitiateResponse initiate(InitiateRequest req) {
        BigDecimal total = BigDecimal.ZERO;
        for (InitiateRequest.Item it : req.items) {
            if (!isSupportedCurrency(it.currency))
                throw new IllegalArgumentException("Unsupported currency: " + it.currency);
            ResponseEntity<Map> resp = rest.getForEntity(merchantSvc + "/api/v1/merchants/products/" + it.productId, Map.class);
            if (!resp.getStatusCode().is2xxSuccessful() || resp.getBody() == null)
                throw new IllegalArgumentException("Product not found: " + it.productId);
            BigDecimal unit = new BigDecimal(String.valueOf(resp.getBody().get("price")));
            if (!String.valueOf(resp.getBody().get("currency")).equalsIgnoreCase(it.currency))
                throw new IllegalArgumentException("Currency mismatch for product " + it.productId);
            total = total.add(unit.multiply(new BigDecimal(it.quantity)));
        }
        BigDecimal fee = total.multiply(new BigDecimal("0.01")).setScale(2, RoundingMode.HALF_UP);
        OrderHeader oh = new OrderHeader();
        oh.setWalletId(req.walletId);
        oh.setTotalAmount(total);
        oh.setFee(fee);
        oh.setCurrency(req.items.get(0).currency);
        oh.setStatus("INITIATED");
        headers.save(oh);

        ResponseEntity<String> dResp = rest.postForEntity(walletSvc + "/api/v1/wallets/" + req.walletId + "/deduct?amount=" + total + "&currency=" + oh.getCurrency(), null, String.class);
        if (!dResp.getStatusCode().is2xxSuccessful()) {
            oh.setStatus("FAILED");
            headers.save(oh);
            throw new IllegalStateException("Insufficient wallet balance or currency mismatch");
        }

        for (InitiateRequest.Item it : req.items) {
            Map body = rest.getForObject(merchantSvc + "/api/v1/merchants/products/" + it.productId, Map.class);
            BigDecimal unit = new BigDecimal(String.valueOf(body.get("price")));
            BigDecimal lineTotal = unit.multiply(new BigDecimal(it.quantity));
            BigDecimal lineFee = fee.multiply(lineTotal).divide(total, 2, RoundingMode.HALF_UP);
            BigDecimal net = lineTotal.subtract(lineFee);

            OrderItem oi = new OrderItem();
            oi.setOrderId(oh.getId());
            oi.setMerchantId(it.merchantId);
            oi.setProductId(it.productId);
            oi.setProductName(String.valueOf(body.get("productName")));
            oi.setQuantity(it.quantity);
            oi.setUnitPrice(unit);
            oi.setTotalPrice(lineTotal);
            items.save(oi);

            ResponseEntity<String> cResp = rest.postForEntity(merchantSvc + "/api/v1/merchants/" + it.merchantId + "/credit?amount=" + net + "&currency=" + it.currency, null, String.class);
            if (!cResp.getStatusCode().is2xxSuccessful()) {
                try {
                    rest.postForEntity(walletSvc + "/api/v1/wallets/" + req.walletId + "/refund?amount=" + total, null, String.class);
                } catch (Exception ignore) {
                }
                oh.setStatus("FAILED");
                headers.save(oh);
                throw new IllegalStateException("Merchant credit failed; refunded wallet.");
            }

            MerchantOrder mo = new MerchantOrder();
            mo.setOrderId(oh.getId());
            mo.setMerchantId(it.merchantId);
            mo.setTotalAmount(lineTotal);
            mo.setCurrency(it.currency);
            mo.setStatus("CAPTURED");
            merchantOrders.save(mo);

            LedgerEntry l1 = new LedgerEntry();
            l1.setOrderId(oh.getId());
            l1.setAccountType("WALLET");
            l1.setDc("D");
            l1.setAmount(lineTotal);
            LedgerEntry l2 = new LedgerEntry();
            l2.setOrderId(oh.getId());
            l2.setAccountType("MERCHANT");
            l2.setDc("C");
            l2.setAmount(net);
            LedgerEntry l3 = new LedgerEntry();
            l3.setOrderId(oh.getId());
            l3.setAccountType("FEE");
            l3.setDc("C");
            l3.setAmount(lineFee);
            ledgers.save(l1);
            ledgers.save(l2);
            ledgers.save(l3);
        }
        oh.setStatus("SUCCESS");
        headers.save(oh);
        try {
            Map<String, String> msg = new HashMap<>();
            msg.put("email", "merchant@example.com");
            msg.put("subject", "Payment Successful");
            msg.put("body", "Order " + oh.getId() + " paid and credited.");
            rest.postForEntity(notifySvc + "/api/v1/notifications", msg, String.class);
        } catch (Exception ignore) {
        }
        InitiateResponse out = new InitiateResponse();
        out.orderId = oh.getId();
        out.status = oh.getStatus();
        out.totalAmount = total;
        out.fee = fee;
        out.message = "Payment successful";
        return out;
    }
}
