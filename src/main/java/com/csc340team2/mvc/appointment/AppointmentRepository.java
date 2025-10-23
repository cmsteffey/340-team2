package com.csc340team2.mvc.appointment;

import com.csc340team2.mvc.user.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> getAppointmentsByCoach(Account coach);
    List<Appointment> getAppointmentsByCustomer(Account customer);
}
