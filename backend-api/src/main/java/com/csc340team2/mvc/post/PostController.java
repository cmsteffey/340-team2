package com.csc340team2.mvc.post;

import com.csc340team2.mvc.account.Account;
import com.csc340team2.mvc.account.AccountRole;
import com.csc340team2.mvc.account.AccountService;
import com.csc340team2.mvc.event.Event;
import com.csc340team2.mvc.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@Controller
public class PostController {
    @Autowired
    private PostService postService;
    @Autowired
    private AccountService accountService;

    @PostMapping("/create-post")
    public ResponseEntity createPost(Session session, @RequestParam Map<String, String> stringMap){
        if(session.getAccount().getRole() != AccountRole.COACH){
            return ResponseEntity.status(401).contentType(MediaType.TEXT_PLAIN).body("Non-coaches cannot create posts");
        }
        String title = stringMap.get("title");
        if(title == null)
            return ResponseEntity.status(400).contentType(MediaType.TEXT_PLAIN).body("Missing or bad 'title'");
        String content = stringMap.get("content");
        if(content == null)
            return ResponseEntity.status(400).contentType(MediaType.TEXT_PLAIN).body("Missing or bad 'content'");
        postService.createPost(session.getAccount(), title, content);
        return ResponseEntity.status(303).header("Location", "/view/dashboard").build();
    }
    @GetMapping("/posts/by/{id}")
    public ResponseEntity getPostsBy(@PathVariable Long id){
        Optional<Account> account = accountService.getAccountById(id);
        if(account.isEmpty() || account.orElseThrow().getRole() != AccountRole.COACH)
            return ResponseEntity.status(404).contentType(MediaType.TEXT_PLAIN).body("Account not found or not coach");
        return ResponseEntity.ok(postService.getAllPostsMadeBy(account.orElseThrow()));
    }
    @PostMapping("/delete-post/{id}")
    public ResponseEntity deleteEvent(Session session, @PathVariable("id") Long id){
        Optional<Post> post = postService.getPostById(id);
        if(post.isEmpty() || !post.orElseThrow().getAuthor().getId().equals(session.getAccount().getId())){
            return ResponseEntity.status(303).header("Location", "/view/dashboard").build();
        }
        postService.deletePost(id);
        return ResponseEntity.status(303).header("Location", "/view/dashboard").build();
    }
}
