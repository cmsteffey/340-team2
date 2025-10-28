package com.csc340team2.mvc.account;

import com.csc340team2.mvc.deck.Deck;
import com.csc340team2.mvc.session.Session;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import org.apache.logging.log4j.util.Lazy;

import java.util.List;

@Entity
@Table(name = "account")
@JsonIgnoreProperties(value = {"id"}, allowSetters = true, allowGetters = true)
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String email;

    @JsonIgnore
    @Column(nullable = false)
    private String passHash;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private AccountRole role;

    @OneToMany(targetEntity = Deck.class, orphanRemoval = true,mappedBy = "account", cascade=CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnoreProperties("account")
    private List<Deck> decks;

    @OneToMany(targetEntity = Session.class, orphanRemoval = true, mappedBy = "account", fetch = FetchType.LAZY)
    @JsonIgnoreProperties("account")
    private List<Session> sessions;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassHash() {
        return passHash;
    }

    public void setPassHash(String passHash) {
        this.passHash = passHash;
    }

    public List<Deck> getDecks() {
        return decks;
    }

    public void setDecks(List<Deck> decks) {
        this.decks = decks;
    }

    public AccountRole getRole(){
        return role;
    }
    public void setRole(AccountRole role){
        this.role = role;
    }
}
