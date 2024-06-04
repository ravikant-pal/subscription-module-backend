package com.trivago.subscription.repository;

import com.trivago.subscription.dto.Status;
import com.trivago.subscription.model.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    List<Subscription> findByHotelId(long hotelId);

    List<Subscription> findAllByOrderByStartDateDesc();

    List<Subscription> findByEndDateLessThanEqual(LocalDate date);

    List<Subscription> findAllByStatusOrderByStartDateDesc(Status status);

    List<Subscription> findAllByStartDateBetweenOrderByStartDateDesc(LocalDate startDate, LocalDate endDate);

    List<Subscription> findAllByStatusAndStartDateBetweenOrderByStartDateDesc(Status status, LocalDate startDate, LocalDate endDate);

}

