package com.csc340team2.mvc.appointment;

import com.csc340team2.mvc.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AppointmentController {
    @Autowired
    private AppointmentService appointmentService;
    @GetMapping("/appointments")
    public ResponseEntity getAppointments(Session session){
        return ResponseEntity.ok(appointmentService.getAppointmentsByCoach(session.getAccount()));
    }
}
