package com.csc340team2.mvc.comment;

import com.csc340team2.mvc.user.Account;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "comment")
public class Comment {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private LocalDateTime postTime;

    @Column(nullable = false)
    private String content;

    @ManyToOne(targetEntity = Account.class)
    @JoinColumn(name = "customer_id", nullable = false)
    private Account customer;

    //Constructor
    public Comment(){
        this.postTime = LocalDateTime.now(); 
    }

    //Getters
    public LocalDateTime getPostTime(){ return postTime; }
    public String getContent() { return content; }
    public Account getCustomer() { return customer; }

    //Setters
    public void setPostTime(LocalDateTime postTime){ this.postTime = postTime; }
    public void setContent(String content){ this.content = content; }
    public void setCustomer(Account customer){ this.customer = customer; }

}
