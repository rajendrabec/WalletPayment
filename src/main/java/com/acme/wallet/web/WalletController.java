package com.acme.wallet.web;
import com.acme.wallet.domain.Wallet;
import com.acme.wallet.domain.WalletResponse;
import com.acme.wallet.service.WalletService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController @RequestMapping("/api/v1/wallets")
public class WalletController {
  private final WalletService service;
  public WalletController(WalletService service){ this.service=service; }
  @GetMapping("/by-customer/{customerId}") public WalletResponse byCustomer(@PathVariable Long customerId){
    return service.byCustomer(customerId); }
  @GetMapping("/{id}") public ResponseEntity<Wallet> get(@PathVariable Long id){ return service.get(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build()); }
  @PostMapping public Wallet create(@RequestBody Wallet w){ return service.create(w); }
}
