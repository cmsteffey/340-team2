package com.csc340team2.mvc.comment;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

import org.springframework.beans.factory.annotation.Autowired;
import com.csc340team2.mvc.account.Account;

public class CommentService {
    
    @Autowired
    private CommentRepository commentreRepository;
    public Comment createReview(String content, Account customer)
    {
        //convert currentTime to localDateTime
        Instant now = Instant.now();
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDateTime localTime = LocalDateTime.ofInstant(now, zoneId);

        Comment returnReview = new Comment();
        returnReview.setPostTime(localTime);
        returnReview.setContent(content);
        returnReview.setCustomer(customer);

        commentreRepository.save(returnReview);
        return returnReview;
    }
    
}
