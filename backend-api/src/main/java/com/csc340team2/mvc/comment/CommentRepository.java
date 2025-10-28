package com.csc340team2.mvc.comment;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.csc340team2.mvc.post.Post;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    public List<Comment> getAllByPost(Post post);
}
