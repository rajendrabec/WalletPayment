package com.acme.merchant.web;
import com.acme.merchant.domain.Product;
import com.acme.merchant.service.ProductService;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController @RequestMapping("/api/v1/products")
public class ProductController {
  private final ProductService service;
  public ProductController(ProductService service){ this.service=service; }
  @PostMapping public Product create(@RequestBody Product p){ return service.create(p); }
  @GetMapping("/by-merchant/{merchantId}") public List<Product> byMerchant(@PathVariable Long merchantId){ return service.byMerchant(merchantId); }
}
