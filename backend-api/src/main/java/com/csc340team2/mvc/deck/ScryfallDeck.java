package com.csc340team2.mvc.deck;

import java.util.List;

public class ScryfallDeck {

    private String url;
    private List<ScryfallCard> cards;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<ScryfallCard> getCards() {
        return cards;
    }

    public void setCards(List<ScryfallCard> cards) {
        this.cards = cards;
    }
}
