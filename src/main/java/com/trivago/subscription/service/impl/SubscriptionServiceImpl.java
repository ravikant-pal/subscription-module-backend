package com.trivago.subscription.service.impl;

import com.trivago.subscription.dto.Status;
import com.trivago.subscription.dto.request.SubscriptionDto;
import com.trivago.subscription.exceptions.ActiveSubscriptionExistsException;
import com.trivago.subscription.model.Subscription;
import com.trivago.subscription.repository.SubscriptionRepository;
import com.trivago.subscription.service.SubscriptionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Override
    @Transactional
    public Subscription save(SubscriptionDto subscriptionDto) {
        Long hotelId = subscriptionDto.getHotelId();
        if (hasActiveSubscription(hotelId)) {
            throw ActiveSubscriptionExistsException.build("Active subscription", "hotel ID: " + hotelId);
        } else {
           return subscriptionRepository.save(subscriptionDto.toSubscription());
        }
    }

    private boolean hasActiveSubscription(Long hotelId) {
        List<Subscription> allSubForHotel = subscriptionRepository.findByHotelId(hotelId);
        return allSubForHotel.stream()
                .anyMatch(sub -> sub.getStatus() == Status.ACTIVE);
    }
}
