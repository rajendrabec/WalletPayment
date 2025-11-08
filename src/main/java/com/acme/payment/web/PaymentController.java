package com.acme.payment.web;

import com.acme.payment.dto.InitiateRequest;
import com.acme.payment.dto.InitiateResponse;
import com.acme.payment.service.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/payments")
public class PaymentController {
    private final PaymentService svc;

    public PaymentController(PaymentService s) {
        this.svc = s;
    }

    @PostMapping("/initiate")
    public ResponseEntity<InitiateResponse> initiate(@RequestBody InitiateRequest req) {
        return ResponseEntity.ok(svc.initiate(req));
    }
}