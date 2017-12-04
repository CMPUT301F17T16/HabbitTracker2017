/*
*HabitEvent
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

import android.graphics.Bitmap;
import android.location.Location;

import java.util.Date;

/**
 * Represents a single event of a habit.
 * @author team 16
 * @version 1.0
 * @see Habit
 * @since 1.0
 */

public class HabitEvent {
    private String comment;
    private Location location;
    private Date date;
    private String habit;
    private String bitmapString;

    /**
     * Creates a new event with a given comment.
     * After creating an event, it must be associated with a habit with Habit.addEvent.
     * @param comment The comment to use.
     */
    HabitEvent(String comment){
        this.comment = comment;
        this.location = null;
        this.date = new Date();
        this.bitmapString=null;
    }

    /**
     * Creates a new event with a given comment and picture.
     * After creating an event, it must be associated with a habit with Habit.addEvent.
     * @param comment The comment to use.
     * @param bitmapString The picture to use.
     */
    HabitEvent(String comment, String bitmapString){
        this.comment = comment;
        this.location = null;
        this.date = new Date();
        this.bitmapString=bitmapString;
    }

    /**
     * Creates a new event with a given comment and location.
     * After creating an event, it must be associated with a habit with Habit.addEvent.
     * @param comment The comment to use.
     * @param location The location to use.
     */
    HabitEvent(String comment, Location location){
        this.comment = comment;
        this.location = location;
        this.date = new Date();
        this.bitmapString=null;
    }

    /**
     * Creates a new event with a given comment, picture, and location.
     * After creating an event, it must be associated with a habit with Habit.addEvent.
     * @param comment The comment to use.
     * @param bitmapString The picture to use.
     * @param location The location to use.
     */
    HabitEvent(String comment, Location location, String bitmapString){
        this.comment = comment;
        this.location = location;
        this.date = new Date();
        this.bitmapString=bitmapString;
    }


    /**
     * Returns comment of this habit event
     * @return Comment of this habit event
     */
    public String getComment() {
        return comment;
    }

    /**
     * Returns location of this habit event
     * @return Location of this habit event
     */
    public Location getLocation() {
        return location;
    }

    /**
     * Returns date of this habit event
     * @return Date of this habit event
     */
    public Date getDate() {
        return date;
    }

    /**
     * Returns habit event's habit title
     * @return Habit event's habit title
     */
    public String getHabit() {
        return habit;
    }

    /**
     * Returns bitmapString of this habit event
     * @return bitmapString of this habit event
     */
    public String getBitmapString(){return bitmapString;}

    /**
     * Set comment of this habit event
     * @param comment
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * Set location of this habit event
     * @param location
     */
    public void setLocation(Location location) {
        this.location = location;
    }

    /**
     * Set date of this habit event
     * @param date
     */
    public void setDate(Date date) {        //Used for testing purposes, so don't delete
        this.date = date;
    }

    /**
     * Set title of this habit event
     * @param habit
     */
    public void setHabit(String habit) {
        this.habit = habit;
    }

    /**
     * Set bitmapString of this habit event
     * @param bitmapString
     */
    public void setBitmapString(String bitmapString){this.bitmapString=bitmapString;}
}
