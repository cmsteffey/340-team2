package com.csc340team2.mvc.eventSubscription;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.csc340team2.mvc.account.Account;
import com.csc340team2.mvc.account.AccountService;
import com.csc340team2.mvc.event.Event;
import com.csc340team2.mvc.event.EventService;
import com.csc340team2.mvc.session.Session;

@RestController
public class EventSubscriptionController {
    @Autowired
    private EventSubscriptionService eventSubscriptionService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private EventService eventService;

    @PostMapping("/eventSubscription")
    public ResponseEntity createEventSubscription(@RequestBody EventSubscriptionCreationData request) {
        
        //get user and event to pass into eventSubscription
        
        Optional<Account> account = accountService.getAccountById(request.getAccountId());
        if(account.isEmpty())
            return ResponseEntity.badRequest().build();
        Optional<Event> event = eventService.getByEventId(request.getEventId());
        if(event.isEmpty())
            return ResponseEntity.badRequest().build();

        EventSubscription createdEventSubscription = eventSubscriptionService.createEventSubscription(event.orElseThrow(), account.orElseThrow());

        return ResponseEntity.ok(createdEventSubscription);
    }
    @GetMapping("/myEvents")
    public ResponseEntity findMyEvents(Session session){
        Account account = session.getAccount(); 

        List<Event> joinedEvents = new ArrayList<Event>();
        List<EventSubscription> allEvents = eventSubscriptionService.getEventsByAccount(account);

        for(int i = 0; i < allEvents.size(); i++){
            joinedEvents.add(allEvents.get(i).getEvent());
        }

        return ResponseEntity.ok(joinedEvents);
    }
}
