package com.csc340team2.mvc.eventSubscription;

import java.time.LocalDateTime;

import com.csc340team2.mvc.session.Session;

import jakarta.persistence.*;

@Entity
@Table(name = "eventSubscription")
public class EventSubscription {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int eventId;

    @ManyToOne(targetEntity = Session.class)
    @JoinColumn(name = "accountId", nullable = false)
    private int accountId;

    @Column()
    private LocalDateTime createdTime;

    // Getters
    public long getId() { return id; }
    public int getEventId() { return eventId; }
    public int getAccountId() { return eventId; }
    public LocalDateTime getCreatedTime() { return createdTime; }

    // Setters
    public void setId(long id) { this.id = id; }
    public void setEventId(int eventId) { this.eventId = eventId; }
    public void setAccountId(int accountId) { this.accountId = accountId; }
    public void setCreatedTime(LocalDateTime createdTime) { this.createdTime = createdTime; }


}
