package com.trivago.subscription.dto.request;

import com.trivago.subscription.dto.Status;
import com.trivago.subscription.dto.Term;
import com.trivago.subscription.exceptions.InvalidTermException;
import com.trivago.subscription.model.Subscription;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Data
public class SubscriptionRequest {

    @NotNull(message = "Hotel ID is required")
    private Long hotelId;

    @NotNull(message = "Start date is required")
    @FutureOrPresent(message = "Start date must be in the present or future")
    private String startDate;

    @NotNull(message = "Term is required")
    private String term;

    public Subscription toSubscription() {

        try {
            Term parsedTerm = Term.valueOf(this.term);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate parsedStartDate = LocalDate.parse(this.startDate, formatter);
            LocalDate nextPaymentOn = parsedTerm == Term.MONTHLY ? parsedStartDate.plusMonths(1) : parsedStartDate.plusYears(1);
            return Subscription.builder()
                    .hotelId(this.hotelId)
                    .startDate(parsedStartDate)
                    .nextPaymentOn(nextPaymentOn)
                    .term(parsedTerm)
                    .status(Status.ACTIVE)
                    .build();
        } catch (IllegalArgumentException e) {
            throw InvalidTermException.build("term : " + this.term);
        }
    }
}
