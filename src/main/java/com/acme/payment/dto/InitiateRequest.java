package com.acme.payment.dto;

public class InitiateRequest {
    public Integer walletId;
    public java.util.List<Item> items;

    public static class Item {
        public Long productId;
        public Long merchantId;
        public Integer quantity;
        public String currency;
    }
}