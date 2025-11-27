package com.bookingcalendar.booking_calendar.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookingcalendar.booking_calendar.entity.DamageFee;
import com.bookingcalendar.booking_calendar.repository.DamageFeeRepository;

@Service
public class DamageFeeService {

    @Autowired
    private DamageFeeRepository damageFeeRepository;

    public List<DamageFee> getAllDamageFees() {
        return damageFeeRepository.findAll();
    }

    public Optional<DamageFee> getDamageFeeById(Long id) {
        return damageFeeRepository.findById(id);
    }

    public DamageFee createDamageFee(DamageFee damageFee) {
        return damageFeeRepository.save(damageFee);
    }

    public Optional<DamageFee> updateDamageFee(Long id, DamageFee damageFeeDetails) {
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

            return damageFeeRepository.save(existingDamageFee);
        });
    }

    public boolean deleteDamageFee(Long id) {
        return damageFeeRepository.findById(id).map(fee -> {
            damageFeeRepository.delete(fee);
            return true;
        }).orElse(false);
    }
}
