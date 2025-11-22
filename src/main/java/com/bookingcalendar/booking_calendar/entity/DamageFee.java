package com.bookingcalendar.booking_calendar.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class DamageFee {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    private String description;
    private Double damageCost;
    private Double fineAmount;
    private Double totalFee;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="booking_id", nullable = false)
    private Booking booking;
}
