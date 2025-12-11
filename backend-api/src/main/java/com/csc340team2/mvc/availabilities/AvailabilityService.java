package com.csc340team2.mvc.availabilities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AvailabilityService {
    @Autowired
    private AvailabilityRepository availabilityRepository;

    public CoachAvailability saveAvailability(CoachAvailability availability){
        return availabilityRepository.save(availability);
    }
    public void deleteAvailability(Long id){
        availabilityRepository.deleteById(id);
    }
}