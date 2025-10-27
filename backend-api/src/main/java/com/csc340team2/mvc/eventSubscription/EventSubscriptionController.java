package com.csc340team2.mvc.eventSubscription;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.csc340team2.mvc.session.Session;

@RestController
public class EventSubscriptionController {
    @Autowired
    private EventSubscriptionService eventSubscriptionService;

    @PostMapping("/eventSubscription")
    public ResponseEntity createEventSubscription(@RequestBody EventSubscription request) {
        
        EventSubscription createdEventSubscription = eventSubscriptionService.createEventSubscription(request.getAccountId());

        return ResponseEntity.ok(createdEventSubscription);
    }
}
