package com.trivago.subscription.model;

import com.trivago.subscription.dto.Status;
import com.trivago.subscription.dto.Term;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
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
