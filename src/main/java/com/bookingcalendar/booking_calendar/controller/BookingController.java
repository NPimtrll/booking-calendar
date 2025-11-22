package com.bookingcalendar.booking_calendar.controller;

import com.bookingcalendar.booking_calendar.entity.Booking;

import java.util.List;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookingcalendar.booking_calendar.repository.BookingRepository;

@RestController
@RequestMapping("/api/booking")
public class BookingController {
    @Autowired
    private BookingRepository bookingRepository;

    @PostMapping
    private Booking createBooking(@RequestBody Booking booking) {
        return bookingRepository.save(booking);
    }

    @GetMapping
    private List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    @GetMapping("/id")
    private ResponseEntity<Booking> getBooking(Long id) {
        return bookingRepository.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/id")
    private ResponseEntity<Booking> updateBooking(Long id, @RequestBody Booking booking) {
        return bookingRepository.findById(id).map(Booking -> {
            booking.setGuestName(booking.getGuestName());
            booking.setPhoneNumber(booking.getPhoneNumber());
            booking.setCheckInDate(booking.getCheckInDate());
            booking.setCheckOutDate(booking.getCheckOutDate());
            booking.setCheckOutTime(booking.getCheckOutTime());
            booking.setBookingPrice(booking.getBookingPrice());
            booking.setDepositAmount(booking.getDepositAmount());
            booking.setBalanceDue(booking.getBalanceDue());

            Booking updatedBooking = bookingRepository.save(booking);
                    return ResponseEntity.ok(updatedBooking);
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBooking(@PathVariable Long id) {
        return bookingRepository.findById(id)
                .map(booking -> {
                    bookingRepository.delete(booking);
                    return ResponseEntity.ok().build();
                }).orElse(ResponseEntity.notFound().build());
    }

}
