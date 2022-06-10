package com.example.Ecommerce.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class AccountAuthenticationException extends Exception {

    private static final long serialVersionUID = 1L;

    public AccountAuthenticationException(String message) {
        super(message);
    }

}