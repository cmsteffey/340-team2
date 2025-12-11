package com.csc340team2.mvc.availabilities;

import com.csc340team2.mvc.account.Account;
import jakarta.persistence.Table;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AvailabilityRepository extends JpaRepository<CoachAvailability, Long> {
    List<CoachAvailability> getAllByCoach(Account coach);
}
