package com.csc340team2.mvc.postSubscription;

import com.csc340team2.mvc.account.Account;
import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "postsubscription")
public class PostSubscription
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long subscriptionId;

    @JoinColumn(nullable = false, name = "customer_id")
    @ManyToOne(targetEntity = Account.class)
    private Account user;

    @JoinColumn(nullable = false, name = "coach_id")
    @ManyToOne(targetEntity = Account.class)
    private Account coach;

    private Instant creationTime;

    public Account getCoach() { return coach; }
    public Account getUser() { return user; }
    public Instant getCreationTime() { return creationTime; }
    public void setCoach(Account newCoach) { coach = newCoach; }
    public void setUser(Account newUser) { user = newUser; }
    public void setCreationTime(Instant creationTime) { this.creationTime = creationTime; }
}
