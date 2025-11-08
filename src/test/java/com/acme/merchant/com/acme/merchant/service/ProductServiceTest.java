package com.acme.merchant.service;
import com.acme.merchant.domain.Product;
import com.acme.merchant.repository.ProductRepository;
import org.junit.jupiter.api.Test; import org.mockito.Mockito;
import java.util.Collections; import static org.junit.jupiter.api.Assertions.*;
public class ProductServiceTest {
  @Test public void emptyList(){
    ProductRepository repo = Mockito.mock(ProductRepository.class);
    Mockito.when(repo.findByMerchantId(99L)).thenReturn(Collections.emptyList());
    ProductService svc = new ProductService(repo);
    assertTrue(svc.byMerchant(99L).isEmpty());
  }
}
