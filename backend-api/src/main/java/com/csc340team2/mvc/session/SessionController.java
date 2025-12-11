package com.csc340team2.mvc.session;

import org.springframework.web.bind.annotation.*;
import tools.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.util.Map;
import java.util.Optional;

@RestController
public class SessionController {
    @Autowired
    private SessionService sessionService;

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
        return ResponseEntity.status(303).header("Set-Cookie", session.orElseThrow().getSetCookieHeader())
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
        return ResponseEntity.status(204).header("Set-Cookie", session.orElseThrow().getSetCookieHeader()).build();
    }
    @GetMapping("/logout")
    public ResponseEntity logout(){
        return ResponseEntity.status(303).header("Location", "/view/login").header("Set-Cookie", "session_guid=;").build();
    }
}
