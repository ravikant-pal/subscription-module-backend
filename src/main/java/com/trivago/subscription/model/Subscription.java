package com.trivago.subscription.model;

import com.trivago.subscription.dto.Status;
import com.trivago.subscription.dto.Term;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
public class Subscription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long hotelId;
    private LocalDate startDate;
    private LocalDate nextPaymentOn;
    private LocalDate endDate;
    @Enumerated(EnumType.ORDINAL)
    private Term term;
    @Enumerated(EnumType.ORDINAL)
    private Status status;
}
