package com.csc340team2.mvc.availabilities;

import com.csc340team2.mvc.account.AccountRole;
import com.csc340team2.mvc.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;

@Controller
public class AvailabilityController {

    @Autowired
    private AvailabilityService availabilityService;

    @PostMapping("/create-availability")
    public ResponseEntity addAvailability(Session session, Map<String, String> body){
        if(session.getAccount().getRole() != AccountRole.COACH){
            return ResponseEntity.badRequest().body("Non coach accounts cannot make availabilities");
        }

        String weekdayString = body.get("weekday");
        if(weekdayString == null || weekdayString.length() != 1 || weekdayString.charAt(0) < '0' || weekdayString.charAt(0) > '6'){
            return ResponseEntity.badRequest().body("Bad weekday");
        }


        String startTimeString = body.get("start");

        if(startTimeString == null || startTimeString.length() > 6 || startTimeString.chars().anyMatch(x->x < '0' || x > '9')){
            return ResponseEntity.badRequest().body("Bad start time");
        }

        String lengthString = body.get("length");

        if(lengthString == null || lengthString.length() > 6 || lengthString.chars().anyMatch(x->x < '0' || x > '9')){
            return ResponseEntity.badRequest().body("Bad length");
        }
        int weekday = weekdayString.charAt(0) - '0';
        int startTime = Integer.parseInt(startTimeString);
        int length = Integer.parseInt(lengthString);

        CoachAvailability availability = new CoachAvailability();
        availability.setCoach(session.getAccount());
        availability.setStartTime(startTime);
        availability.setLength(length);
        availability.setWeekday(weekday);
        CoachAvailability savedAvailability = availabilityService.saveAvailability(availability);
        return ResponseEntity.status(303).header("Location", "/view/dashboard").build();
    }
    @PostMapping("/delete-availability/:id")
    public ResponseEntity deleteAvailability(Session session, Long id){
        availabilityService.deleteAvailability(id);
        return ResponseEntity.status(303).header("Location", "/view/dashboard").build();
    }
}
