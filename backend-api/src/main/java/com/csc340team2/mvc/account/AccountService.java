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
        return accountRepository.save(account);
    }
    public Optional<Account> getAccountById(Long id){
        return accountRepository.findById(id);
    }
}
