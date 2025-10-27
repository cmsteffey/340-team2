package com.csc340team2.mvc.comment;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.csc340team2.mvc.account.Account;
import com.csc340team2.mvc.account.AccountService;

public class CommentController {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private CommentService commentService;
    @Autowired
    private AccountService accountService;
    
    @PostMapping("comment/{id}")
    public ResponseEntity createComment(@PathVariable Long id, @RequestBody Comment request) {
        
        Optional<Account> optAccount = accountService.getAccountById(id);

        if(optAccount.isEmpty())
            return ResponseEntity.notFound().build();

        Account account = optAccount.get();

        Comment createdComment = commentService.createComment(request.getContent(), account);
        return ResponseEntity.ok(createdComment);
    }
    @GetMapping("/comments")
    public ResponseEntity getAllComments()
    {
        List<Comment> comments = commentRepository.findAll();
        return ResponseEntity.ok(comments);
    }
    @GetMapping("/comment/{id}")
    public ResponseEntity<Comment> getComment(@PathVariable Long id)
    {
        Optional<Comment> comment = commentRepository.findById(id);
        if(comment.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(comment.get());
    }
    @PutMapping("/comments/{id}")
    public ResponseEntity updateComment(@PathVariable Long id, @RequestBody Comment updatedComment)
    {
        Optional<Comment> comment = commentRepository.findById(id);
        if(comment.isEmpty()) return ResponseEntity.notFound().build();
        //Update comment to new comment
        comment.get().setContent(updatedComment.getContent());
        Comment newComment = commentRepository.save(comment.get());
        return ResponseEntity.ok(newComment);
    }
    @DeleteMapping("/comments/{id}")
    public ResponseEntity deleteComment(@PathVariable Long id){
        if(!commentRepository.existsById(id)){
            return ResponseEntity.notFound().build();
        }
        commentRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
