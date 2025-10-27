package com.csc340team2.mvc;

import com.csc340team2.mvc.account.AccountService;
import com.csc340team2.mvc.appointment.Appointment;
import com.csc340team2.mvc.appointment.AppointmentService;
import com.csc340team2.mvc.deck.DeckService;
import com.csc340team2.mvc.session.Session;
import com.csc340team2.mvc.session.SessionService;
import com.csc340team2.mvc.account.Account;
import com.csc340team2.mvc.deck.Deck;
import com.csc340team2.mvc.account.AccountRole;
import com.fasterxml.jackson.databind.JsonNode;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.FileInputStream;
import java.io.InputStream;
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
    private AppointmentService appointmentService;


    @GetMapping("/view/decks")
    public String viewDecks(Model model){
        List<Deck> decks = deckService.getAllDecksWithUsers();
        model.addAttribute("decks", decks);
        LoggerFactory.getLogger(ApiController.class).warn("View called, model attr added");
        return "deckView";
    }

    @GetMapping("/view/my-decks")
    public String viewMyDecks(Session session, Model model){
        model.addAttribute("decks", deckService.getAllOwnedBy(session.getAccount()));
        return "myDecksView";
    }
    @GetMapping("/view/calendar")
    public String viewMyCalendar(Session session, Model model){
        model.addAttribute("appointments", (List<Appointment>)(
                session.getAccount().getRole() == AccountRole.COACH ?
                        appointmentService.getAppointmentsByCoach(session.getAccount()) :
                session.getAccount().getRole() == AccountRole.CUSTOMER ?
                        appointmentService.getAppointmentsByCustomer(session.getAccount()) :
                        null
                ));
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
    @GetMapping("/view/dashboard")
    public String viewDashboard(Session currentSession, Model model){
        model.addAttribute("pct", Math.abs(new Random().nextInt()) % 100);
        return "dashboard";
    }
    @GetMapping("/static/{fileName}")
    public ResponseEntity getStaticFile(@PathVariable String fileName){
        if(fileName.contains("\\") || fileName.contains("/")){
            return ResponseEntity.badRequest().build();
        }
        try {
            InputStream stream = System.getenv("magicoach-src-from-files") != null ? new FileInputStream("src/main/resources/static/" + fileName) : ApiController.class.getResourceAsStream("/static/" + fileName);
            if(stream == null){
                return ResponseEntity.notFound().build();
            }
            InputStreamResource resource = new InputStreamResource(stream);
            return ResponseEntity.ok().contentType(MediaType.valueOf(fileName.endsWith(".css") ? "text/css" :
                                                   fileName.endsWith(".html") ? "text/html" :
                                                   fileName.endsWith(".js") ? "text/javascript" : "application/octet-stream")).body(resource);
        } catch(Exception o){
            return ResponseEntity.internalServerError().build();
        }
    }
}