package com.acme.wallet.web;
import com.acme.wallet.domain.Wallet;
import com.acme.wallet.dto.WalletResponseDTO;
import com.acme.wallet.service.WalletService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController @RequestMapping("/api/v1/wallets")
public class WalletController {
  private final WalletService service;
  public WalletController(WalletService service){ this.service=service; }
  @GetMapping("/{customerId}") public WalletResponseDTO byCustomer(@PathVariable Long customerId){
    return service.byCustomer(customerId); }
  @PostMapping public Wallet create(@RequestBody Wallet w){ return service.create(w); }
}
