package com.csc340team2.mvc.review;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.csc340team2.mvc.account.Account;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;
    
    public Review createReview(String content, Account customer, int rating)
    {
        //convert currentTime to localDateTime
        Instant now = Instant.now();
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDateTime localTime = LocalDateTime.ofInstant(now, zoneId);

        Review returnReview = new Review();
        returnReview.setPostTime(localTime);
        returnReview.setContent(content);
        returnReview.setCustomer(customer);
        returnReview.setRating(rating);

        reviewRepository.save(returnReview);
        return returnReview;
    }
}
