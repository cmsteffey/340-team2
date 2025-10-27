package com.csc340team2.mvc.post;

import com.csc340team2.mvc.account.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> getAllByAuthor(Account author);
}
