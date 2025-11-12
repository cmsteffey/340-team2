package com.csc340team2.mvc.appointment;

import com.csc340team2.mvc.account.Account;
import jakarta.persistence.*;

import java.time.Instant;
import java.util.Date;

@Entity
@Table(name = "appointment")
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Instant time;

    @Column(nullable = false)
    private int length;

    @JoinColumn(name = "customer_id", nullable = false)
    @ManyToOne(targetEntity = Account.class)
    private Account customer;

    @JoinColumn(name = "coach_id", nullable = false)
    @ManyToOne(targetEntity = Account.class)
    private Account coach;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getTime() {
        return time;
    }

    public void setTime(Instant time) {
        this.time = time;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public Account getCustomer() {
        return customer;
    }

    public void setCustomer(Account customer) {
        this.customer = customer;
    }

    public Account getCoach() {
        return coach;
    }

    public void setCoach(Account coach) {
        this.coach = coach;
    }
}
