package com.csc340team2.mvc.deck;

import com.csc340team2.mvc.account.Account;
import com.csc340team2.mvc.account.AccountService;
import com.csc340team2.mvc.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class DeckController {
    @Autowired
    private DeckService deckService;

    @Autowired
    private AccountService accountService;
    @PostMapping("/decks")
    public ResponseEntity addDeck(Session session, @RequestBody Deck deck){
        deck.setAccount(session.getAccount());
        return ResponseEntity.ok(deckService.addDeck(deck));
    }
    @DeleteMapping("/decks/{id}")
    public ResponseEntity deleteDeck(@PathVariable Long id){
        return deckService.deleteDeckById(id) ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }
    @GetMapping("/decks")
    public ResponseEntity getDecks(){
        return ResponseEntity.ok(deckService.getAllDecksWithUsers());
    }
    @GetMapping("/decks/user/{id}")
    public ResponseEntity getDecksForUser(@PathVariable Long id){
        Optional<Account> userInDb = accountService.getAccountById(id);
        Account account = userInDb.orElse(null);
        if(account == null){
            return ResponseEntity.status(HttpStatusCode.valueOf(404)).header("Content-Type", "text/plain").body("User not found");
        }
        return ResponseEntity.ok(account.getDecks());
    }
}
