package com.csc340team2.mvc.deck;

import com.csc340team2.mvc.account.Account;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeckService {

    @Autowired
    private DeckRepository deckRepository;

    @Autowired
    private ScryfallClient scryfallClient;

    @Transactional
    public void importDeck(Account user, String scryfallCode, String name){
        ScryfallDeck deckData = scryfallClient.fetchDeck(scryfallCode);

        Deck newDeck = new Deck();
        newDeck.setAccount(user);
        newDeck.setNickname(name);
        newDeck.setScryfallUrl(deckData.getUrl());
        newDeck.setColors(parseColors(deckData.getCards()));
        newDeck.setCoverCardUUID(deckData.getCards().isEmpty() ? "" : deckData.getCards().get(0).getUuid());

        deckRepository.save(newDeck);
    }

    private Short parseColors(List<ScryfallCard> cards){
        short colors = 0;
        for(ScryfallCard card : cards){
            String ci = card.getColorIdentity();
            if(ci.contains("W")) colors |= 16;
            if(ci.contains("U")) colors |= 8;
            if(ci.contains("B")) colors |= 4;
            if(ci.contains("R")) colors |= 2;
            if(ci.contains("G")) colors |= 1;
        }
        return colors;
    }

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
        deck.setId(null);
        return deckRepository.save(deck);
    }
}
