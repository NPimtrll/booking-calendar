package com.bookingcalendar.booking_calendar.controller;

import com.bookingcalendar.booking_calendar.entity.Booking;
import com.bookingcalendar.booking_calendar.repository.BookingRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookingController.class)
public class BookingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookingRepository bookingRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private Booking booking;

    @BeforeEach
    void setUp() {
        booking = new Booking();
        booking.setId(1L);
        booking.setGuestName("John Doe");
        booking.setPhoneNumber("0812345678");
        booking.setCheckInDate(LocalDate.of(2025, 12, 1));
        booking.setCheckOutDate(LocalDate.of(2025, 12, 5));
        booking.setCheckOutTime(LocalDate.of(2025, 12, 5));
        booking.setBookingPrice(5000.0);
        booking.setDepositAmount(2000.0);
        booking.setRemainingPayment(3000.0);
        booking.setBalanceDue(3000.0);
    }

    @Test
    void createBooking_ShouldReturnCreatedBooking() throws Exception {
        Mockito.when(bookingRepository.save(any(Booking.class))).thenReturn(booking);

        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/booking")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(booking)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.guestName", is(booking.getGuestName())))
                .andExpect(jsonPath("$.phoneNumber", is(booking.getPhoneNumber())));
    }

    @Test
    void getAllBookings_ShouldReturnAllBookings() throws Exception {
        Mockito.when(bookingRepository.findAll()).thenReturn(Arrays.asList(booking));

        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/booking")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].guestName", is(booking.getGuestName())));
    }

    @Test
    void getBooking_WithValidId_ShouldReturnBooking() throws Exception {
        Mockito.when(bookingRepository.findById(1L)).thenReturn(Optional.of(booking));

        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/booking/id")
                .param("id", "1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.guestName", is(booking.getGuestName())));
    }

    @Test
    void getBooking_WithInvalidId_ShouldReturnNotFound() throws Exception {
        Mockito.when(bookingRepository.findById(anyLong())).thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/booking/id")
                .param("id", "999")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void updateBooking_WithValidId_ShouldReturnUpdatedBooking() throws Exception {
        Mockito.when(bookingRepository.findById(1L)).thenReturn(Optional.of(booking));
        Mockito.when(bookingRepository.save(any(Booking.class))).thenReturn(booking);

        booking.setGuestName("Updated Name");

        mockMvc.perform(MockMvcRequestBuilders
                .put("/api/booking/id")
                .param("id", "1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(booking)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.guestName", is("Updated Name")));
    }

    @Test
    void deleteBooking_WithValidId_ShouldReturnOk() throws Exception {
        Mockito.when(bookingRepository.findById(1L)).thenReturn(Optional.of(booking));
        Mockito.doNothing().when(bookingRepository).delete(any(Booking.class));

        mockMvc.perform(MockMvcRequestBuilders
                .delete("/api/booking/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void deleteBooking_WithInvalidId_ShouldReturnNotFound() throws Exception {
        Mockito.when(bookingRepository.findById(999L)).thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders
                .delete("/api/booking/999")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
