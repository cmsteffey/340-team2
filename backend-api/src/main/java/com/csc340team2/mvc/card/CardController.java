package com.csc340team2.mvc.card;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class CardController {

    @Autowired
    private CardService cardService;

    @GetMapping("/decks/{deckId}/cards")
    public ResponseEntity<List<Card>> getCardsInDeck(@PathVariable Long deckId) {
        List<Card> cards = cardService.getCardsByDeckId(deckId);
        return ResponseEntity.ok(cards);
    }

    @GetMapping("/cards/{id}")
    public ResponseEntity<Card> getCardById(@PathVariable Long id) {
        return cardService.getCardById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/cards/scryfall/{scryfallId}")
    public ResponseEntity<List<Card>> getCardByScryfallId(@PathVariable String scryfallId) {
        List<Card> cards = cardService.getCardsByScryfallId(scryfallId);
        return ResponseEntity.ok(cards);
    }

    @PostMapping("/decks/{deckId}/cards")
    public ResponseEntity<Card> addCardToDeck(@PathVariable Long deckId, @RequestBody Card card) {
        try {
            Card savedCard = cardService.addCardToDeck(deckId, card);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedCard);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PatchMapping("/cards/{id}")
    public ResponseEntity<Card> updateCardQuantity(
            @PathVariable Long id,
            @RequestBody Map<String, Integer> request
    ) {
        Integer quantity = request.get("quantity");
        if (quantity == null || quantity < 0) {
            return ResponseEntity.badRequest().build();
        }

        try {
            Card updatedCard = cardService.updateCardQuantity(id, quantity);
            return ResponseEntity.ok(updatedCard);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/cards/{id}")
    public ResponseEntity<Map<String, String>> deleteCard(@PathVariable Long id) {
        boolean deleted = cardService.deleteCardById(id);
        if (!deleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(Map.of("message", "Card deleted successfully"));
    }

    @GetMapping("/cards")
    public ResponseEntity<List<Card>> getAllCards() {
        return ResponseEntity.ok(cardService.getAllCards());
    }
}
