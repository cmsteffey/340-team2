package com.csc340team2.mvc.appointment;

import com.csc340team2.mvc.user.Account;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "appointment")
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Date time;

    @Column(nullable = false)
    private int length;

    @JoinColumn(name = "customer_id", nullable = false)
    @ManyToOne(targetEntity = Account.class)
    private Account customer;

    @JoinColumn(name = "coach_id", nullable = false)
    @ManyToOne(targetEntity = Account.class)
    private Account coach;


}
