package com.trivago.subscription.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidStatusException extends RuntimeException {

    private InvalidStatusException(ErrorMessage errorMessage, Object... variables) {
        super(String.format(errorMessage.getMessage(), variables));
    }

    public static InvalidStatusException build(String resource) {
        return new InvalidStatusException(ErrorMessage.INVALID_INPUT, resource);
    }
}