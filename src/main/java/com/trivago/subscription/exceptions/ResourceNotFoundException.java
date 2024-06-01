package com.trivago.subscription.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

    private ResourceNotFoundException(ErrorMessage errorMessage, Object... variables) {
        super(String.format(errorMessage.getMessage(), variables));
    }

    public static ResourceNotFoundException build(String resource) {
        return new ResourceNotFoundException(ErrorMessage.NOT_FOUND, resource);
    }
}
