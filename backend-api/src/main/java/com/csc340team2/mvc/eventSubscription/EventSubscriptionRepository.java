package com.csc340team2.mvc.eventSubscription;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.csc340team2.mvc.account.Account;
import com.csc340team2.mvc.event.Event;

@Repository
public interface EventSubscriptionRepository extends JpaRepository<EventSubscription, Long> {
    public List<EventSubscription> getByAccount(Account account);
    public List<EventSubscription> getByEvent(Event event);
}