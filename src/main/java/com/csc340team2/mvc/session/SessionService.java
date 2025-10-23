package com.csc340team2.mvc.session;

import com.csc340team2.mvc.user.Account;
import com.csc340team2.mvc.user.AccountRepository;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

@Service
public class SessionService {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private SessionRepository sessionRepository;

    public Optional<Session> authenticateAndCreateSession(String email, String password){
        Optional<Account> account = accountRepository.findByEmail(email);
        if(account.isEmpty())
            LoggerFactory.getLogger(SessionService.class).error("Account not found for email " + email);
        if(account.isEmpty())
            return Optional.empty();
        Session session = new Session();
        session.setAccount(account.orElseThrow());
        session.setKey(UUID.randomUUID().toString());
        return Optional.of(sessionRepository.save(session));
    }
    public Optional<Session> getSessionByKey(String key){
        return sessionRepository.findSessionByKey(key);
    }
}
