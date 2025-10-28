package com.csc340team2.mvc.comment;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.csc340team2.mvc.account.Account;
import com.csc340team2.mvc.post.Post;
import com.csc340team2.mvc.post.PostService;
import com.csc340team2.mvc.session.Session;

@RestController
public class CommentController {
    @Autowired
    private CommentService commentService;
    @Autowired
    private PostService postService;
    
    @PostMapping("comment")
    public ResponseEntity createComment(Session session, @RequestBody Comment request) {
        
        Account userAccount = session.getAccount();
        Optional<Post> commentedPost = postService.getPostById(request.getPost().getId());
        
        if(commentedPost.isEmpty())
            return ResponseEntity.badRequest().build();

        Comment createdComment = commentService.createComment(request.getContent(), userAccount, commentedPost.orElseThrow());
        return ResponseEntity.ok(createdComment);
    }
    @GetMapping("/comments/on/{id}")
    public ResponseEntity getComment(@PathVariable Long id)
    {
        Optional<Post> post = postService.getPostById(id);
        if(post.isEmpty()) return ResponseEntity.notFound().build();
        Post newPost = post.orElseThrow();
        return ResponseEntity.ok(commentService.getCommentsByPost(newPost));
    }
    @PutMapping("/comments/{id}")
    public ResponseEntity updateComment(@PathVariable Long id, @RequestBody Comment updatedComment)
    {
        Optional<Comment> comment = commentService.getCommentById(id);
        if(comment.isEmpty()) return ResponseEntity.badRequest().build();
        Comment newComment = comment.orElseThrow();
        newComment.setContent(updatedComment.getContent());
        return ResponseEntity.ok(commentService.updateComment(newComment));
    }
}
