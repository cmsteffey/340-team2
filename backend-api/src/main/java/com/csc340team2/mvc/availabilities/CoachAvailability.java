package com.csc340team2.mvc.availabilities;

import com.csc340team2.mvc.account.Account;
import jakarta.persistence.*;

@Entity
public class CoachAvailability {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer weekday;

    //Seconds after midnight
    private Integer startTime;

    //seconds
    private Integer length;

    @ManyToOne(targetEntity = Account.class)
    @JoinColumn(name = "coach_id", nullable = false)
    private Account coach;


    public Long getId(){
        return id;
    }
    public void setId(Long id){
        this.id = id;
    }
    public Integer getWeekday(){
        return weekday;
    }
    public void setWeekday(Integer weekday){
        this.weekday = weekday;
    }
    public Integer getLength(){
        return length;
    }
    public void setLength(Integer length){
        this.length = length;
    }

    public Integer getStartTime() {
        return startTime;
    }

    public void setStartTime(Integer startTime) {
        this.startTime = startTime;
    }

    public void setCoach(Account coach){
        this.coach = coach;
    }

    public Account getCoach(){
        return coach;
    }
}
