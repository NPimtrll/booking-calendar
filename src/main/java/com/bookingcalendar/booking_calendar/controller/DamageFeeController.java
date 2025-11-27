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
import com.bookingcalendar.booking_calendar.service.DamageFeeService;

@RestController
@RequestMapping("/api/damagefee")
public class DamageFeeController {

    @Autowired
    private DamageFeeService damageFeeService;

    @PostMapping
    private DamageFee createDamageFee(@RequestBody DamageFee damagefee) {
        return damageFeeService.createDamageFee(damagefee);
    }

    @GetMapping
    private List<DamageFee> getAllDamagefee() {
        return damageFeeService.getAllDamageFees();
    }

    @GetMapping("/{id}")
    private ResponseEntity<DamageFee> getDamageFeeById(@PathVariable Long id) {
        return damageFeeService.getDamageFeeById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<DamageFee> updateDamageFee(@PathVariable Long id, @RequestBody DamageFee damageFeeDetails) {
        return damageFeeService.updateDamageFee(id, damageFeeDetails)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDamageFee(@PathVariable Long id) {
        if (damageFeeService.deleteDamageFee(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
