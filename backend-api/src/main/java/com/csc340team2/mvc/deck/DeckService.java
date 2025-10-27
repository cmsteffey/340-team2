package com.csc340team2.mvc.deck;

import com.csc340team2.mvc.account.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeckService {
    @Autowired
    private DeckRepository deckRepository;

    /*@Query("SELECT d FROM Deck d JOIN FETCH d.account account")
    public List<Deck> getAllWithUsers();
    public List<Deck> getAllBy();
    public List<Deck> getAllByAccount(Account account);
    @Modifying
    public int deleteDecksById(Long id);
    * */

    public List<Deck> getAllDecksWithUsers(){
        return deckRepository.getAllWithUsers();
    }

    public List<Deck> getAllDecksWithoutUsers(){
        return deckRepository.getAllBy();
    }

    public List<Deck> getAllOwnedBy(Account account){
        return deckRepository.getAllByAccount(account);
    }

    public boolean deleteDeckById(Long id){
        return deckRepository.deleteDecksById(id) == 1;
    }

    public Deck addDeck(Deck deck){
        return deckRepository.save(deck);
    }
}
