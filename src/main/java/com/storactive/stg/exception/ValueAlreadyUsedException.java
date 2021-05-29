package com.storactive.stg.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ValueAlreadyUsedException extends RuntimeException {
    public ValueAlreadyUsedException(String message) {
        super(message);
    }
}
