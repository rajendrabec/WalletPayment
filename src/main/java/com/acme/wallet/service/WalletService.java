package com.acme.wallet.service;
import com.acme.wallet.domain.Customer;
import com.acme.wallet.domain.Wallet;
import com.acme.wallet.domain.WalletResponse;
import com.acme.wallet.repository.CustomerRepository;
import com.acme.wallet.repository.WalletRepository;
import org.springframework.stereotype.Service;
import java.util.*;
@Service
public class WalletService {
  private final WalletRepository repo;
  private  CustomerRepository customerRepo;
  public WalletService(WalletRepository repo,CustomerRepository customerRepo  ){
    this.repo = repo;
    this.customerRepo = customerRepo;
  }
  public WalletResponse byCustomer(Long customerId){

    List<Wallet>  walletList = repo.findByCustomerId(customerId);
    Optional<Customer> customerList = customerRepo.findById(customerId);


    WalletResponse response = new WalletResponse();

    response.setCustomerName(customerList.get().getFullName());
    response.setCurrency(walletList.get(0).getCurrency());
    response.setAmount(walletList.get(0).getBalance());
    return response;
  }
  public Optional<Wallet> get(Long id){ return repo.findById(id); }
  public Wallet create(Wallet w){ if(w.getBalance()==null) w.setBalance(java.math.BigDecimal.ZERO); return repo.save(w); }
}
