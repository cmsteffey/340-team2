package com.csc340team2.mvc.review;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.csc340team2.mvc.account.Account;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;
    
    public Review createReview(String content, Account customer, Account coach, int rating)
    {
        Review returnReview = new Review();
        returnReview.setPostTime(Instant.now());
        returnReview.setContent(content);
        returnReview.setCustomer(customer);
        returnReview.setRating(rating);
        returnReview.setCoach(coach);

        return reviewRepository.save(returnReview);
    }

    public Optional<Review> findById(Long id){
        return reviewRepository.findById(id);
    }

    public Review updateReview(Review review){
        return reviewRepository.save(review);
    }

    public List<Review> getReviewsByCoach(Account coach){
        return reviewRepository.getReviewsByCoach(coach);
    }
}
