package com.trivago.subscription.service.impl;

import com.trivago.subscription.dto.Status;
import com.trivago.subscription.dto.Term;
import com.trivago.subscription.dto.request.SubscriptionRequest;
import com.trivago.subscription.exceptions.ActiveSubscriptionExistsException;
import com.trivago.subscription.exceptions.InvalidStatusException;
import com.trivago.subscription.exceptions.ResourceNotFoundException;
import com.trivago.subscription.model.Subscription;
import com.trivago.subscription.repository.SubscriptionRepository;
import com.trivago.subscription.service.SubscriptionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @Override
    @Transactional
    public Subscription save(SubscriptionRequest subscriptionRequest) {
        Long hotelId = subscriptionRequest.getHotelId();
        if (hasActiveSubscription(hotelId)) {
            throw ActiveSubscriptionExistsException.build("Active subscription", "hotel ID: " + hotelId);
        } else {
           return subscriptionRepository.save(subscriptionRequest.toSubscription());
        }
    }

    @Override
    public List<Subscription> getAllSubscriptions(String status, String monthAndYear) {
        if (monthAndYear != null) {
            YearMonth yearMonth = YearMonth.parse(monthAndYear, formatter);

            LocalDate startDate = yearMonth.atDay(1);
            LocalDate endDate = yearMonth.atEndOfMonth();

            if (status != null) {
                if (Status.valueOf(status) == Status.EXPIRED) {
                    return subscriptionRepository.findByEndDateLessThanEqual(LocalDate.now());
                } else {
                    return subscriptionRepository.findAllByStatusAndStartDateBetweenOrderByStartDateDesc(Status.valueOf(status), startDate, endDate);
                }
            } else {
                return subscriptionRepository.findAllByStartDateBetweenOrderByStartDateDesc(startDate, endDate);
            }
        } else {
            if (status != null) {
                if (Status.valueOf(status) == Status.EXPIRED) {
                    return subscriptionRepository.findByEndDateLessThanEqual(LocalDate.now());
                } else {
                    return subscriptionRepository.findAllByStatusOrderByStartDateDesc(Status.valueOf(status));
                }
            } else {
                return subscriptionRepository.findAllByOrderByStartDateDesc();
            }
        }
    }

    @Override
    @Transactional
    public Subscription changeStatus(Long id, String status) {
        Optional<Subscription> optionalSubscription = subscriptionRepository.findById(id);
        if (optionalSubscription.isEmpty()) {
            throw ResourceNotFoundException.build("Subscription with id: " + id);
        }
        try {
            Status parsedStatus = Status.valueOf(status);
            Subscription subscription = optionalSubscription.get();
            if (parsedStatus == Status.EXPIRED) {
                throw InvalidStatusException.build("status : " + status);
            } else if (parsedStatus == Status.ACTIVE) {
                LocalDate startDate = LocalDate.now();
                LocalDate nextPaymentOn = subscription.getTerm() == Term.MONTHLY ? startDate.plusMonths(1) : startDate.plusYears(1);
                subscription.setStartDate(startDate);
                subscription.setNextPaymentOn(nextPaymentOn);
                subscription.setEndDate(null);
            } else if (parsedStatus == Status.CANCELED) {
                subscription.setEndDate(subscription.getNextPaymentOn().plusDays(1));
            }
            subscription.setStatus(parsedStatus);
            return subscription;
        } catch (IllegalArgumentException e) {
            throw InvalidStatusException.build("status : " + status);
        }
    }

    private boolean hasActiveSubscription(Long hotelId) {
        List<Subscription> allSubForHotel = subscriptionRepository.findByHotelId(hotelId);
        return allSubForHotel.stream()
                .anyMatch(sub -> sub.getStatus() == Status.ACTIVE);
    }
}
