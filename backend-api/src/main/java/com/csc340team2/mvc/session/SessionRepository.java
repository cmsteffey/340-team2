package com.csc340team2.mvc.session;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SessionRepository extends JpaRepository<Session, Long> {
    public List<Session> getAllBy();
    public Optional<Session> findSessionByKey(String key);

}
