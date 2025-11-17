package com.csc340team2.mvc;

import com.csc340team2.mvc.account.AccountService;
import com.csc340team2.mvc.appointment.Appointment;
import com.csc340team2.mvc.appointment.AppointmentService;
import com.csc340team2.mvc.deck.DeckService;
import com.csc340team2.mvc.postSubscription.PostSubscriptionService;
import com.csc340team2.mvc.session.Session;
import com.csc340team2.mvc.session.SessionService;
import com.csc340team2.mvc.account.Account;
import com.csc340team2.mvc.deck.Deck;
import com.csc340team2.mvc.account.AccountRole;
import com.fasterxml.jackson.databind.JsonNode;
import org.apache.coyote.Response;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.Instant;
import java.time.YearMonth;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalField;
import java.util.*;

@Controller
public class ApiController {
    @Autowired
    private AccountService accountService;
    @Autowired
    private DeckService deckService;
    @Autowired
    private SessionService sessionService;
    @Autowired
    private PostSubscriptionService postSubscriptionService;
    @Autowired
    private AppointmentService appointmentService;


    @GetMapping("/view/decks")
    public String viewDecks(Model model){
        List<Deck> decks = deckService.getAllDecksWithUsers();
        model.addAttribute("decks", decks);
        return "deckView";
    }

    @GetMapping("/view/my-decks")
    public String viewMyDecks(Session session, Model model){
        model.addAttribute("decks", deckService.getAllOwnedBy(session.getAccount()));
        return "myDecksView";
    }
    @GetMapping("/view/calendar")
    public String viewMyCalendar(Session session, Model model, @RequestParam(required = false) Integer timeOverride){
        List<Appointment> appointments = (List<Appointment>)(
                session.getAccount().getRole() == AccountRole.COACH ?
                        appointmentService.getAppointmentsByCoach(session.getAccount()) :
                session.getAccount().getRole() == AccountRole.CUSTOMER ?
                        appointmentService.getAppointmentsByCustomer(session.getAccount()) :
                        null
                );
        model.addAttribute("appointments", appointments);
        Instant now = Instant.now();
        ZoneOffset zoneOffset = ZoneOffset.of("-5");
        ZonedDateTime nowZoned = ZonedDateTime.ofInstant(now, zoneOffset);//TODO: Include timezone on account object
        YearMonth yearMonth = YearMonth.from(nowZoned);
        model.addAttribute("monthLength", yearMonth.lengthOfMonth());
        model.addAttribute("prevMonthLength", yearMonth.minusMonths(1).lengthOfMonth());
        model.addAttribute("startDay", yearMonth.atDay(1).getDayOfWeek().getValue() % 7);
        model.addAttribute("monthName", yearMonth.getMonth().getDisplayName(TextStyle.FULL_STANDALONE, Locale.US));
        model.addAttribute("dayNames", new String[]{"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"});
        model.addAttribute("currentDay", Optional.ofNullable(timeOverride).orElse(nowZoned.getDayOfMonth()));
        model.addAttribute("currentYear", nowZoned.getYear());
        model.addAttribute("currentMonth", yearMonth.getMonthValue());

        DateTimeFormatter meetingTimeFormatter = DateTimeFormatter.ofPattern("h:mm a");
        ArrayList<Integer> offsets = new ArrayList<>(appointments.size());
        ArrayList<String> timesToShow = new ArrayList<>(appointments.size());
        for (Appointment appointment : appointments) {
            Instant appointmentTime = appointment.getTime();
            ZonedDateTime zonedAppointmentTime = appointmentTime.atZone(zoneOffset);
            offsets.add(zonedAppointmentTime.getMonthValue() == yearMonth.getMonthValue() ? zonedAppointmentTime.getDayOfMonth() - 1 :
                    zonedAppointmentTime.getMonthValue() > yearMonth.getMonthValue() ? zonedAppointmentTime.getDayOfMonth() + yearMonth.lengthOfMonth() - 1 :
                            zonedAppointmentTime.getDayOfMonth() - yearMonth.minusMonths(1).lengthOfMonth() - 1);
            timesToShow.add(zonedAppointmentTime.format(meetingTimeFormatter) + " - " + zonedAppointmentTime.plusSeconds(appointment.getLength()).format(meetingTimeFormatter));
        }
        model.addAttribute("offsets", offsets);
        model.addAttribute("timesToShow", timesToShow);

        return "calendar";
    }
    @GetMapping("/view/profile")
    public String viewProfile(Session session){
        return "profile";
    }
    @GetMapping("/view/login")
    public String viewLogin(){
        return "login";
    }
    @GetMapping("/view/register")
    public String viewRegistration() {
        return "register";
    }
    @GetMapping({"/view/dashboard", "/"})
    public String viewDashboard(Session currentSession, Model model){
        model.addAttribute("pct", Math.abs(new Random().nextInt()) % 100);

        //Used in dashboard to filter out coaches
        List<Account> accountList = accountService.getAllAccounts();
        Collections.shuffle(accountList);
        model.addAttribute("accounts", accountList);

        model.addAttribute("coachPosts", postSubscriptionService.getSubscribedPosts(currentSession.getAccount().getId()));
        LoggerFactory.getLogger(ApiController.class).debug("List {}", model.getAttribute("coachPosts"));

        //Filter out user and coach
        return currentSession.getAccount().getRole() == AccountRole.COACH ?  "dashboard" : "userDashboard";
    }

    @PostMapping("/subscribe")
    public String subscribeToCoach(Session session, @RequestParam long coachId){
        Account user = session.getAccount();
        Optional<Account> coach = accountService.getAccountById(coachId);
        coach.ifPresent(account -> postSubscriptionService.subscribe(user, account));

        return "redirect:/view/dashboard";
    }

    @GetMapping("/static/{fileName}")
    public ResponseEntity getStaticFile(@PathVariable String fileName){
        if(fileName.contains("\\") || fileName.contains("/")){
            return ResponseEntity.badRequest().build();
        }
        try {
            //LoggerFactory.getLogger(ApiController.class).debug(Arrays.stream(new File(".").list()).reduce("", (x, acc)->acc + "\n" + x));
            InputStream stream = System.getenv("magicoach-src-from-files") != null ? new FileInputStream("backend-api/src/main/resources/static/" + fileName) : ApiController.class.getResourceAsStream("/static/" + fileName);
            if(stream == null){
                return ResponseEntity.notFound().build();
            }
            InputStreamResource resource = new InputStreamResource(stream);
            return ResponseEntity.ok().contentType(MediaType.valueOf(fileName.endsWith(".css") ? "text/css" :
                                                   fileName.endsWith(".html") ? "text/html" :
                                                   fileName.endsWith(".js") ? "text/javascript" : "application/octet-stream")).body(resource);
        } catch(Exception o){
            LoggerFactory.getLogger(ApiController.class).error("Inside static:", o);
            return ResponseEntity.internalServerError().build();
        }
    }
    @GetMapping("/profile_picture/{uuid}")
    public ResponseEntity getProfilePicture(@PathVariable UUID uuid){
        try {
            InputStream stream = new FileInputStream("backend-api/src/main/resources/static/profile_pictures/" + uuid.toString() + ".png");
            InputStreamResource resource = new InputStreamResource(stream);
            return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(resource);
        } catch (IOException ioex){
            return ResponseEntity.internalServerError().build();
        }
    }
}