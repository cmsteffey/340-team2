package com.csc340team2.mvc.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;
    public Optional<Account> getAccountForEmail(String email){
        return accountRepository.findByEmail(email);
    }
    public List<Account> getAllAccounts(){
        return accountRepository.getAllBy();
    }
    public Account addAccount(Account account){
        account.setId(null);
        account.setPassHash(""); //TODO: Add actual password hashing and check the password in SessionService!
        return accountRepository.save(account);
    }
    public Optional<Account> getAccountById(Long id){
        return accountRepository.findById(id);
    }
}
