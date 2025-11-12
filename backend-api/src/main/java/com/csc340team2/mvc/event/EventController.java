package com.csc340team2.mvc.event;

import java.util.List;

import org.slf4j.LoggerFactory;
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
        LoggerFactory.getLogger(EventController.class).debug("{}", session.getAccount());
        LoggerFactory.getLogger(EventController.class).debug("{}", session.getAccount().getUsername());

        Event createdEvent = eventService.createEvent(account, request.getTitle(), request.getDescription(), request.getStartTime(), request.getEventDuration(), request.getEventAddress(), request.getEventCost());
        LoggerFactory.getLogger(EventController.class).debug("{}", createdEvent.getTitle());
        LoggerFactory.getLogger(EventController.class).debug("{}", createdEvent.getTitle());

        return ResponseEntity.ok(createdEvent);
    }
    @GetMapping("/events")
    public ResponseEntity getAllEvents()
    {
        List<Event> events = eventRepository.findAll();
        return ResponseEntity.ok(events);
    }
}
