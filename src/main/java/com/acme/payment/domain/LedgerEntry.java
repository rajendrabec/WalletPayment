package com.acme.payment.domain;
import javax.persistence.*; import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;
@Entity
@Table(name="ledger_entries")
@Getter
@Setter
public class LedgerEntry {
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  private Long id;

  @Column(name="order_id", nullable=false)
  private Long orderId;

  @Column(name="account_number")
  private Long accountNumber;
  @Column(name="account_type", nullable=false) private String accountType;
  @Column(name="dc", nullable=false, length=1) private String dc;
  @Column(name="amount", nullable=false) private BigDecimal amount;
}
