package com.csc340team2.mvc.availabilities;

import com.csc340team2.mvc.account.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public List<CoachAvailability> getAvailabilitiesForCoach(Account coach){
        return availabilityRepository.getAllByCoach(coach);
    }
}
