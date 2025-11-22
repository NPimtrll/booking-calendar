package com.bookingcalendar.booking_calendar.controller;

import java.util.List;

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

import com.bookingcalendar.booking_calendar.entity.DamageFee;
import com.bookingcalendar.booking_calendar.repository.DamageFeeRepository;

@RestController
@RequestMapping("/api/damagefee")
public class DamageFeeController {

    @Autowired
    private DamageFeeRepository damageFeeRepository;

    @PostMapping
    private DamageFee createDamageFee(@RequestBody DamageFee damagefee) {
        return damageFeeRepository.save(damagefee);
    }

    @GetMapping
    private List<DamageFee> getAllDamagefee() {
        return damageFeeRepository.findAll();
    }

    @GetMapping("/{id}")
    private ResponseEntity<DamageFee> getDamageFeeById(Long id) {
        return damageFeeRepository.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<DamageFee> updateDamageFee(Long id, @RequestBody DamageFee damageFeeDetails) {
        return damageFeeRepository.findById(id).map(existingDamageFee -> {
            if (damageFeeDetails.getDescription() != null) {
                existingDamageFee.setDescription(damageFeeDetails.getDescription());
            }
            if (damageFeeDetails.getDamageCost() != null) {
                existingDamageFee.setDamageCost(damageFeeDetails.getDamageCost());
            }
            if (damageFeeDetails.getFineAmount() != null) {
                existingDamageFee.setFineAmount(damageFeeDetails.getFineAmount());
            }
            if (damageFeeDetails.getTotalFee() != null) {
                existingDamageFee.setTotalFee(damageFeeDetails.getTotalFee());
            }
            if (damageFeeDetails.getBooking() != null) {
                existingDamageFee.setBooking(damageFeeDetails.getBooking());
            }

            DamageFee updatedDamageFee = damageFeeRepository.save(existingDamageFee);
            return ResponseEntity.ok(updatedDamageFee);
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDamageFee(@PathVariable Long id) {
        return damageFeeRepository.findById(id)
                .map(booking -> {
                    damageFeeRepository.delete(booking);
                    return ResponseEntity.ok().build();
                }).orElse(ResponseEntity.notFound().build());
    }

}
