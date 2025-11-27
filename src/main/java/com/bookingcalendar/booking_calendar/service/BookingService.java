package com.bookingcalendar.booking_calendar.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookingcalendar.booking_calendar.entity.Booking;
import com.bookingcalendar.booking_calendar.repository.BookingRepository;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    public Optional<Booking> getBookingById(Long id) {
        return bookingRepository.findById(id);
    }

    public Booking createBooking(Booking booking) {
        return bookingRepository.save(booking);
    }

    public Optional<Booking> updateBooking(Long id, Booking bookingDetails) {
        return bookingRepository.findById(id).map(existingBooking -> {
            existingBooking.setGuestName(bookingDetails.getGuestName());
            existingBooking.setPhoneNumber(bookingDetails.getPhoneNumber());
            existingBooking.setCheckInDate(bookingDetails.getCheckInDate());
            existingBooking.setCheckOutDate(bookingDetails.getCheckOutDate());
            existingBooking.setCheckOutTime(bookingDetails.getCheckOutTime());
            existingBooking.setBookingPrice(bookingDetails.getBookingPrice());
            existingBooking.setDepositAmount(bookingDetails.getDepositAmount());
            existingBooking.setBalanceDue(bookingDetails.getBalanceDue());

            return bookingRepository.save(existingBooking);
        });
    }

    public boolean deleteBooking(Long id) {
        return bookingRepository.findById(id).map(booking -> {
            bookingRepository.delete(booking);
            return true;
        }).orElse(false);
    }
}
