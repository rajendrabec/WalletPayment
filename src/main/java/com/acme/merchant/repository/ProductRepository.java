package com.acme.merchant.repository;
import com.acme.merchant.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface ProductRepository extends JpaRepository<Product, Long> {
  List<Product> findByMerchantId(Long merchantId);
}
