package com.acme.payment.repository;
import com.acme.payment.domain.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
  java.util.List<OrderItem> findByOrderId(Long orderId);
}
