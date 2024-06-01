package com.trivago.subscription.controller;

import com.trivago.subscription.dto.request.SubscriptionDto;
import com.trivago.subscription.model.Subscription;
import com.trivago.subscription.service.SubscriptionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping("/api/subscriptions")
public class SubscriptionController {
    @Autowired
    private SubscriptionService subscriptionService;

    @PostMapping
    public Subscription createSubscription(@Valid @RequestBody SubscriptionDto subscription) {
        return subscriptionService.save(subscription);
    }
}

