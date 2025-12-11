package com.csc340team2.mvc.account;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

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
        account.setPassHash(new BCryptPasswordEncoder().encode(account.getPassHash()));
        LoggerFactory.getLogger(AccountService.class).error(account.getPassHash());
        return accountRepository.save(account);
    }
    public Optional<Account> getAccountById(Long id){
        return accountRepository.findById(id);
    }
}
