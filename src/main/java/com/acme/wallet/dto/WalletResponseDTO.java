package com.acme.wallet.dto;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

public class WalletResponseDTO {
    String customerName;
    String currency;
    BigDecimal amount;
    private static final List<String> ALLOWEDCURRENCIES = Arrays.asList("INR", "USD", "JPY");
    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        if (!ALLOWEDCURRENCIES.contains(currency)) {
            throw new IllegalArgumentException("Invalid status: " + currency);
        }
        this.currency = currency;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "WalletResponse{" +
                "customerName='" + customerName + '\'' +
                ", currency='" + currency + '\'' +
                ", amount=" + amount +
                '}';
    }
}
