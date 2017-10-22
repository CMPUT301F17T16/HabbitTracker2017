package com.example.habittracker2017;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by Alex on 2017-10-20.
 */

public class Habit {
    private String title;
    private String reason;
    private Date startDate;
    private HashMap<String, Boolean> schedule;
    private ArrayList<HabitEvent> events;

    Habit(String title, String reason, Date startDate, HashMap<String, Boolean> schedule){
        //TODO
    }

    public void addEvent(HabitEvent event){
        //TODO
    }

    public HabitEvent getLastEvent(){
        return null; //TODO
    }

    public boolean isDue(){
        return false; //TODO
    }

    @Override
    public String toString() {
        return null; //TODO
    }

    public String getTitle(){
        return title;
    }

    public String getReason(){
        return reason;
    }

    public Date getStartDate() {
        return startDate;
    }

    public HashMap<String, Boolean> getSchedule() {
        return schedule;
    }

    public ArrayList<HabitEvent> getEvents() {
        return events;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public void setStartDate(Date startDate) {      //Used in testing, do not delete
        this.startDate = startDate;
    }

    public void setSchedule(HashMap<String, Boolean> schedule) {
        this.schedule = schedule;
    }

    public void setEvents(ArrayList<HabitEvent> events) {
        this.events = events;
    }
}
