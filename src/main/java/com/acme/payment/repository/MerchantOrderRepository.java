package com.acme.payment.repository;

import com.acme.payment.domain.MerchantOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MerchantOrderRepository extends JpaRepository<MerchantOrder, Long> {
}