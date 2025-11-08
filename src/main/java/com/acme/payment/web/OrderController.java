package com.acme.payment.web;

import com.acme.payment.domain.OrderHeader;
import com.acme.payment.domain.OrderItem;
import com.acme.payment.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {
    private final OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }

    @PostMapping
    public OrderHeader create(@RequestBody OrderHeader h) {
        return service.create(h);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderHeader> get(@PathVariable Long id) {
        return service.get(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{orderId}/items")
    public List<OrderItem> items(@PathVariable Long orderId) {
        return service.items(orderId);
    }
}
