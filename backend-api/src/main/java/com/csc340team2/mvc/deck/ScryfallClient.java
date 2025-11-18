package com.csc340team2.mvc.deck;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ScryfallClient {

    private final RestTemplate restTemplate;

    public ScryfallClient() {
        this.restTemplate = new RestTemplate();
    }

    public ScryfallDeck fetchDeck(String scryfallCode) {
        String url = "https://api.scryfall.com/decks/" + scryfallCode;
        return restTemplate.getForObject(url, ScryfallDeck.class);
    }

    public ScryfallCard fetchCard(String cardId) {
        String url = "https://api.scryfall.com/cards/" + cardId;
        return restTemplate.getForObject(url, ScryfallCard.class);
    }
}
