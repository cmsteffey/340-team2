package com.csc340team2.mvc.appointment;

import com.csc340team2.mvc.account.AccountRole;
import com.csc340team2.mvc.account.AccountService;
import com.csc340team2.mvc.session.Session;
import com.csc340team2.mvc.account.Account;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
public class AppointmentController {
    @Autowired
    private AppointmentService appointmentService;
    @Autowired
    private AccountService accountService;

    @GetMapping("/appointments")
    public ResponseEntity getAppointments(Session session){
        return session.getAccount().getRole() == AccountRole.COACH ?
                ResponseEntity.ok(appointmentService.getAppointmentsByCoach(session.getAccount())) :
                session.getAccount().getRole() == AccountRole.CUSTOMER ?
                ResponseEntity.ok(appointmentService.getAppointmentsByCustomer(session.getAccount())) :
                ResponseEntity.ok(new ArrayList<Appointment>());
    }
    @PostMapping("/appointments")
    public ResponseEntity scheduleAppointment(Session session, @RequestBody JsonNode body){
        JsonNode timeNode = body.findValue("time");
        if(timeNode == null || !timeNode.isNumber())
            return ResponseEntity.badRequest().contentType(MediaType.TEXT_PLAIN).body("Missing or bad 'time'");
        JsonNode lengthNode = body.findValue("length");
        if(lengthNode == null || !lengthNode.isNumber())
            return ResponseEntity.badRequest().contentType(MediaType.TEXT_PLAIN).body("Missing or bad 'length'");
        JsonNode withNode = body.findValue("with");
        if(withNode == null || !withNode.isNumber())
            return ResponseEntity.badRequest().contentType(MediaType.TEXT_PLAIN).body("Missing or bad 'with'");
        Optional<Account> withOptional = accountService.getAccountById(withNode.asLong());
        if(withOptional.isEmpty())
            return ResponseEntity.status(404).contentType(MediaType.TEXT_PLAIN).body("Coach account not found");
        Appointment appt = new Appointment();
        appt.setTime(Instant.ofEpochMilli(timeNode.asLong()));
        appt.setCustomer(session.getAccount());
        appt.setCoach(withOptional.orElseThrow());
        return ResponseEntity.status(200).body(appointmentService.saveAppointment(appt));
    }
}
