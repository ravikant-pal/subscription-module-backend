package com.trivago.subscription.repository;

import com.trivago.subscription.dto.Status;
import com.trivago.subscription.model.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    List<Subscription> findAllByOrderByStartDateDesc();

    List<Subscription> findAllByStatusOrderByStartDateDesc(Status status);

    List<Subscription> findAllByStartDateOrderByStartDateDesc(LocalDate startDate);

    List<Subscription> findAllByStatusAndStartDateOrderByStartDateDesc(Status status, LocalDate startDate);

    List<Subscription> findByHotelId(long hotelId);
}

