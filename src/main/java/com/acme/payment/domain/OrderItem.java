package com.acme.payment.domain;
import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "order_items")
public class OrderItem {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(name = "order_id", nullable = false)
  private Long orderId;
  @Column(name = "merchant_id", nullable = false)
  private Long merchantId;
  @Column(name = "product_id", nullable = false)
  private Long productId;// not strictly needed if column differs; but schema uses product_name
  @Column(name = "product_name")
  private String productName;
  @Column(nullable = false)
  private Integer quantity;
  @Column(name = "unit_price", nullable = false)
  private BigDecimal unitPrice;
  @Column(name = "total_price", nullable = false)
  private BigDecimal totalPrice;
  @Column(name = "created_at")
  private Timestamp createdAt;

}