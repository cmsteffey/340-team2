package com.csc340team2.mvc.post;

import com.csc340team2.mvc.account.Account;
import com.csc340team2.mvc.account.AccountRole;
import com.csc340team2.mvc.account.AccountService;
import com.csc340team2.mvc.session.Session;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@Controller
public class PostController {
    @Autowired
    private PostService postService;
    @Autowired
    private AccountService accountService;

    @PostMapping("/posts")
    public ResponseEntity createPost(Session session, @RequestBody JsonNode bodyNode){
        if(session.getAccount().getRole() != AccountRole.COACH){
            return ResponseEntity.status(401).contentType(MediaType.TEXT_PLAIN).body("Non-coaches cannot create posts");
        }
        JsonNode titleNode = bodyNode.findValue("title");
        if(titleNode == null || !titleNode.isTextual())
            return ResponseEntity.status(400).contentType(MediaType.TEXT_PLAIN).body("Missing or bad 'title'");
        JsonNode contentNode = bodyNode.findValue("content");
        if(contentNode == null || !contentNode.isTextual())
            return ResponseEntity.status(400).contentType(MediaType.TEXT_PLAIN).body("Missing or bad 'content'");
        return ResponseEntity.ok(postService.createPost(session.getAccount(), titleNode.textValue(), contentNode.textValue()));
    }
    @GetMapping("/posts/by/{id}")
    public ResponseEntity getPostsBy(@PathVariable Long id){
        Optional<Account> account = accountService.getAccountById(id);
        if(account.isEmpty() || account.orElseThrow().getRole() != AccountRole.COACH)
            return ResponseEntity.status(404).contentType(MediaType.TEXT_PLAIN).body("Account not found or not coach");
        return ResponseEntity.ok(postService.getAllPostsMadeBy(account.orElseThrow()));
    }
}
