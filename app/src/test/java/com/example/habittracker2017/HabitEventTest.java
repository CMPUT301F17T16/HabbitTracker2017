package com.example.habittracker2017;

import android.location.Location;
import android.location.LocationManager;

import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Unit test for Habit event
 *
 * @author team 16
 * @version 1.0
 * @see HabitEvent
 * @since 1.0
 */

public class HabitEventTest {
    HashMap<Integer, Boolean> testSchedule = new HashMap<>();

    @Test
    public void testBasicEventCreation() {
        String testTitle = "Title";
        String testReason = "Reason";
        Habit habit = new Habit(testTitle, testReason, new Date(), testSchedule);
        String comment = "Test comment";
        HabitEvent event = new HabitEvent(comment);
        habit.addEvent(event);

        assertTrue(habit.getEvents().contains(event));
        assertEquals(event.getComment(), comment);
        assertFalse(event.getDate().after(new Date(System.currentTimeMillis())));
        assertTrue(event.getDate().after(new Date(System.currentTimeMillis() - 100))); //Or some other reasonable timeframe
    }

    @Test
    public void testBasicEventDelete(){
        Habit habit = new Habit("Title", "Reason", new Date(), testSchedule);
        String comment = "Test comment";
        HabitEvent event = new HabitEvent(comment);
        habit.addEvent(event);

        //Tests delete
        habit.deleteEvent(event);
        assertFalse(habit.getEvents().contains(event));
    }

    @Test
    public void testGetComment(){
        String comment = "Test comment";
        HabitEvent event = new HabitEvent(comment);

        assertEquals("wrong comment returned",comment,event.getComment());
    }

    @Test
    public void testSetComment(){
        String comment = "Original test comment";
        HabitEvent event = new HabitEvent(comment);
        String newComment = "New test comment";
        event.setComment(newComment);

        assertEquals("comment did not update", newComment, event.getComment());
    }

    @Test
    public void testGetLocation(){
        String comment = "Test comment";
        Location testLocation = null;
        HabitEvent event = new HabitEvent(comment, testLocation);

        assertEquals("wrong location gotten", testLocation, event.getLocation());
    }

    @Test
    public void testSetLocation(){
        String comment = "Test comment";
        Location testLocation=new Location(LocationManager.GPS_PROVIDER);
        testLocation.setLatitude(53.521);
        testLocation.setLongitude(-113.521);

        HabitEvent event = new HabitEvent(comment, testLocation);

        assertEquals("Wrong location set",testLocation,event.getLocation());

    }

    @Test
    public void testSetDate(){
        String comment = "Test comment";
        Location testLocation = null;
        HabitEvent event = new HabitEvent(comment, testLocation);

        String newTestDate = "22-11-2017";
        SimpleDateFormat converter = new SimpleDateFormat("dd-MM-yyyy");
        Date newDate = new Date();
        try {
            newDate = converter.parse(newTestDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        event.setDate(newDate);

        assertEquals("new date not set", newDate, event.getDate());
    }

    @Test
    public void testGetDate(){
        String comment = "Test comment";
        Location testLocation = null;
        HabitEvent event = new HabitEvent(comment, testLocation);
        Date newDate = new Date();

        assertEquals("date gotten is not the same as date set", newDate, event.getDate());

    }

    @Test
    public void testSetHabit() {
        Habit habit = new Habit("Title", "Reason", new Date(), testSchedule);
        String comment = "Test comment";
        Location testLocation = null;
        HabitEvent event = new HabitEvent(comment, testLocation);
        habit.addEvent(event);

        String newString = "New Title";
        event.setHabit(newString);
        assertEquals("new title not set", newString, event.getHabit());
    }

    @Test
    public void testGetAndSetBitmapString(){
        String comment = "Test comment";
        Location testLocation = null;
        HabitEvent event = new HabitEvent(comment, testLocation);

        String test = "testString";
        event.setBitmapString(test);

        assertEquals("bitmap string not getting string or not setting string",test, event.getBitmapString());
    }


}
