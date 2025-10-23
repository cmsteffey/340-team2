package com.csc340team2.mvc;

import com.csc340team2.mvc.appointment.AppointmentRepository;
import com.csc340team2.mvc.comment.CommentRepository;
import com.csc340team2.mvc.comment.Comment;
import com.csc340team2.mvc.session.Session;
import com.csc340team2.mvc.session.SessionRepository;
import com.csc340team2.mvc.session.SessionService;
import com.csc340team2.mvc.user.Account;
import com.csc340team2.mvc.user.AccountRepository;
import com.csc340team2.mvc.deck.Deck;
import com.csc340team2.mvc.deck.DeckRepository;
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
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Controller
public class ApiController {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private DeckRepository deckRepository;
    @Autowired
    private SessionRepository sessionRepository;
    @Autowired
    private SessionService sessionService;
    @Autowired
    private AppointmentRepository appointmentRepository;
    @Autowired
    private CommentRepository commentRepository;


    @PostMapping(value = "/sessions", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity createSession(@RequestParam Map<String, String> bodyMap){
        String email = bodyMap.getOrDefault("email", null);
        if(email == null)
            return ResponseEntity.status(400).contentType(MediaType.TEXT_PLAIN).body("Email missing or malformed");
        String password = bodyMap.getOrDefault("password", null);
        if(password == null)
            return ResponseEntity.status(400).contentType(MediaType.TEXT_PLAIN).body("Password missing or malformed");
        Optional<Session> session = sessionService.authenticateAndCreateSession(email, password);
        if(session.isEmpty())
            return ResponseEntity.status(404).contentType(MediaType.TEXT_PLAIN).body("Bad credentials");
        return ResponseEntity.status(303).header("Set-Cookie", "session_guid=" + session.orElseThrow().getKey() + "; HttpOnly=true; Secure=true")
                .header("Location", "/view/dashboard").build();
    }

    @PostMapping(value = "/sessions", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity createSession(@RequestBody JsonNode credentialsNode){
        JsonNode emailNode = credentialsNode.findValue("email");
        if(emailNode == null || !emailNode.isTextual())
            return ResponseEntity.status(400).contentType(MediaType.TEXT_PLAIN).body("Email missing or malformed");
        JsonNode passwordNode = credentialsNode.findValue("password");
        if(passwordNode == null || !passwordNode.isTextual())
            return ResponseEntity.status(400).contentType(MediaType.TEXT_PLAIN).body("Password missing or malformed");
        Optional<Session> session = sessionService.authenticateAndCreateSession(emailNode.asText(), passwordNode.asText());
        if(session.isEmpty())
            return ResponseEntity.status(404).contentType(MediaType.TEXT_PLAIN).body("Bad credentials");
        return ResponseEntity.status(200).header("Set-Cookie", "session_guid=" + session.orElseThrow().getKey() + "; HttpOnly=true; Secure=true")
                .header("Location", "/view/dashboard").build();
    }

    @PostMapping("/registration")
    public ResponseEntity registerAccount(@RequestBody Account account){
        Account saved = accountRepository.save(account);
        return ResponseEntity.ok(saved);
    }
    @GetMapping("/accounts")
    public ResponseEntity getAccounts(){
        return ResponseEntity.ok(accountRepository.getAllBy());
    }
    @GetMapping("/appointments")
    public ResponseEntity getAppointments(Session session){

        return ResponseEntity.ok(appointmentRepository.getAppointmentsByCoachId(session.getId()));
    }
    
    //#region Comment
    @GetMapping("/comments")
    public ResponseEntity getAllComments()
    {
        List<Comment> comments = commentRepository.findAll();
        return ResponseEntity.ok(comments);
    }
    @GetMapping("/comment/{id}")
    public ResponseEntity<Comment> getComment(@PathVariable Long id)
    {
        Optional<Comment> comment = commentRepository.findById(id);
        if(comment.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(comment.get());
    }
    @PutMapping("/comments/{id}")
    public ResponseEntity updateComment(@PathVariable Long id, @RequestBody Comment updatedComment)
    {
        Optional<Comment> comment = commentRepository.findById(id);
        if(comment.isEmpty()) return ResponseEntity.notFound().build();
        //Update comment to new comment
        comment.get().setContent(updatedComment.getContent());
        Comment newComment = commentRepository.save(comment.get());
        return ResponseEntity.ok(newComment);
    }
    @DeleteMapping("/comments/{id}")
    public ResponseEntity deleteComment(@PathVariable Long id){
        if(!commentRepository.existsById(id)){
            return ResponseEntity.notFound().build();
        }
        commentRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
    //#endregion

    @PostMapping("/decks")
    public ResponseEntity addDeck(Session session, @RequestBody Deck deck){
        deck.setAccount(session.getAccount());
        return ResponseEntity.ok(deckRepository.save(deck));
    }
    @DeleteMapping("/decks/{id}")
    public ResponseEntity deleteDeck(@PathVariable Long id){
        return deckRepository.deleteDecksById(id) != 0 ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }
    @GetMapping("/decks")
    public ResponseEntity getDecks(){
        return ResponseEntity.ok(deckRepository.getAllBy());
    }
    @GetMapping("/decks/user/{id}")
    public ResponseEntity getDecksForUser(@PathVariable Long id){
        Optional<Account> userInDb = accountRepository.findById(id);
        Account account = userInDb.orElse(null);
        if(account == null){
            return ResponseEntity.status(HttpStatusCode.valueOf(404)).header("Content-Type", "text/plain").body("User not found");
        }
        return ResponseEntity.ok(account.getDecks());
    }

    //Review

    @GetMapping("/view/decks")
    public String viewDecks(Model model){
        List<Deck> decks = deckRepository.getAllWithUsers();
        model.addAttribute("decks", decks);
        LoggerFactory.getLogger(ApiController.class).warn("View called, model attr added");
        return "deckView";
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