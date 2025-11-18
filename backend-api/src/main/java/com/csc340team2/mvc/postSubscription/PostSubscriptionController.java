package com.csc340team2.mvc.postSubscription;

import com.csc340team2.mvc.account.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class PostSubscriptionController {
    @Autowired
    private PostSubscriptionService postSubscriptionService;
    @Autowired
    private AccountService accountService;

}
