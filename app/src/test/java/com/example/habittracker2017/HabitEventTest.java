package com.example.habittracker2017;

import android.location.Location;

import org.junit.Test;

import java.util.Date;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by jmark on 2017-12-04.
 */

public class HabitEventTest {
    HashMap<Integer, Boolean> testSchedule = new HashMap<>();

    @Test
    public void testBasicEventCreation() {
        Habit habit = new Habit("Title", "Reason", new Date(), testSchedule);
        String comment = "Test comment";
        HabitEvent event = new HabitEvent(comment);
        habit.addEvent(event);

        assertTrue(habit.getEvents().contains(event));
        assertEquals(event.getHabit(), habit);
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
        Habit habit = new Habit("Title", "Reason", new Date(), testSchedule);
        String comment = "Test comment";
        HabitEvent event = new HabitEvent(comment);
        habit.addEvent(event);

        assertEquals("wrong comment returned",comment,event.getComment());
    }

    @Test
    public void testSetComment(){
        Habit habit = new Habit("Title", "Reason", new Date(), testSchedule);
        String comment = "Original test comment";
        HabitEvent event = new HabitEvent(comment);
        habit.addEvent(event);
        String newComment = "New test comment";
        event.setComment(newComment);

        assertEquals("comment did not update", newComment, event.getComment());
    }

    @Test
    public void testGetLocation(){
        Habit habit = new Habit("Title", "Reason", new Date(), testSchedule);
        String comment = "Test comment";
        Location testLocation = null;
        HabitEvent event = new HabitEvent(comment, testLocation);
        habit.addEvent(event);

        assertEquals("wrong location gotten", testLocation, event.getLocation());
    }

}
