package com.acme.payment.dto;

import java.math.BigDecimal;

public class InitiateResponse {
    public Long orderId;
    public String status;
    public BigDecimal totalAmount;
    public BigDecimal fee;
    public String message;
}