package com.trivago.subscription.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidTermException extends RuntimeException {

    private InvalidTermException(ErrorMessage errorMessage, Object... variables) {
        super(String.format(errorMessage.getMessage(), variables));
    }

    public static InvalidTermException build(String resource) {
        return new InvalidTermException(ErrorMessage.INVALID_INPUT, resource);
    }
}