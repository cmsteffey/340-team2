package com.csc340team2.mvc.event;

import java.time.*;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.csc340team2.mvc.account.AccountRole;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.csc340team2.mvc.account.Account;
import com.csc340team2.mvc.session.Session;

@RestController
public class EventController {
    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private EventService eventService;

    static final private Pattern pricePattern = Pattern.compile("^(?<dollars>\\d+)(\\.(?<cents>\\d{1,2}))?$");

    @PostMapping("/create-event")
    public ResponseEntity createEvent(Session session, @RequestParam Map<String, String> body) {
        
        Account account = session.getAccount();
        if(account.getRole() != AccountRole.COACH)
            return ResponseEntity.badRequest().body("Non-coaches can't make events");

        String dateString = body.get("date");
        if(dateString == null || !dateString.matches("\\d{4}-\\d{2}-\\d{2}"))
            return ResponseEntity.badRequest().body("Bad date");
        String timeString = body.get("time");
        if(timeString == null || !timeString.matches("\\d\\d:\\d\\d"))
            return ResponseEntity.badRequest().body("Bad time");
        int startHour = Integer.parseInt(timeString.substring(0, 2));
        int startMinute = Integer.parseInt(timeString.substring(3, 5));
        String hoursString = body.get("hours");
        if(hoursString == null || !hoursString.matches("\\d{0,2}"))
            return ResponseEntity.badRequest().body("Bad hours");
        String minutesString = body.get("minutes");
        if(minutesString == null || !minutesString.matches("\\d{0,2}"))
            return ResponseEntity.badRequest().body("Bad minutes");
        String address = body.get("address");
        if(address == null || address.isEmpty())
            return ResponseEntity.badRequest().body("Bad address");

        ZoneId zone = ZoneId.of("America/New_York");
        LocalDate date = LocalDate.of(
                Integer.parseInt(dateString.substring(0, 4)),
                Integer.parseInt(dateString.substring(5, 7)) - 1,
                Integer.parseInt(dateString.substring(8, 10))
        );
        LocalDateTime dateTime = LocalDateTime.of(date, LocalTime.of(startHour, startMinute));
        ZonedDateTime zonedDateTime = ZonedDateTime.of(dateTime, zone);
        Instant startTime = Instant.from(zonedDateTime);

        String entryPrice = body.get("price");
        Matcher matcher = pricePattern.matcher(entryPrice);

        String title = body.get("title");
        if(title == null || title.isEmpty())
            return ResponseEntity.badRequest().body("Bad title");
        String description = body.get("description");
        if(description == null || description.isEmpty())
            return ResponseEntity.badRequest().body("Bad description");
        if(!matcher.find()){
            return ResponseEntity.badRequest().body("Bad price");
        }
        int price = matcher.group("cents") == null ? Integer.parseInt(matcher.group("dollars")) : Integer.parseInt(matcher.group("dollars")) * 10 * (matcher.group("cents").length() == 2 ? 10 : 1) + Integer.parseInt(matcher.group("cents"));
        Event createdEvent = eventService.createEvent(account, title, description, startTime, (Integer.parseInt(hoursString) * 60 + Integer.parseInt(minutesString)) * 60, address, price);
        return ResponseEntity.status(303).header("Location", "/view/dashboard").build();
    }
    @GetMapping("/events")
    public ResponseEntity getAllEvents()
    {
        List<Event> events = eventRepository.findAll();
        return ResponseEntity.ok(events);
    }
}
