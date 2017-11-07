package com.example.habittracker2017;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;

import java.io.File;
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
    private Habit habit;

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
    HabitEvent(String comment, File picture){
        this.comment = comment;
        this.picture = scaledownPicture(picture);
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
    HabitEvent(String comment, File picture, Location location){
        this.comment = comment;
        this.picture = scaledownPicture(picture);
        this.location = location;
        this.date = new Date();
    }

    /**
     * Deletes this event from its parent habit.
     */
    public void delete(){
        habit.getEvents().remove(this);
    }

    /**
     * Converts an image file to a scaled down bitmap to reduce storage size.
     * @param file File to convert.
     * @return Small bitmap.
     */
    private Bitmap scaledownPicture(File file){           //This should be moved to a control class
        Bitmap image = BitmapFactory.decodeFile(file.getAbsolutePath());
        return Bitmap.createScaledBitmap(image, 64, 64, false);
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

    public Habit getHabit() {
        return habit;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setPicture(File picture) {
        this.picture = scaledownPicture(picture);
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void setDate(Date date) {        //Used for testing purposes, so don't delete
        this.date = date;
    }

    public void setHabit(Habit habit) {
        this.habit = habit;
    }
}
