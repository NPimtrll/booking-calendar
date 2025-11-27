package com.bookingcalendar.booking_calendar.controller;

import com.bookingcalendar.booking_calendar.entity.Booking;
import com.bookingcalendar.booking_calendar.entity.DamageFee;
import com.bookingcalendar.booking_calendar.service.DamageFeeService;
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

import java.util.Arrays;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DamageFeeController.class)
public class DamageFeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DamageFeeService damageFeeService;

    @Autowired
    private ObjectMapper objectMapper;

    private DamageFee damageFee;
    private Booking booking;

    @BeforeEach
    void setUp() {
        booking = new Booking();
        booking.setId(1L);
        booking.setGuestName("John Doe");

        damageFee = new DamageFee();
        damageFee.setId(1L);
        damageFee.setDescription("Broken lamp");
        damageFee.setDamageCost(500.0);
        damageFee.setFineAmount(100.0);
        damageFee.setTotalFee(600.0);
        damageFee.setBooking(booking);
    }

    @Test
    void createDamageFee_ShouldReturnCreatedDamageFee() throws Exception {
        Mockito.when(damageFeeService.createDamageFee(any(DamageFee.class))).thenReturn(damageFee);

        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/damagefee")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(damageFee)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description", is(damageFee.getDescription())));
    }

    @Test
    void getAllDamageFees_ShouldReturnAllDamageFees() throws Exception {
        Mockito.when(damageFeeService.getAllDamageFees()).thenReturn(Arrays.asList(damageFee));

        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/damagefee")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].description", is(damageFee.getDescription())));
    }

    @Test
    void getDamageFeeById_WithValidId_ShouldReturnDamageFee() throws Exception {
        Mockito.when(damageFeeService.getDamageFeeById(1L)).thenReturn(Optional.of(damageFee));

        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/damagefee/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description", is(damageFee.getDescription())));
    }

    @Test
    void getDamageFeeById_WithInvalidId_ShouldReturnNotFound() throws Exception {
        Mockito.when(damageFeeService.getDamageFeeById(999L)).thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/damagefee/{id}", 999L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void updateDamageFee_WithValidId_ShouldReturnUpdatedDamageFee() throws Exception {
        // Create update data
        DamageFee updateData = new DamageFee();
        updateData.setDescription("Updated damage description");
        updateData.setDamageCost(600.0);
        updateData.setFineAmount(100.0);
        updateData.setTotalFee(700.0);
        updateData.setBooking(booking);

        // Mock the updated damage fee that will be returned
        DamageFee updatedDamageFee = new DamageFee();
        updatedDamageFee.setId(1L);
        updatedDamageFee.setDescription("Updated damage description");
        updatedDamageFee.setDamageCost(600.0);
        updatedDamageFee.setFineAmount(100.0);
        updatedDamageFee.setTotalFee(700.0);
        updatedDamageFee.setBooking(booking);

        Mockito.when(damageFeeService.updateDamageFee(anyLong(), any(DamageFee.class)))
                .thenReturn(Optional.of(updatedDamageFee));

        mockMvc.perform(MockMvcRequestBuilders
                .put("/api/damagefee/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateData)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description", is("Updated damage description")))
                .andExpect(jsonPath("$.totalFee", is(700.0)));
    }

    @Test
    void updateDamageFee_WithInvalidId_ShouldReturnNotFound() throws Exception {
        Mockito.when(damageFeeService.updateDamageFee(anyLong(), any(DamageFee.class))).thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders
                .put("/api/damagefee/{id}", 999L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(damageFee)))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteDamageFee_WithValidId_ShouldReturnOk() throws Exception {
        Mockito.when(damageFeeService.deleteDamageFee(1L)).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders
                .delete("/api/damagefee/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void deleteDamageFee_WithInvalidId_ShouldReturnNotFound() throws Exception {
        Mockito.when(damageFeeService.deleteDamageFee(999L)).thenReturn(false);

        mockMvc.perform(MockMvcRequestBuilders
                .delete("/api/damagefee/{id}", 999L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
