package com.csc340team2.mvc.deck;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface DeckRepository extends JpaRepository<Deck, Long>{
    @Query("SELECT d FROM Deck d JOIN FETCH d.account account")
    public List<Deck> getAllWithUsers();
    public List<Deck> getAllBy();
    @Modifying
    public int deleteDecksById(Long id);
}
