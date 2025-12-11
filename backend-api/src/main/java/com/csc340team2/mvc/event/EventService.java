package com.csc340team2.mvc.event;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.csc340team2.mvc.account.Account;

@Service
public class EventService {
    @Autowired
    private EventRepository eventRepository;

    public Event createEvent(Account eventHost, String _title, String _description, Instant _startTime, int _eventDuration, String _eventAddress, int _eventCost)
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

    public List<Event> getEventsHostedBy(Account account){
        return eventRepository.getEventsByEventHostId(account.getId());
    }

    public List<Event> getUpcomingEvents() {
        return eventRepository.findByStartTimeAfterOrderByStartTimeAsc(Instant.now());
    }

    public void deleteEvent(Long eventId){
        eventRepository.deleteById(eventId);
    }
}