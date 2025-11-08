package com.acme.merchant.domain;
import javax.persistence.*; import lombok.Getter; import lombok.Setter;
import java.math.BigDecimal;
@Entity @Table(name="merchant_accounts") @Getter @Setter
public class MerchantAccount {
  @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
  @Column(name="merchant_id", nullable=false) private Long merchantId;
  @Column(name="account_number", nullable=false, unique=true) private String accountNumber;
  @Column(nullable=false, length=3) private String currency;
  @Column(nullable=false) private BigDecimal balance;
}
