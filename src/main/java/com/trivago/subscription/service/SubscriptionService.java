package com.trivago.subscription.service;

import com.trivago.subscription.dto.request.SubscriptionRequest;
import com.trivago.subscription.model.Subscription;

import java.util.List;

public interface SubscriptionService {
    Subscription save(SubscriptionRequest subscriptionRequest);

    List<Subscription> getAllSubscriptions(String status, String startDate);

    Subscription changeStatus(Long id, String status);
}
