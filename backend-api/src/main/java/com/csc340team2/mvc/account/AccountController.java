package com.csc340team2.mvc.account;

import com.csc340team2.mvc.session.Session;
import com.csc340team2.mvc.session.SessionService;
import org.slf4j.LoggerFactory;
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
    public ResponseEntity registerAccount(@ModelAttribute Account account){
        if(account.getRole() == null){
            return ResponseEntity.badRequest().build();
        }
        Account saved = accountService.addAccount(account);
        Session createdSession = sessionService.createSessionForAccount(saved);
        return ResponseEntity.status(303).header("Set-Cookie", createdSession.getSetCookieHeader()).header("Location", "/").build();
    }
    @GetMapping("/account/myAccount")
    public ResponseEntity getMyAccount(Session session){
        return ResponseEntity.ok(session.getAccount());
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
