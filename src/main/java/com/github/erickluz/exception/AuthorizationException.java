package com.github.erickluz.exception;

public class AuthorizationException extends Exception{
    private static final long serialVersionUID = 3053724504261168874L;

    public AuthorizationException(String message) {
        super(message);
    }
}