package com.acme.wallet.domain;
import javax.persistence.*; import java.math.BigDecimal;
import lombok.Getter; import lombok.Setter;
@Entity @Table(name="wallets") @Getter @Setter
public class Wallet {
  @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
  @Column(nullable=false, length=3) private String currency;
  @Column(nullable=false) private BigDecimal balance;
  @Column(name="customer_id") private Long customerId;
}
