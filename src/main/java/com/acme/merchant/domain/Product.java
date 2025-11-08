package com.acme.merchant.domain;
import javax.persistence.*; import java.math.BigDecimal; import lombok.Getter; import lombok.Setter;
@Entity @Table(name="products") @Getter @Setter
public class Product {
  @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
  @Column(name="merchant_id", nullable=false) private Long merchantId;
  @Column(name="product_name", nullable=false) private String productName;
  private String description;
  @Column(nullable=false) private BigDecimal price;
  @Column(nullable=false, length=3) private String currency;
  @Column(name="stock_quantity", nullable=false) private Integer stockQuantity;
  private Boolean active;
}
