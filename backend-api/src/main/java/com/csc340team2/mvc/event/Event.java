package com.csc340team2.mvc.event;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.csc340team2.mvc.account.Account;
import com.csc340team2.mvc.eventSubscription.EventSubscription;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;

@Entity
@Table(name = "event")
public class Event {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // @OneToMany(targetEntity = EventSubscription.class)
    // @JoinColumn(name = "event_id")
    // @JsonIgnoreProperties("event")
    // private List<EventSubscription> eventSubscriptions = new ArrayList<EventSubscription>();

    @Column(nullable = false)
    private String title;

    @Column()
    private String description;

    @Column()
    private LocalDateTime startTime;

    @Column()
    private int eventDuration;

    @Column()
    private String eventAddress;

    @Column()
    private double eventCost;

    @ManyToOne(targetEntity = Account.class)
    @JoinColumn(name = "event_host_id", nullable = false)
    private Account eventHost;
    
    // Getters
    public long getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public LocalDateTime getStartTime() { return startTime; }
    public int getEventDuration() { return eventDuration; }
    public String getEventAddress() { return eventAddress; }
    public double getEventCost() { return eventCost; }
    public Account getEventHost() { return eventHost; }
    //public List<EventSubscription> getEventSubscriptions() { return eventSubscriptions; }

    // Setters
    public void setId(long id) { this.id = id; }
    public void setTitle(String title) { this.title = title; }
    public void setDescription(String description) { this.description = description; }
    public void setStartTime(LocalDateTime startTime) { this.startTime = startTime; }
    public void setEventDuration(int eventDuration) { this.eventDuration = eventDuration; }
    public void setEventAddress(String eventAddress) { this.eventAddress = eventAddress; }
    public void setEventCost(double eventCost) { this.eventCost = eventCost; }
    public void setEventHost(Account eventHost) { this.eventHost = eventHost; }
    //public void setEventSubscriptions(List<EventSubscription> eventSubscriptions) { this.eventSubscriptions = eventSubscriptions; }

}
