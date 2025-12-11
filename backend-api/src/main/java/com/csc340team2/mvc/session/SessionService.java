package com.csc340team2.mvc.session;

import com.csc340team2.mvc.account.Account;
import com.csc340team2.mvc.account.AccountRepository;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
        if(!new BCryptPasswordEncoder().matches(password, account.orElseThrow().getPassHash()))
            return Optional.empty();
        Session session = new Session();
        session.setAccount(account.orElseThrow());
        session.setKey(generateSessionKey());
        return Optional.of(sessionRepository.save(session));
    }
    public Session createSessionForAccount(Account account){
        Session session = new Session();
        session.setAccount(account);
        session.setKey(generateSessionKey());
        return sessionRepository.save(session);
    }
    public Optional<Session> getSessionByKey(String key){
        return sessionRepository.findSessionByKey(key);
    }

    private String generateSessionKey(){
        return UUID.randomUUID().toString();
    }
}
