package com.bookingcalendar.booking_calendar.repository;

import com.bookingcalendar.booking_calendar.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long>{
    
}
