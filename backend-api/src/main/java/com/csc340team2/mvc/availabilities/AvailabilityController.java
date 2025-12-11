package com.csc340team2.mvc.availabilities;

import com.csc340team2.mvc.account.AccountRole;
import com.csc340team2.mvc.session.Session;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class AvailabilityController {

    @Autowired
    private AvailabilityService availabilityService;

    @PostMapping("/create-availability")
    public ResponseEntity addAvailability(Session session, @RequestParam Map<String, String> body){
        if(session.getAccount().getRole() != AccountRole.COACH){
            return ResponseEntity.badRequest().body("Non coach accounts cannot make availabilities");
        }

        String weekdayString = body.get("weekday");
        LoggerFactory.getLogger(AvailabilityController.class).error("\"" + weekdayString + "\", " + weekdayString.charAt(0));
        if(weekdayString == null || weekdayString.length() != 1 || weekdayString.charAt(0) < '0' || weekdayString.charAt(0) > '6'){
            return ResponseEntity.badRequest().body("Bad weekday");
        }


        String startHourString = body.get("starthour");

        if(startHourString == null || startHourString.length() > 6 || startHourString.chars().anyMatch(x->x < '0' || x > '9')){
            return ResponseEntity.badRequest().body("Bad start time");
        }

        String startMinuteString = body.get("startminute");

        if(startMinuteString == null || startMinuteString.length() > 6 || startMinuteString.chars().anyMatch(x->x < '0' || x > '9')){
            return ResponseEntity.badRequest().body("Bad start time");
        }

        String lengthString = body.get("lengthmins");

        if(lengthString == null || lengthString.length() > 6 || lengthString.chars().anyMatch(x->x < '0' || x > '9')){
            return ResponseEntity.badRequest().body("Bad length");
        }
        String apString = body.get("ap");
        if(apString == null){
            return ResponseEntity.badRequest().body("Bad AP");
        }
        int weekday = weekdayString.charAt(0) - '0';
        int startHour = Integer.parseInt(startHourString) + (startHourString.equals("12") ? -12 : 0) + (apString.equals("PM") ? 12 : 0);
        int startMinute = Integer.parseInt(startMinuteString);
        if(startHour > 23 || startMinute > 59 || (((startHour * 60) + startMinute) * 60 > 86400)){
            return ResponseEntity.badRequest().body("Bad time");
        }
        int length = Integer.parseInt(lengthString) * 60;

        CoachAvailability availability = new CoachAvailability();
        availability.setCoach(session.getAccount());
        availability.setStartTime(((startHour * 60) + startMinute) * 60);
        availability.setLength(length);
        availability.setWeekday(weekday);
        CoachAvailability savedAvailability = availabilityService.saveAvailability(availability);
        return ResponseEntity.status(303).header("Location", "/view/profile").build();
    }
    @PostMapping("/delete-availability/{id}")
    public ResponseEntity deleteAvailability(Session session,@PathVariable Long id){
        availabilityService.deleteAvailability(id);
        return ResponseEntity.status(303).header("Location", "/view/dashboard").build();
    }
}
