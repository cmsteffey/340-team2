package com.csc340team2.mvc.eventSubscription;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventSubscriptionService {
    @Autowired
    private EventSubscriptionRepository eventSubscriptionRepository;

    public EventSubscription createEventSubscription(int accountId)
    {
        EventSubscription returnEventSubscription = new EventSubscription();
        
        //convert currentTime to localDateTime
        Instant now = Instant.now();
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDateTime localTime = LocalDateTime.ofInstant(now, zoneId);

        returnEventSubscription.setCreatedTime(localTime);
        returnEventSubscription.setAccountId(accountId);

        eventSubscriptionRepository.save(returnEventSubscription);
        return returnEventSubscription;
    }
}
