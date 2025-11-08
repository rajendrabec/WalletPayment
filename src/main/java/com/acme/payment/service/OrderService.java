package com.acme.payment.service;

import com.acme.payment.domain.*;
import com.acme.payment.repository.*;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class OrderService {
    private final OrderHeaderRepository headers;
    private final OrderItemRepository items;

    public OrderService(OrderHeaderRepository h, OrderItemRepository i) {
        this.headers = h;
        this.items = i;
    }

    public OrderHeader create(OrderHeader h) {
        if (h.getFee() == null)
            h.setFee(java.math.BigDecimal.ZERO);
        return headers.save(h);
    }

    public Optional<OrderHeader> get(Long id) {
        return headers.findById(id);
    }

    public java.util.List<OrderItem> items(Long orderId) {
        return items.findByOrderId(orderId);
    }
}
