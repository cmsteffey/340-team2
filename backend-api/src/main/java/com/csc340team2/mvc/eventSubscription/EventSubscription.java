package com.csc340team2.mvc.eventSubscription;

import java.time.Instant;

import com.csc340team2.mvc.account.Account;
import com.csc340team2.mvc.event.Event;

import jakarta.persistence.*;

@Entity
@Table(name = "event_subscription")
public class EventSubscription {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(targetEntity = Account.class)
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    @ManyToOne(targetEntity = Event.class)
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

    @Column(nullable = false)
    private Instant createdTime;

    // Getters
    public long getId() { return id; }
    public Account getAccount() { return account; }
    public Instant getCreatedTime() { return createdTime; }
    public Event getEvent() { return event; }

    // Setters
    public void setId(long id) { this.id = id; }
    public void setAccount(Account account) { this.account = account; }
    public void setCreatedTime(Instant createdTime) { this.createdTime = createdTime; }
    public void setEvent(Event event) { this.event = event; }

}
