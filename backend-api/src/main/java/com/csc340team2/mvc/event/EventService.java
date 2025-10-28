package com.csc340team2.mvc.event;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.csc340team2.mvc.account.Account;

@Service
public class EventService {
    @Autowired
    private EventRepository eventRepository;
    
    public Event createEvent(Account eventHost, String _title, String _description, LocalDateTime _startTime, int _eventDuration, String _eventAddress, double _eventCost)
    {
        Event returnEvent = new Event();
        returnEvent.setEventHost(eventHost);
        returnEvent.setTitle(_title);
        returnEvent.setDescription(_description);
        returnEvent.setStartTime(_startTime);
        returnEvent.setEventDuration(_eventDuration);
        returnEvent.setEventAddress(_eventAddress);
        returnEvent.setEventCost(_eventCost);

        return eventRepository.save(returnEvent);
    }

    public Optional<Event> getByEventId(Long eventId)
    {
        return eventRepository.findById(eventId);
    }

    //add get event

}
