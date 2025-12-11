package com.csc340team2.mvc.availabilities;

import jakarta.persistence.Table;
import org.springframework.data.jpa.repository.JpaRepository;

@Table(name = "availability")
public interface AvailabilityRepository extends JpaRepository<CoachAvailability, Long> {

}
