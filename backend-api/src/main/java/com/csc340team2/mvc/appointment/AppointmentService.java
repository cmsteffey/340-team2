package com.csc340team2.mvc.appointment;

import com.csc340team2.mvc.account.Account;
import com.csc340team2.mvc.account.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppointmentService {
    @Autowired
    private AppointmentRepository appointmentRepository;
    public List<Appointment> getAppointmentsByCoach(Account coach){
        return appointmentRepository.getAppointmentsByCoach(coach);
    }
    public List<Appointment> getAppointmentsByCustomer(Account customer){
        return appointmentRepository.getAppointmentsByCustomer(customer);
    }

}
