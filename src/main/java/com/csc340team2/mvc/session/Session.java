package com.csc340team2.mvc.session;

import com.csc340team2.mvc.user.Account;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "session", indexes = {
        @Index(name="idx_key", columnList = "key")
})
public class Session {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="key")
    private String key;

    @Column(name="started_at")
    private Date startedAt;

    @JsonIgnoreProperties("sessions")
    @JoinColumn(name="account_id")
    @ManyToOne(optional = false, targetEntity = Account.class, cascade=CascadeType.PERSIST)
    private Account account;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Date getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(Date startedAt) {
        this.startedAt = startedAt;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
