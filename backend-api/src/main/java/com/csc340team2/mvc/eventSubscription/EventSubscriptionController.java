package com.csc340team2.mvc.eventSubscription;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.csc340team2.mvc.session.Session;

@RestController
public class EventSubscriptionController {
    @Autowired
    private EventSubscriptionRepository eventSubscriptionRepository;
    @Autowired
    private EventSubscriptionService eventSubscriptionService;

    @PostMapping("/event")
    public ResponseEntity createEvent(Session session, @RequestBody EventSubscription request) {

        EventSubscription createdEventSubscription = eventSubscriptionService.createEventSubscription();
        
        eventSubscriptionRepository.save(createdEventSubscription);
        return ResponseEntity.ok(createdEventSubscription);
    }
}
