package com.bookingcalendar.booking_calendar.controller;

import com.bookingcalendar.booking_calendar.entity.Booking;
import com.bookingcalendar.booking_calendar.service.BookingService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

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
    private BookingService bookingService;

    @Autowired
    private ObjectMapper objectMapper;

    private Booking booking;

    @BeforeEach
    void setUp() {
        booking = new Booking();
        booking.setId(1L);
        booking.setGuestName("John Doe");
        booking.setPhoneNumber("1234567890");
        booking.setCheckInDate(LocalDate.of(2024, 1, 1));
        booking.setCheckOutDate(LocalDate.of(2024, 1, 3));
        booking.setCheckOutTime(LocalDate.of(2024, 1, 3));
        booking.setBookingPrice(300.0);
        booking.setDepositAmount(50.0);
        booking.setBalanceDue(250.0);
    }

    @Test
    void createBooking_ShouldReturnCreatedBooking() throws Exception {
        Mockito.when(bookingService.createBooking(any(Booking.class))).thenReturn(booking);

        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/booking")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(booking)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.guestName", is(booking.getGuestName())));
    }

    @Test
    void getAllBookings_ShouldReturnAllBookings() throws Exception {
        Mockito.when(bookingService.getAllBookings()).thenReturn(Arrays.asList(booking));

        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/booking")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].guestName", is(booking.getGuestName())));
    }

    @Test
    void getBooking_WithValidId_ShouldReturnBooking() throws Exception {
        Mockito.when(bookingService.getBookingById(1L)).thenReturn(Optional.of(booking));

        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/booking/id?id=1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.guestName", is(booking.getGuestName())));
    }

    @Test
    void getBooking_WithInvalidId_ShouldReturnNotFound() throws Exception {
        Mockito.when(bookingService.getBookingById(999L)).thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/booking/id?id=999")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void updateBooking_WithValidId_ShouldReturnUpdatedBooking() throws Exception {
        Booking updatedBooking = new Booking();
        updatedBooking.setId(1L);
        updatedBooking.setGuestName("Jane Doe");
        updatedBooking.setPhoneNumber("0987654321");
        updatedBooking.setCheckInDate(LocalDate.of(2024, 2, 1));
        updatedBooking.setCheckOutDate(LocalDate.of(2024, 2, 3));
        updatedBooking.setCheckOutTime(LocalDate.of(2024, 2, 3));
        updatedBooking.setBookingPrice(400.0);
        updatedBooking.setDepositAmount(60.0);
        updatedBooking.setBalanceDue(340.0);

        Mockito.when(bookingService.updateBooking(anyLong(), any(Booking.class)))
                .thenReturn(Optional.of(updatedBooking));

        mockMvc.perform(MockMvcRequestBuilders
                .put("/api/booking/id?id=1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedBooking)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.guestName", is("Jane Doe")))
                .andExpect(jsonPath("$.bookingPrice", is(400.0)));
    }

    @Test
    void updateBooking_WithInvalidId_ShouldReturnNotFound() throws Exception {
        Mockito.when(bookingService.updateBooking(anyLong(), any(Booking.class))).thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders
                .put("/api/booking/id?id=999")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(booking)))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteBooking_WithValidId_ShouldReturnOk() throws Exception {
        Mockito.when(bookingService.deleteBooking(1L)).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders
                .delete("/api/booking/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void deleteBooking_WithInvalidId_ShouldReturnNotFound() throws Exception {
        Mockito.when(bookingService.deleteBooking(999L)).thenReturn(false);

        mockMvc.perform(MockMvcRequestBuilders
                .delete("/api/booking/999")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
