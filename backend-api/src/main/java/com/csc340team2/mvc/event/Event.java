package com.csc340team2.mvc.event;

import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(name = "event")
public class Event {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

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

    @Column(nullable = false)
    private Long eventHostId;

    // Getters
    public long getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public LocalDateTime getStartTime() { return startTime; }
    public int getEventDuration() { return eventDuration; }
    public String getEventAddress() { return eventAddress; }
    public double getEventCost() { return eventCost; }
    public Long getEventHostId() { return eventHostId; }

    // Setters
    public void setId(long id) { this.id = id; }
    public void setTitle(String title) { this.title = title; }
    public void setDescription(String description) { this.description = description; }
    public void setStartTime(LocalDateTime startTime) { this.startTime = startTime; }
    public void setEventDuration(int eventDuration) { this.eventDuration = eventDuration; }
    public void setEventAddress(String eventAddress) { this.eventAddress = eventAddress; }
    public void setEventCost(double eventCost) { this.eventCost = eventCost; }
    public void setEventHostId(Long eventHostId) { this.eventHostId = eventHostId; }

}
