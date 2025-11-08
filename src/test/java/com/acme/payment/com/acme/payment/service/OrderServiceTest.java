package com.acme.payment.service;
import com.acme.payment.domain.OrderHeader;
import com.acme.payment.repository.OrderHeaderRepository;
import com.acme.payment.repository.OrderItemRepository;
import org.junit.jupiter.api.Test; import org.mockito.Mockito; import static org.junit.jupiter.api.Assertions.*;
public class OrderServiceTest {
  @Test public void defaultFeeApplied(){
    OrderHeaderRepository h = Mockito.mock(OrderHeaderRepository.class);
    OrderItemRepository i = Mockito.mock(OrderItemRepository.class);
    Mockito.when(h.save(Mockito.any(OrderHeader.class))).thenAnswer(a -> a.getArgument(0));
    OrderService svc = new OrderService(h,i);
    OrderHeader oh = new OrderHeader(); oh.setCurrency("INR"); oh.setStatus("INITIATED"); oh.setWalletId(1); oh.setTotalAmount(new java.math.BigDecimal("99.99"));
    OrderHeader saved = svc.create(oh); assertNotNull(saved.getFee());
  }
}
