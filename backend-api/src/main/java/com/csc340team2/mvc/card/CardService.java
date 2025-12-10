package com.csc340team2.mvc.card;

import com.csc340team2.mvc.deck.Deck;
import com.csc340team2.mvc.deck.DeckRepository;
import com.csc340team2.mvc.card.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CardService {

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private DeckRepository deckRepository;

    public List<Card> getCardsByDeckId(Long deckId) {
        return cardRepository.findByDeckId(deckId);
    }

    public Optional<Card> getCardById(Long id) {
        return cardRepository.findById(id);
    }

    public List<Card> getCardsByScryfallId(String scryfallId) {
        return cardRepository.findByScryfallId(scryfallId);
    }

    public Card addCardToDeck(Long deckId, Card card) {
        Deck deck = deckRepository.findById(deckId)
                .orElseThrow(() -> new IllegalArgumentException("Deck not found"));

        card.setDeck(deck);
        Card savedCard = cardRepository.save(card);

        if (deck.getCards() != null) {
            deck.getCards().add(savedCard);
        }
        return savedCard;
    }

    public Card updateCardQuantity(Long id, Integer quantity) {
        Card card = cardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Card not found"));

        card.setQuantity(quantity);
        return cardRepository.save(card);
    }

    public boolean deleteCardById(Long id) {
        if (!cardRepository.existsById(id)) {
            return false;
        }
        cardRepository.deleteById(id);
        return true;
    }

    public List<Card> getAllCards() {
        return cardRepository.findAll();
    }
}
