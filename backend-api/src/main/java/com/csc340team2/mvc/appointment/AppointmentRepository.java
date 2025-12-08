package com.csc340team2.mvc.appointment;

import com.csc340team2.mvc.account.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> getAppointmentsByCoach(Account coach);
    List<Appointment> getAppointmentsByCustomer(Account customer);
    @Query(value = "select id from appointment a where a.coach_id = :coach and ((a.time > to_timestamp(:startTime / 1000) and a.time < to_timestamp((:startTime / 1000) + :length)) OR ((a.time + make_interval(secs => :length)) > to_timestamp(:startTime / 1000) and (a.time + make_interval(secs => :length)) < to_timestamp((:startTime / 1000) + :length)))", nativeQuery = true)
    List<Appointment> getConflicts(@Param("coach") Long coachId, @Param("start") Long startTime, @Param("length") Integer length);
}
