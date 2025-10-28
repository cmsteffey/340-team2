package com.csc340team2.mvc.comment;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.csc340team2.mvc.account.Account;
import com.csc340team2.mvc.post.Post;

@Service
public class CommentService {
    
    @Autowired
    private CommentRepository commentRepository;
    public Comment createComment(String content, Account customer, Post post)
    {
        //convert currentTime to localDateTime
        Instant now = Instant.now();
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDateTime localTime = LocalDateTime.ofInstant(now, zoneId);

        Comment returnComment = new Comment();
        returnComment.setPostTime(localTime);
        returnComment.setContent(content);
        returnComment.setCustomer(customer);
        returnComment.setPost(post);

        return commentRepository.save(returnComment);
    }

    public List<Comment> getCommentsByPost(Post post){
        return commentRepository.getAllByPost(post);
    }

    public Optional<Comment> getCommentById(Long id){
        return commentRepository.findById(id);
    }

    public Comment updateComment(Comment comment){
        return commentRepository.save(comment);
    }
}
