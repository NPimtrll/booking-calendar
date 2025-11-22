package com.bookingcalendar.booking_calendar.entity;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String guestName;

    private String phoneNumber;

    private LocalDate checkInDate;

    private LocalDate checkOutDate;

    private LocalDate checkOutTime;

    private Double bookingPrice;

    private Double depositAmount;

    private Double remainingPayment;

    private Double balanceDue;
}
