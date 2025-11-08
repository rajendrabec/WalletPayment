package com.acme.payment.repository;
import com.acme.payment.domain.OrderHeader;
import org.springframework.data.jpa.repository.JpaRepository;
public interface OrderHeaderRepository extends JpaRepository<OrderHeader, Long> {}
