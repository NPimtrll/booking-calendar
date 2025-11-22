package com.bookingcalendar.booking_calendar.repository;

import java.util.List;
import com.bookingcalendar.booking_calendar.entity.DamageFee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DamageFeeRepository extends JpaRepository<DamageFee, Long> {
    List<DamageFee> findByBookingId(Long bookingId);
}
