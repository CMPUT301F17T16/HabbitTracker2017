/*
*Habit
*
* version 1.0
*
* Dec 3, 2017
*
*Copyright (c) 2017 Team 16, CMPUT301, University of Alberta - All Rights Reserved.
*You may use, distribute, or modify this code under terms and conditions of the Code of Student Behavior at University of Alberta.
*You can find a copy of the license in this project. Otherwise please contact contact@abc.ca.
*
*/

package com.example.habittracker2017;

import android.provider.Settings;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

/**
 * Represents a class of habits, used to organize habit events.
 * It also tracks if a event can be created from this habit at the given time.
 * @author team 16
 * @version 1.0
 * @see HabitEvent
 * @since 1.0
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

    /**
     * Delete the event belong to this habit
     * @param event The event to detach
     */
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
        return this.title;       //This will probably get changed to a nicely formatted String later.
    }

    /**
     * Returns the title of this habit type
     * @return Title of this habit type
     */
    public String getTitle(){
        return title;
    }

    /**
     * Returns the reason of this habit type
     * @return Reason of this habit type
     */
    public String getReason(){
        return reason;
    }

    /**
     * Returns startDate of this habit type
     * @return StartDate of this habit type
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * Returns startDate of this habit type in string and specified date format
     * @return StartDate of this habit type in string and specified date format
     */
    public String getStartDateString() {return new SimpleDateFormat("yyyy-MM-dd").format(startDate);}

    /**
     * Returns the schedule of this habit type
     * @return Schedule of this habit type
     */
    public HashMap<Integer, Boolean> getSchedule() {
        return schedule;
    }

    /**
     * Return events of this habit type
     * @return Events of this habit type
     */
    public ArrayList<HabitEvent> getEvents() {
        return events;
    }

    /**
     * Set title of this habit type
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Set reason of this habit type
     * @param reason
     */
    public void setReason(String reason) {
        this.reason = reason;
    }

    /**
     * Set startDate of this habit type
     * @param startDate
     */
    public void setStartDate(Date startDate) {      //Used in testing, do not delete
        this.startDate = startDate;
    }

    /**
     * Set schedule of this habit type
     * @param schedule
     */
    public void setSchedule(HashMap<Integer, Boolean> schedule) {
        this.schedule = schedule;
    }

    /**
     * Set event of this habit type
     * @param events
     */
    public void setEvents(ArrayList<HabitEvent> events) {
        this.events = events;
    }
}
