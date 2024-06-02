package com.trivago.subscription.controller;

import com.trivago.subscription.dto.request.SubscriptionRequest;
import com.trivago.subscription.model.Subscription;
import com.trivago.subscription.service.SubscriptionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping("/api/subscriptions")
@CrossOrigin(origins = "http://localhost:3000")
public class SubscriptionController {
    @Autowired
    private SubscriptionService subscriptionService;

    @PostMapping
    public Subscription createSubscription(@Valid @RequestBody SubscriptionRequest subscription) {
        return subscriptionService.save(subscription);
    }

    @GetMapping
    public List<Subscription> getAllSubscriptions(@RequestParam(required = false) String status, @RequestParam(required = false) String startDate) {
        return subscriptionService.getAllSubscriptions(status, startDate);
    }

    @PutMapping("/{id}")
    public Subscription updateStatus(@PathVariable Long id, @RequestParam String status) {
        return subscriptionService.changeStatus(id,status);
    }
}

