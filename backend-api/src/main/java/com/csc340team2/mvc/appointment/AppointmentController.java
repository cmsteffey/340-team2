package com.csc340team2.mvc.appointment;

import com.csc340team2.mvc.account.AccountRole;
import com.csc340team2.mvc.account.AccountService;
import com.csc340team2.mvc.session.Session;
import com.csc340team2.mvc.account.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.*;

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
    public ResponseEntity scheduleAppointment(Session session, @RequestParam Map<String, String> body){
        String timeString = body.get("time");
        if(timeString == null || timeString.length() > 19 || timeString.isEmpty() || timeString.chars().anyMatch(x->x > '9' || x < '0'))
            return ResponseEntity.badRequest().contentType(MediaType.TEXT_PLAIN).body("Missing or bad 'time'");
        String lengthString = body.get("length");
        if(lengthString == null || lengthString.length() > 9 || lengthString.isEmpty() || lengthString.chars().anyMatch(x->x > '9' || x < '0'))
            return ResponseEntity.badRequest().contentType(MediaType.TEXT_PLAIN).body("Missing or bad 'length'");
        String withString = body.get("with");
        if(withString == null || withString.length() > 19 || withString.isEmpty() || withString.chars().anyMatch(x->x > '9' || x < '0'))
            return ResponseEntity.badRequest().contentType(MediaType.TEXT_PLAIN).body("Missing or bad 'with'");
        long timeLong = Long.parseLong(timeString);
        int lengthInt = Integer.parseInt(lengthString);
        long withLong = Long.parseLong(withString);
        Optional<Account> withOptional = accountService.getAccountById((long)Integer.parseInt(withString));
        if(withOptional.isEmpty())
            return ResponseEntity.status(404).contentType(MediaType.TEXT_PLAIN).body("Coach account not found");

        Appointment appt = new Appointment();
        appt.setTime(Instant.ofEpochMilli(timeLong));
        appt.setCustomer(session.getAccount());
        appt.setCoach(withOptional.orElseThrow());
        appt.setLength(lengthInt);
        Appointment saved = appointmentService.saveAppointment(appt);
        if(saved == null)
            return ResponseEntity.status(409).body("Appointment Conflict for " + withOptional.orElseThrow().getUsername());
        return ResponseEntity.status(303).header("Location", "/view/calendar").build();
    }
}
