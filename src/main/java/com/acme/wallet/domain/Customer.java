package com.acme.wallet.domain;
import javax.persistence.*;
import lombok.Getter; import lombok.Setter;
@Entity @Table(name="customers") @Getter @Setter
public class Customer {
  @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
  @Column(name="full_name", nullable=false) private String fullName;
  @Column(nullable=false, unique=true) private String email;
  @Column(name="phone_number") private String phoneNumber;
}
