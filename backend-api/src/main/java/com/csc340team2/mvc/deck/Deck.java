package com.csc340team2.mvc.deck;
import com.csc340team2.mvc.account.Account;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

@Entity
@Table(name = "deck")

@JsonIgnoreProperties(value = {"id"}, allowSetters = false, allowGetters = true)
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
    /*
    * White: 16s bit
    * Blue: 8s bit
    * Black: 4s bit
    * Red: 2s bit
    * Green: 1s bit
    */
    @Column(nullable = false)
    private String coverCardUUID;

    @JsonProperty("account")
    @ManyToOne(targetEntity = Account.class)
    @JoinColumn(name= "account_id", nullable = false)
    @JsonIgnoreProperties("decks")
    private Account account;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getScryfallUrl() {
        return scryfallUrl;
    }

    public void setScryfallUrl(String scryfallUrl) {
        this.scryfallUrl = scryfallUrl;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Short getColors() {
        return colors;
    }

    public void setColors(Short colors) {
        this.colors = colors;
    }

    public String getCoverCardUUID() {
        return coverCardUUID;
    }

    public void setCoverCardUUID(String coverCardUUID) {
        this.coverCardUUID = coverCardUUID;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    @JsonIgnore
    public String getColorsAsString() {
        short colors = getColors();
        if (colors == 0) return "";
        String string = (((colors & 16) == 16 ? "White, " : "") +
                ((colors & 8) == 8 ? "Blue, " : "") +
                ((colors & 4) == 4 ? "Black, " : "") +
                ((colors & 2) == 2 ? "Red, " : "") +
                ((colors & 1) == 1 ? "Green, " : ""));
        return string.substring(0, string.length() - 2);
    }
}
