package com.csc340team2.mvc.appointment;

import com.csc340team2.mvc.account.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> getAppointmentsByCoach(Account coach);
    List<Appointment> getAppointmentsByCustomer(Account customer);
}
