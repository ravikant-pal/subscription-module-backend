package com.trivago.subscription.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ActiveSubscriptionExistsException extends RuntimeException {

    private ActiveSubscriptionExistsException(ErrorMessage errorMessage, Object... variables) {
        super(String.format(errorMessage.getMessage(), variables));
    }

    public static ActiveSubscriptionExistsException build(String... resource) {
        return new ActiveSubscriptionExistsException(ErrorMessage.ALREADY_EXIST, (Object[]) resource);
    }
}