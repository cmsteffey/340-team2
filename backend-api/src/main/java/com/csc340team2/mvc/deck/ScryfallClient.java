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

    public ScryfallCard searchCardByName(String cardName) {
        try {
            String encodedName = java.net.URLEncoder.encode(cardName, "UTF-8");
            String url = "https://api.scryfall.com/cards/named?fuzzy=" + encodedName;
            return restTemplate.getForObject(url, ScryfallCard.class);
        } catch (java.io.UnsupportedEncodingException e) {
            throw new RuntimeException("Failed to encode card name", e);
        }
    }

    public ScryfallCard fetchCardBySetAndNumber(String setCode, String collectorNumber) {
        String url = "https://api.scryfall.com/cards/" + setCode.toLowerCase() + "/" + collectorNumber;
        return restTemplate.getForObject(url, ScryfallCard.class);
    }

    public void respectRateLimit() {
        try {
            Thread.sleep(100); // 100ms delay
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}