package com.csc340team2.mvc.session;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CookieExceptionHandler {
    @ExceptionHandler(InvalidSessionCookieException.class)
    public ResponseEntity<String> handleMissingCookieException(InvalidSessionCookieException ex){
        String redirectLocation = ex.getRedirectLocation();
        if(redirectLocation == null) {
            return ResponseEntity.status(401).contentType(MediaType.TEXT_PLAIN).body(ex.getMessage());
        } else {
            return ResponseEntity.status(303).header("Location", redirectLocation).build();
        }
    }
}
