package com.csc340team2.mvc.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.csc340team2.mvc.account.Account;
import com.csc340team2.mvc.session.Session;

@RestController
public class EventController {
    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private EventService eventService;

    @PostMapping("/event")
    public ResponseEntity createEvent(Session session, @RequestBody Event request) {
        
        Account account = session.getAccount();

        Event createdEvent = eventService.createEvent(request.getTitle(), request.getDescription(), request.getStartTime(), request.getEventDuration(), request.getEventAddress(), request.getEventCost());
        createdEvent.setEventHostId(account.getId());
        
        eventRepository.save(createdEvent);
        return ResponseEntity.ok(createdEvent);
    }
}
