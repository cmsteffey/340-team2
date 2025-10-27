package com.csc340team2.mvc.review;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class ReviewController {
    @Autowired
    private ReviewRepository reviewRepository;

    @PostMapping("reviews/new")
    public ResponseEntity<Review> createReview(@RequestBody Review review) {
        Review savedReview = reviewRepository.save(review);
        return ResponseEntity.ok(savedReview);
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
