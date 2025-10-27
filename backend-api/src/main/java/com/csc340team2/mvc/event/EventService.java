package com.csc340team2.mvc.event;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventService {
    @Autowired
    private EventRepository eventRepository;
    
    public Event createEvent(String _title, String _description, LocalDateTime _startTime, int _eventDuration, String _eventAddress, double _eventCost)
    {
        Event returnEvent = new Event();
        returnEvent.setTitle(_title);
        returnEvent.setDescription(_description);
        returnEvent.setStartTime(_startTime);
        returnEvent.setEventDuration(_eventDuration);
        returnEvent.setEventAddress(_eventAddress);
        returnEvent.setEventCost(_eventCost);

        eventRepository.save(returnEvent);
        return returnEvent;
    }
}
