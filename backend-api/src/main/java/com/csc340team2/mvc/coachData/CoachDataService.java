package com.csc340team2.mvc.coachData;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CoachDataService {
    @Autowired
    private CoachDataRepository coachDataRepository;
    public CoachData saveCoachData(CoachData data){
        return coachDataRepository.save(data);
    }
}
