package com.example.habittracker2017;

import android.provider.Settings;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by Alex on 2017-10-20.
 * Represents a class of habits, used to organize habit events.
 * It also tracks if a event can be created from this habit at the given time.
 */

public class Habit {
    private String title;
    private String reason;
    private Date startDate;
    private HashMap<Integer, Boolean> schedule;
    private ArrayList<HabitEvent> events;

    /**
     * Creates a new Habit type.
     * @param title The name of this habit.
     * @param reason The given reason for this habit.
     * @param startDate The date after which new habit events can be made.
     * @param schedule A map representing the days the habit is due on.
     */
    Habit(String title, String reason, Date startDate, HashMap<Integer, Boolean> schedule){
        this.title = title;
        this.reason = reason;
        this.startDate = startDate;
        this.schedule = schedule;
        this.events = new ArrayList<>();
    }

    /**
     * Registers an event as belonging to this habit, as a two-way relationship.
     * @param event The event to register.
     */
    public void addEvent(HabitEvent event){
        events.add(event);
    }

    public void deleteEvent(HabitEvent event){
        events.remove(event);
    }

    /**
     * Returns the most recent event registered with the habit.
     * Returns null if there are no events for this habit.
     * @return The event in events with the most recent date field.
     */
    public HabitEvent getLastEvent(){
        if(events.size() == 0){
            return null;
        } else {
            HabitEvent event = events.get(0);
            for(int i=1;i<events.size();i++){
                if(event.getDate().before(events.get(i).getDate())){
                    event = events.get(i);
                }
            }
            return event;
        }
    }

    /**
     * Returns true iff this habit is scheduled to be done today.
     * @return True if this habit is due, false otherwise.
     */
    public boolean isDue(){
        Date today = new Date();
        if(today.before(startDate)){
            return false;
        } else {
            Calendar c = Calendar.getInstance();
            c.setTime(today);
            int weekDay = c.get(Calendar.DAY_OF_WEEK);
            if (this.schedule.get(weekDay)==null) {
                return false;
            }else{
                return this.schedule.get(weekDay);
            }
        }
    }

    /**
     * Returns a String representation of this habit type.
     * @return String representation of this habit type.
     */
    @Override
    public String toString() {
        return "Title:"+ this.title;       //This will probably get changed to a nicely formatted String later.
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

    public HashMap<Integer, Boolean> getSchedule() {
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

    public void setSchedule(HashMap<Integer, Boolean> schedule) {
        this.schedule = schedule;
    }

    public void setEvents(ArrayList<HabitEvent> events) {
        this.events = events;
    }
}
