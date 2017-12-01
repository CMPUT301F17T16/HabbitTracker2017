package com.example.habittracker2017;

import android.graphics.Bitmap;
import android.location.Location;

import java.util.Date;

/**
 * Created by Alex on 2017-10-20.
 * Represents a single event of a habit.
 */

public class HabitEvent {
    private String comment;
    private Bitmap picture;
    private Location location;
    private Date date;
    private String habit;

    /**
     * Creates a new event with a given comment.
     * After creating an event, it must be associated with a habit with Habit.addEvent.
     * @param comment The comment to use.
     */
    HabitEvent(String comment){
        this.comment = comment;
        this.picture = null;
        this.location = null;
        this.date = new Date();
    }

    /**
     * Creates a new event with a given comment and picture.
     * After creating an event, it must be associated with a habit with Habit.addEvent.
     * @param comment The comment to use.
     * @param picture The picture to use.
     */
    HabitEvent(String comment, Bitmap picture){
        this.comment = comment;
        this.picture = picture;
        this.location = null;
        this.date = new Date();
    }

    /**
     * Creates a new event with a given comment and location.
     * After creating an event, it must be associated with a habit with Habit.addEvent.
     * @param comment The comment to use.
     * @param location The location to use.
     */
    HabitEvent(String comment, Location location){
        this.comment = comment;
        this.picture = null;
        this.location = location;
        this.date = new Date();
    }

    /**
     * Creates a new event with a given comment, picture, and location.
     * After creating an event, it must be associated with a habit with Habit.addEvent.
     * @param comment The comment to use.
     * @param picture The picture to use.
     * @param location The location to use.
     */
    HabitEvent(String comment, Bitmap picture, Location location){
        this.comment = comment;
        this.picture = picture;
        this.location = location;
        this.date = new Date();
    }



    public String getComment() {
        return comment;
    }

    public Bitmap getPicture() {
        return picture;
    }

    public Location getLocation() {
        return location;
    }

    public Date getDate() {
        return date;
    }

    public String getHabit() {
        return habit;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setPicture(Bitmap picture) {
        this.picture = picture;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void setDate(Date date) {        //Used for testing purposes, so don't delete
        this.date = date;
    }

    public void setHabit(String habit) {
        this.habit = habit;
    }
}
