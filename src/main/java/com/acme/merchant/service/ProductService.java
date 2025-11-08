package com.acme.merchant.service;
import com.acme.merchant.domain.Product;
import com.acme.merchant.repository.ProductRepository;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class ProductService {
  private final ProductRepository repo;
  public ProductService(ProductRepository repo){ this.repo=repo; }
  public Product create(Product p){ return repo.save(p); }
  public List<Product> byMerchant(Long id){ return repo.findByMerchantId(id); }
}
