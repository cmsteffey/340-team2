package com.csc340team2.mvc.comment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    // maybe add some custom queries here like findCommentContaining(string x)
}
