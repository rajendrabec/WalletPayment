package com.acme.merchant.domain;
import javax.persistence.*; import lombok.Getter; import lombok.Setter;
@Entity @Table(name="merchants") @Getter @Setter
public class Merchant {
  @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
  @Column(nullable=false) private String name;
  @Column(nullable=false, unique=true) private String email;
  private String phone;
  private Boolean active;
}
