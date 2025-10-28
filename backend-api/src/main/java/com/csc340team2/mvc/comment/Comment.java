package com.csc340team2.mvc.comment;

import com.csc340team2.mvc.account.Account;
import com.csc340team2.mvc.post.Post;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "comment")
public class Comment {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column()
    private LocalDateTime postTime;

    @Column(nullable = false)
    private String content;

    @ManyToOne(targetEntity = Account.class)
    @JoinColumn(name = "customer_id", nullable = false)
    private Account customer;

    @ManyToOne(targetEntity = Post.class)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    //Constructor
    public Comment(){
        this.postTime = LocalDateTime.now(); 
    }

    //Getters
    public LocalDateTime getPostTime(){ return postTime; }
    public String getContent() { return content; }
    public Account getCustomer() { return customer; }
    public Post getPost() { return post; }

    //Setters
    public void setPostTime(LocalDateTime postTime){ this.postTime = postTime; }
    public void setContent(String content){ this.content = content; }
    public void setCustomer(Account customer){ this.customer = customer; }
    public void setPost(Post post){ this.post = post; }

}
