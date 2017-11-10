package com.example.habittracker2017;

import android.graphics.Bitmap;
import android.location.Location;
import android.os.Build;
import android.os.SystemClock;
import android.support.annotation.RequiresApi;

import org.junit.Test;

import java.io.File;
import java.util.Date;
import java.util.HashMap;

import static junit.framework.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

/**
 * Created by Alex on 2017-10-21.
 */

public class HabitTrackerUnitTest {

    private HashMap<Integer, Boolean> testSchedule;
    private HashMap<Integer, Boolean> inverseSchedule;
    private Date sunday;
    private Date monday;
    private Date tuesday;
    private Date wedsday;
    private Date thursday;
    private Date friday;
    private Date saterday;

    public HabitTrackerUnitTest() {

        testSchedule = new HashMap<>();
        inverseSchedule = new HashMap<>();

        testSchedule.put(0, false);
        testSchedule.put(1, true);
        testSchedule.put(2, false);
        testSchedule.put(3, true);
        testSchedule.put(4, false);
        testSchedule.put(5, true);
        testSchedule.put(6, false);

        inverseSchedule.put(0, true);
        inverseSchedule.put(1, false);
        inverseSchedule.put(2, true);
        inverseSchedule.put(3, false);
        inverseSchedule.put(4, true);
        inverseSchedule.put(5, false);
        inverseSchedule.put(6, true);

        sunday = new Date(117, 9, 1);
        monday = new Date(117, 9, 2);
        tuesday = new Date(117, 9, 3);
        wedsday = new Date(117, 9, 4);
        thursday = new Date(117, 9, 5);
        friday = new Date(117, 9, 6);
        saterday = new Date(117, 9, 7);
    }

    @Test
    public void testHabitCreation() {
        String title = "Title";
        String reason = "Reason";
        Date startDate = new Date();
        HashMap<Integer, Boolean> schedule = testSchedule;
        Habit habit = new Habit(title, reason, startDate, schedule);

        assertEquals(habit.getTitle(), title);
        assertEquals(habit.getReason(), reason);
        assertEquals(habit.getStartDate(), startDate);
        assertEquals(habit.getSchedule(), schedule);
        assertTrue(habit.getEvents().isEmpty());
    }

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

        //Tests delete
        event.delete();
        assertFalse(habit.getEvents().contains(event));
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Test
    public void testAdvancedEventCreation() {
        Habit habit = new Habit("Title", "Reason", new Date(), testSchedule);
        String comment = "Test comment";

        Location location = null;

        Bitmap picture = Bitmap.createBitmap(64, 64, Bitmap.Config.RGBA_F16);   //Or whatever format we end up using

        HabitEvent eventL = new HabitEvent(comment, location);
        HabitEvent eventP = new HabitEvent(comment, picture);
        HabitEvent eventPL = new HabitEvent(comment, picture, location);
        habit.addEvent(eventL);
        habit.addEvent(eventP);
        habit.addEvent(eventPL);

        assertTrue(habit.getEvents().contains(eventL));
        assertTrue(habit.getEvents().contains(eventP));
        assertTrue(habit.getEvents().contains(eventPL));

        assertEquals(eventL.getLocation(), location);
        assertEquals(eventPL.getLocation(), location);
        assertEquals(eventP.getPicture(), picture);
        assertEquals(eventPL.getPicture(), picture);
    }

    @Test
    public void testGetLastEvent(){
        Habit habit = new Habit("Title", "Reason", new Date(), testSchedule);

        HabitEvent firstEvent = new HabitEvent("Test");
        HabitEvent middleEvent = new HabitEvent("Test");
        HabitEvent lastEvent = new HabitEvent("Test");
        middleEvent.setDate(new Date(System.currentTimeMillis() + 100));
        lastEvent.setDate(new Date(System.currentTimeMillis() + 200));

        habit.addEvent(firstEvent);
        habit.addEvent(lastEvent);
        habit.addEvent(middleEvent);

        assertEquals(habit.getLastEvent(), lastEvent);
    }

    @Test
    public void testIsDue(){
        Habit habit = new Habit("Title", "Reason", sunday, testSchedule);
        Habit otherHabit = new Habit("Title", "Reason", sunday, inverseSchedule);

        assertFalse(habit.isDue(sunday));
        assertTrue(otherHabit.isDue(sunday));

        assertTrue(habit.isDue(monday));
        assertFalse(otherHabit.isDue(monday));

        assertFalse(habit.isDue(tuesday));
        assertTrue(otherHabit.isDue(tuesday));

        assertTrue(habit.isDue(wedsday));
        assertFalse(otherHabit.isDue(wedsday));

        assertFalse(habit.isDue(thursday));
        assertTrue(otherHabit.isDue(thursday));

        assertTrue(habit.isDue(friday));
        assertFalse(otherHabit.isDue(friday));

        assertFalse(habit.isDue(saterday));
        assertTrue(otherHabit.isDue(saterday));

        habit.setStartDate(friday);
        assertFalse(habit.isDue(monday));
    }

    @Test
    public void testCreateUser(){
        String name = "Test";
        User user = new User(name);

        assertEquals(user.getName(), name);
        assertTrue(user.getHabits().isEmpty());
        assertTrue(user.getFollowing().isEmpty());
        assertTrue(user.getFollowers().isEmpty());
        assertTrue(user.getRequests().isEmpty());
    }
}
