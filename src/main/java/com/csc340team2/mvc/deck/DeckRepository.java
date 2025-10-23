package com.csc340team2.mvc.deck;

import com.csc340team2.mvc.user.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface DeckRepository extends JpaRepository<Deck, Long>{
    @Query("SELECT d FROM Deck d JOIN FETCH d.account account")
    public List<Deck> getAllWithUsers();
    public List<Deck> getAllBy();
    public List<Deck> getAllByAccount(Account account);
    @Modifying
    public int deleteDecksById(Long id);
}
