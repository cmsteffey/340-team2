package com.csc340team2.mvc.deck;

import com.csc340team2.mvc.account.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface DeckRepository extends JpaRepository<Deck, Long> {
    @Query("SELECT d FROM Deck d JOIN FETCH d.account")
    List<Deck> getAllWithUsers();

    List<Deck> getAllByAccount(Account account);

    @Modifying
    int deleteDecksById(Long id);
}
