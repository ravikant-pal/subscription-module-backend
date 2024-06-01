package com.trivago.subscription.service;

import com.trivago.subscription.dto.request.SubscriptionDto;
import com.trivago.subscription.model.Subscription;

public interface SubscriptionService {
    Subscription save(SubscriptionDto subscriptionDto);
}
