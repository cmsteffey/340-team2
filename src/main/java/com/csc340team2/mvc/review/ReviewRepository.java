package com.csc340team2.mvc.review;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    //could add something like return rating > x or < x
}
