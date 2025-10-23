package com.csc340team2.mvc.session;

public class InvalidSessionCookieException extends Exception{
    private String redirectLocation;
    public InvalidSessionCookieException(String message, String redirectLocation){
        super(message);
        this.redirectLocation = redirectLocation;
    }
    public String getRedirectLocation(){
        return this.redirectLocation;
    }
}
