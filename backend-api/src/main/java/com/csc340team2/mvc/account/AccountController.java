package com.csc340team2.mvc.account;

import com.csc340team2.mvc.session.Session;
import com.csc340team2.mvc.session.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AccountController {
    @Autowired
    private AccountService accountService;
    @Autowired
    private SessionService sessionService;
    @PostMapping("/registration")
    public ResponseEntity registerAccount(@RequestBody Account account){
        Account saved = accountService.addAccount(account);
        Session createdSession = sessionService.createSessionForAccount(account);
        return ResponseEntity.status(200).header("Set-Cookie", createdSession.getSetCookieHeader()).body(saved);
    }
    @GetMapping("/accounts/{id}")
    public ResponseEntity getAccount(@PathVariable Long id) {
        return ResponseEntity.ok(accountService.getAccountById(id));
    }
    @GetMapping("/accounts")
    public ResponseEntity getAccounts(){
        return ResponseEntity.ok(accountService.getAllAccounts());
    }
}
