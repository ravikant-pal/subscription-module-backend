package com.trivago.subscription.controller;

import com.trivago.subscription.dto.request.SubscriptionRequest;
import com.trivago.subscription.model.Subscription;
import com.trivago.subscription.service.SubscriptionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;



/*
 TODO: 1. Create the database schema. ✅
  2. discuss the logic of subscription status like ACTIVE, EXPIRED and CANCELED
  3. Create an audit table that stores the history of changes in a subscription.
  4. Docker file for frontend UI
  5. run with docker compose
  6. Provide detailed instructions on how to execute code
  2. Retrieve subscriptions by start date month

 */


@Validated
@RestController
@RequestMapping("/api/subscriptions")
@CrossOrigin(origins = "http://localhost:3000")
@Tag(name = "Auth Controller", description = "Info related to Auth.")
public class SubscriptionController {
    @Autowired
    private SubscriptionService subscriptionService;

    @Operation(summary = "New Subscription", description = "Start new subscription for a specific hotel.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful response", content = @Content(schema = @Schema(implementation = Map.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = String.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "409", description = "Active Subscription already exists.", content = @Content(schema = @Schema(implementation = String.class), mediaType = MediaType.TEXT_HTML_VALUE)),
            @ApiResponse(responseCode = "500", description = "Unknown or unexpected error encountered", content = @Content(schema = @Schema(implementation = String.class), mediaType = MediaType.TEXT_HTML_VALUE))
    })
    @PostMapping
    public Subscription createSubscription(@Valid @RequestBody SubscriptionRequest subscription) {
        return subscriptionService.save(subscription);
    }

    @Operation(summary = "Get All Subscription", description = "Get all subscription with @status and @startDate optional filter.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful response", content = @Content(schema = @Schema(implementation = List.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "500", description = "Unknown or unexpected error encountered", content = @Content(schema = @Schema(implementation = String.class), mediaType = MediaType.TEXT_HTML_VALUE))
    })
    @GetMapping
    public List<Subscription> getAllSubscriptions(@RequestParam(required = false) String status, @RequestParam(required = false) String startDate) {
        return subscriptionService.getAllSubscriptions(status, startDate);
    }

    @Operation(summary = "Change Subscription Status", description = "To cancel/restart subscription with given @id and @status.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Operation Accepted.", content = @Content(schema = @Schema(implementation = String.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = String.class), mediaType = MediaType.TEXT_HTML_VALUE)),
            @ApiResponse(responseCode = "404", description = "The specified resource was not found", content = @Content(schema = @Schema(implementation = String.class), mediaType = MediaType.TEXT_HTML_VALUE)),
            @ApiResponse(responseCode = "500", description = "Unknown or unexpected error encountered", content = @Content(schema = @Schema(implementation = String.class), mediaType = MediaType.TEXT_HTML_VALUE))
    })
    @PutMapping("/{id}")
    public Subscription updateStatus(@PathVariable Long id, @RequestParam String status) {
        return subscriptionService.changeStatus(id,status);
    }
}

