package com.switchfully.eurder.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UnknownIdException extends RuntimeException {

    public UnknownIdException(String message) {
        super(message);
    }
}
