package com.csc340team2.mvc.review;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.csc340team2.mvc.account.Account;
import com.csc340team2.mvc.session.Session;

import java.util.List;
import java.util.Optional;

@RestController
public class ReviewController {
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private ReviewService reviewService;

    @PostMapping("/reviews")
    public ResponseEntity createReview(Session session, @RequestBody Review request) {
        
        Account account = session.getAccount();

        Review createdReview = reviewService.createReview(request.getContent(), account, request.getRating());
        return ResponseEntity.ok(createdReview);
    }

    @GetMapping("/reviews")
    public ResponseEntity getAllReviews(){
        List<Review> reviews = reviewRepository.findAll();
        return ResponseEntity.ok(reviews);
    }
    @GetMapping("/reviews/{id}")
    public ResponseEntity getReview(long id)
    {
        Optional<Review> review = reviewRepository.findById(id);
        if(review.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(review.get());
    }
    @PutMapping("/reviews/{id}")
    public ResponseEntity updateReview(@PathVariable Long id, @RequestBody Review updatedReview)
    {
        Optional<Review> review = reviewRepository.findById(id);
        if(review.isEmpty()) return ResponseEntity.notFound().build();
        //Update review to new review
        review.get().setContent(updatedReview.getContent());
        Review newReview = reviewRepository.save(review.get());
        return ResponseEntity.ok(newReview);
    }
    @DeleteMapping("/reviews/{id}")
    public ResponseEntity deleteReview(@PathVariable Long id){
        if(!reviewRepository.existsById(id)){
            return ResponseEntity.notFound().build();
        }
        reviewRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
