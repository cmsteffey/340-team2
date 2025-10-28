package com.csc340team2.mvc.review;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.csc340team2.mvc.account.Account;
import com.csc340team2.mvc.account.AccountRole;
import com.csc340team2.mvc.account.AccountService;
import com.csc340team2.mvc.post.Post;
import com.csc340team2.mvc.session.Session;

import java.util.List;
import java.util.Optional;

@RestController
public class ReviewController {
    @Autowired
    private ReviewService reviewService;
    @Autowired
    private AccountService accountService;

    @PostMapping("/reviews")
    public ResponseEntity createReview(Session session, @RequestBody Review request) {
        
        Account userAccount = session.getAccount();
        if(userAccount.getRole() == AccountRole.COACH) return ResponseEntity.badRequest().build();
        LoggerFactory.getLogger(ReviewController.class).debug("{}", request.getCoach().getId());
        Optional<Account> coachAccount = accountService.getAccountById(request.getCoach().getId());
        if(coachAccount.isEmpty())
            return ResponseEntity.badRequest().build();

        Review createdReview = reviewService.createReview(request.getContent(), userAccount, coachAccount.orElseThrow(), request.getRating());
        return ResponseEntity.ok(createdReview);
    }

    @GetMapping("/reviews/on/{id}")
    public ResponseEntity getReview(@PathVariable Long id)
    {
        Optional<Account> coach = accountService.getAccountById(id);
        if(coach.isEmpty() || coach.orElseThrow().getRole() != AccountRole.COACH) return ResponseEntity.notFound().build();
        Account newCoach = coach.orElseThrow();
        return ResponseEntity.ok(reviewService.getReviewsByCoach(newCoach));
    }
    @GetMapping("/reviews/{id}")
    public ResponseEntity getReview(long id)
    {
        Optional<Review> review = reviewService.findById(id);
        if(review.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(review.get());
    }
    @PutMapping("/reviews/{id}")
    public ResponseEntity updateReview(@PathVariable Long id, @RequestBody Review updatedReview)
    {
        Optional<Review> review = reviewService.findById(id);
        if(review.isEmpty()) return ResponseEntity.notFound().build();
        //Update review to new review
        Review newReview = review.orElseThrow();
        return ResponseEntity.ok(reviewService.updateReview(newReview));
    }
}
