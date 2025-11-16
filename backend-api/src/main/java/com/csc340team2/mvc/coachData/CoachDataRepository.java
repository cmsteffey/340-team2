package com.csc340team2.mvc.coachData;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoachDataRepository extends JpaRepository<CoachData, Long> {

}
