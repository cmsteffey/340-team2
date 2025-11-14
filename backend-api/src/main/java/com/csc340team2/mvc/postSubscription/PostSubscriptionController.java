package com.csc340team2.mvc.postSubscription;

import com.csc340team2.mvc.account.Account;
import com.csc340team2.mvc.account.AccountRole;
import com.csc340team2.mvc.account.AccountService;
import com.csc340team2.mvc.post.PostService;
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
public class PostSubscriptionController {
    @Autowired
    private PostSubscriptionService postSubscriptionService;
    @Autowired
    private AccountService accountService;

}
