package com.csc340team2.mvc.eventSubscription;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.csc340team2.mvc.account.Account;
import com.csc340team2.mvc.event.Event;

@Service
public class EventSubscriptionService {
    @Autowired
    private EventSubscriptionRepository eventSubscriptionRepository;

    public EventSubscription createEventSubscription(Event event, Account account)
    {
        EventSubscription returnEventSubscription = new EventSubscription();

        returnEventSubscription.setCreatedTime(Instant.now());
        returnEventSubscription.setAccount(account);
        returnEventSubscription.setEvent(event);

        return eventSubscriptionRepository.save(returnEventSubscription);
    }

    public List<EventSubscription> getEventsByEvent(Event event)
    {
        return eventSubscriptionRepository.getByEvent(event);
    }

    public List<EventSubscription> getEventsByAccount(Account account)
    {
        return eventSubscriptionRepository.getByAccount(account);
    }

    @Transactional
    public void unsubscribeFromEvent(Account user, Event event) {
        eventSubscriptionRepository.deleteByAccountAndEvent(user, event);
    }
}