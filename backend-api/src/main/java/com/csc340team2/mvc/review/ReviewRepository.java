package com.csc340team2.mvc.review;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.csc340team2.mvc.account.Account;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    public List<Review> getReviewsByCoach(Account coach);
}
