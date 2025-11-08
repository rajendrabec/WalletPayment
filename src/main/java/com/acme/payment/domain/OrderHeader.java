package com.acme.payment.domain;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "`orders`")
@Getter
@Setter
public class OrderHeader {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "wallet_id", nullable = false)
    private Integer walletId;
    @Column(name = "totalamount", nullable = false)
    private BigDecimal totalAmount;
    @Column(nullable = false)
    private BigDecimal fee;
    @Column(nullable = false, length = 3)
    private String currency;
    @Column(nullable = false)
    private String status;
    @Column(name = "created_at")
    private Timestamp createdAt;
}