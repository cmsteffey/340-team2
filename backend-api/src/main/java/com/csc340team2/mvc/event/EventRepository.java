package com.csc340team2.mvc.event;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> getEventsByEventHostId(Long eventHostId);

    List<Event> findByStartTimeAfterOrderByStartTimeAsc(Instant startTime);
}