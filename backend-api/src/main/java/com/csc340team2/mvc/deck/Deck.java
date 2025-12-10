package com.csc340team2.mvc.deck;

import com.csc340team2.mvc.account.Account;
import com.csc340team2.mvc.card.Card;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "deck")
@JsonIgnoreProperties(value = {"id"}, allowGetters = true)
public class Deck {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String scryfallUrl;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private Short colors;

    @Column(nullable = false)
    private String coverCardUUID;

    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    @JsonIgnoreProperties("decks")
    private Account account;

    @OneToMany(mappedBy = "deck", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Card> cards = new ArrayList<>();

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getScryfallUrl() { return scryfallUrl; }
    public void setScryfallUrl(String scryfallUrl) { this.scryfallUrl = scryfallUrl; }

    public String getNickname() { return nickname; }
    public void setNickname(String nickname) { this.nickname = nickname; }

    public Short getColors() { return colors; }
    public void setColors(Short colors) { this.colors = colors; }

    public String getCoverCardUUID() { return coverCardUUID; }
    public void setCoverCardUUID(String coverCardUUID) { this.coverCardUUID = coverCardUUID; }

    public Account getAccount() { return account; }
    public void setAccount(Account account) { this.account = account; }

    public List<Card> getCards() { return cards; }
    public void setCards(List<Card> cards) { this.cards = cards; }

    public void addCard(Card card) {
        card.setDeck(this);
        cards.add(card);
    }

    @JsonIgnore
    public String getColorsAsString() {
        short c = getColors();
        if (c == 0) return "";
        String s = ((c & 16) == 16 ? "White, " : "") +
                ((c & 8) == 8 ? "Blue, " : "") +
                ((c & 4) == 4 ? "Black, " : "") +
                ((c & 2) == 2 ? "Red, " : "") +
                ((c & 1) == 1 ? "Green, " : "");
        return s.substring(0, s.length() - 2);
    }
}
