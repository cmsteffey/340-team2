package com.csc340team2.mvc.review;

import com.csc340team2.mvc.account.Account;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "review")
public class Review {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private int rating;

    @Column(nullable = false)
    private LocalDateTime postTime;

    @Column(nullable = false)
    private String content;

    @ManyToOne(targetEntity = Account.class)
    @JoinColumn(name = "customer_id", nullable = false)
    private Account customer;

    //Constructor
    public Review(){
        this.postTime = LocalDateTime.now(); 
        this.rating = 0;
    }

    //Getters
    public LocalDateTime getPostTime(){ return postTime; }
    public String getContent() { return content; }
    public Account getCustomer() { return customer; }
    public int getRating() { return rating; }

    //Setters
    public void setPostTime(LocalDateTime postTime){ this.postTime = postTime; }
    public void setContent(String content){ this.content = content; }
    public void setCustomer(Account customer){ this.customer = customer; }
    public void setRating(int rating){ this.rating = rating; }

}