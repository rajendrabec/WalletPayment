package com.acme.merchant.service;
import com.acme.merchant.domain.Product;
import com.acme.merchant.repository.ProductRepository;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class ProductService {

    private final ProductRepository repo;

    // Constructor injection (recommended)
    public ProductService(ProductRepository repo){
        this.repo = repo;
    }

    // Save a new product or update existing one
    public Product create(Product p){
        return repo.save(p);
    }

    // Get all products for a specific merchant
    public List<Product> byMerchant(Long id){
        return repo.findByMerchantId(id);
    }
}
