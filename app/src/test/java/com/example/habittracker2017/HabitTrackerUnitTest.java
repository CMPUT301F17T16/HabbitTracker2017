package com.example.habittracker2017;

import android.location.Location;
import android.os.SystemClock;

import org.junit.Test;

import java.io.File;
import java.util.Date;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

/**
 * Created by Alex on 2017-10-21.
 */

public class HabitTrackerUnitTest {

    private HashMap<String, Boolean> testSchedule;
    private HashMap<String, Boolean> inverseSchedule;
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

        testSchedule.put("sun", false);     //Not sure exactly how we're going to implement these maps
        testSchedule.put("mon", true);
        testSchedule.put("tue", false);
        testSchedule.put("wed", true);
        testSchedule.put("thr", false);
        testSchedule.put("fri", true);
        testSchedule.put("sat", false);

        inverseSchedule.put("sun", true);
        inverseSchedule.put("mon", false);
        inverseSchedule.put("tue", true);
        inverseSchedule.put("wed", false);
        inverseSchedule.put("thr", true);
        inverseSchedule.put("fri", false);
        inverseSchedule.put("sat", true);

        sunday = new Date(117, 10, 1);
        monday = new Date(117, 10, 2);
        tuesday = new Date(117, 10, 3);
        wedsday = new Date(117, 10, 4);
        thursday = new Date(117, 10, 5);
        friday = new Date(117, 10, 6);
        saterday = new Date(117, 10, 7);
    }

    @Test
    public void testHabitCreation() {
        String title = "Title";
        String reason = "Reason";
        Date startDate = new Date();
        HashMap<String, Boolean> schedule = testSchedule;
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

    @Test
    public void testAdvancedEventCreation() {
        Habit habit = new Habit("Title", "Reason", new Date(), testSchedule);
        String comment = "Test comment";

        Location location = null;

        File picture = new File("TestImage.png");   //Or .jpg or any whatever format we end up using

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
        assertTrue(eventP.getPicture().exists());           //Confirms scaled-down pictures are used
        assertNotSame(eventP.getPicture(), picture);        //instead of the original
        assertTrue(eventP.getPicture().getTotalSpace() < 65536);
        assertTrue(eventPL.getPicture().exists());
        assertNotSame(eventPL.getPicture(), picture);
        assertTrue(eventPL.getPicture().getTotalSpace() < 65536);
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
    public void testIsDue(){        //This might be simpler based on the implementation of IsDue()
        Date savedDate = new Date(System.currentTimeMillis());

        Habit habit = new Habit("Title", "Reason", new Date(), testSchedule);
        Habit otherHabit = new Habit("Title", "Reason", new Date(), inverseSchedule);

        SystemClock.setCurrentTimeMillis(sunday.getTime());
        assertFalse(habit.isDue());
        assertTrue(otherHabit.isDue());

        SystemClock.setCurrentTimeMillis(monday.getTime());
        assertTrue(habit.isDue());
        assertFalse(otherHabit.isDue());

        SystemClock.setCurrentTimeMillis(tuesday.getTime());
        assertFalse(habit.isDue());
        assertTrue(otherHabit.isDue());

        SystemClock.setCurrentTimeMillis(wedsday.getTime());
        assertTrue(habit.isDue());
        assertFalse(otherHabit.isDue());

        SystemClock.setCurrentTimeMillis(thursday.getTime());
        assertFalse(habit.isDue());
        assertTrue(otherHabit.isDue());

        SystemClock.setCurrentTimeMillis(friday.getTime());
        assertTrue(habit.isDue());
        assertFalse(otherHabit.isDue());

        SystemClock.setCurrentTimeMillis(saterday.getTime());
        assertFalse(habit.isDue());
        assertTrue(otherHabit.isDue());

        //Tests the habits before start date are not flagged as due
        SystemClock.setCurrentTimeMillis(monday.getTime());
        habit.setStartDate(friday);
        assertFalse(habit.isDue());

        SystemClock.setCurrentTimeMillis(savedDate.getTime());      //Should change this to use Clocks
    }

    @Test
    public void testCreateUser(){
        String name = "Test";
        User user = new User(name);

        assertEquals(user.getName(), name);
        assertTrue(user.getHabits().isEmpty());
        assertTrue(user.getEvents().isEmpty());
        assertTrue(user.getFollowing().isEmpty());
        assertTrue(user.getFollowers().isEmpty());
        assertTrue(user.getRequests().isEmpty());
    }
}
