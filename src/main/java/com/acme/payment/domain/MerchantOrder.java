package com.acme.payment.domain;
import javax.persistence.*; import java.math.BigDecimal;
import lombok.Getter; import lombok.Setter;
@Entity @Table(name="merchant_orders") @Getter @Setter
public class MerchantOrder {
  @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
  @Column(name="order_id", nullable=false) private Long orderId;
  @Column(name="merchant_id", nullable=false) private Long merchantId;
  @Column(name="merchant_account_id") private Long merchantAccountId;
  @Column(name="total_amount", nullable=false) private BigDecimal totalAmount;
  @Column(nullable=false, length=3) private String currency;
  @Column(nullable=false) private String status;
}
