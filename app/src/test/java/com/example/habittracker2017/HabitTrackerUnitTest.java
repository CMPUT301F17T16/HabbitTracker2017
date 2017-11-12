package com.example.habittracker2017;

import android.graphics.Bitmap;
import android.location.Location;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
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

    @Test
    public void testAdvancedEventCreation() {
        Habit habit = new Habit("Title", "Reason", new Date(), testSchedule);
        String comment = "Test comment";

        Location location = null;
        Bitmap picture = null;

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

    @Test
    public void testAddHabits(){
        User user = new User("Name");
        Habit habit0 = new Habit("Title", "Reason", new Date(), testSchedule);
        Habit habit1 = new Habit("Title", "Reason", new Date(), testSchedule);
        Habit habit2 = new Habit("Title", "Reason", new Date(), testSchedule);
        HabitEvent event01 = new HabitEvent("Test");
        HabitEvent event11 = new HabitEvent("Test");
        HabitEvent event12 = new HabitEvent("Test");
        HabitEvent event13 = new HabitEvent("Test");

        user.addHabit(habit0);
        user.addHabit(habit1);
        user.addHabit(habit2);

        habit0.addEvent(event01);
        habit1.addEvent(event11);
        habit1.addEvent(event12);
        habit1.addEvent(event13);

        //Test that events can get information from habits
        assertEquals(event01.getHabit(), habit0);
        assertEquals(event11.getHabit(), habit1);
        assertEquals(event12.getHabit(), habit1);
        assertEquals(event13.getHabit(), habit1);

        //Test that all events can be retrieved from the User object
        ArrayList<HabitEvent> fullList = new ArrayList<>();
        for(Habit habit : user.getHabits()){
            for(HabitEvent event : habit.getEvents()){
                fullList.add(event);
            }
        }

        assertEquals(fullList.size(), 4);
        assertTrue(fullList.contains(event01));
        assertTrue(fullList.contains(event11));
        assertTrue(fullList.contains(event12));
        assertTrue(fullList.contains(event13));

        //Tests the most recent events can be retrieved from the User object
        ArrayList<HabitEvent> reList = new ArrayList<>();
        for(Habit habit : user.getHabits()){
            if(habit.getLastEvent() != null){
                reList.add(habit.getLastEvent());
            }
        }

        assertEquals(reList.size(), 2);
        assertTrue(reList.contains(event01));
        assertTrue(reList.contains(event11) || reList.contains(event12) //Since these events are created around the same time,
                || reList.contains(event13));                                   // this implementation could return any one of them.
    }

    @Test
    public void testDeleteHabit(){
        User user = new User("Name");
        Habit habit0 = new Habit("Title", "Reason", new Date(), testSchedule);
        Habit habit1 = new Habit("Title", "Reason", new Date(), testSchedule);
        Habit habit2 = new Habit("Title", "Reason", new Date(), testSchedule);

        user.addHabit(habit0);
        user.addHabit(habit1);
        user.addHabit(habit2);

        user.deleteHabit(habit1);

        assertEquals(user.getHabits().size(), 2);
        assertTrue(user.getHabits().contains(habit0));
        assertTrue(user.getHabits().contains(habit2));

        user.deleteHabit(habit2);

        assertEquals(user.getHabits().size(), 1);
        assertTrue(user.getHabits().contains(habit0));

        user.deleteHabit(habit0);

        assertEquals(user.getHabits().size(), 0);
    }
}
