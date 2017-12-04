package com.example.habittracker2017;

import org.junit.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import static junit.framework.Assert.assertEquals;

/**
 * Created by jmark on 2017-12-04.
 */

public class UserTest {

    HashMap<Integer, Boolean> testSchedule = new HashMap<>();

    @Test
    public void testCreateUser(){
        String name = "TestUser";
        User user = new User(name);

        assertEquals("User not created",name, user.getName());
    }

    @Test
    public void testAddHabit(){
        String name = "TestUser";
        Habit habit0 = new Habit("Title", "Reason", new Date(), testSchedule);
        User user = new User(name);

        user.addHabit(habit0);
        ArrayList<Habit> habits = user.getHabits();
        assertEquals("Habit not added properly or returned improperly",habit0, habits.get(0));

    }

    @Test
    public void testDeleteHabit(){
        String name = "TestUser";
        Habit habit0 = new Habit("Title", "Reason", new Date(), testSchedule);
        Habit habit1 = new Habit("Title1", "Reason1", new Date(), testSchedule);
        User user = new User(name);

        user.addHabit(habit0);
        user.addHabit(habit1);
        user.deleteHabit(habit0);
        ArrayList<Habit> habits = user.getHabits();

        assertEquals("Habit not properly deleted", habit1, habits.get(0));
    }

    @Test
    public void testAddFollowing(){
        String name = "TestUser";
        String testFollow = "TestFollowing";
        Habit habit0 = new Habit("Title", "Reason", new Date(), testSchedule);
        User user = new User(name);
        user.addHabit(habit0);

        user.addFollowing(testFollow);
        ArrayList<String> following = user.getFollowing();
        assertEquals("Following not being added properly", testFollow, following.get(0));
    }

    @Test
    public void testAddFollower(){
        String name = "TestUser";
        String testFollower = "TestFollower";
        Habit habit0 = new Habit("Title", "Reason", new Date(), testSchedule);
        User user = new User(name);
        user.addHabit(habit0);

        user.addFollower(testFollower);
        ArrayList<String> follower = user.getFollowers();
        assertEquals("Follower not being added properly", testFollower, follower.get(0));
    }

    @Test
    public void testAddRequest(){
        String name = "TestUser";
        String testRequest = "TestRequest";
        Habit habit0 = new Habit("Title", "Reason", new Date(), testSchedule);
        User user = new User(name);
        user.addHabit(habit0);

        user.addRequest(testRequest);
        ArrayList<String> requests = user.getRequests();
        assertEquals("Requests not being added properly", testRequest, requests.get(0));
    }

    @Test
    public void testSetName(){
        String name = "TestUser";
        User user = new User(name);

        String newName = "TestUserNew";
        user.setName(newName);

        assertEquals("Name not being changed properly", newName, user.getName());
    }

    @Test
    public void testSetHabits(){
        String name = "TestUser";
        ArrayList<Habit> testSet = new ArrayList<>();
        Habit habit0 = new Habit("Title", "Reason", new Date(), testSchedule);
        Habit habit1 = new Habit("Title", "Reason", new Date(), testSchedule);
        Habit habit2 = new Habit("Title", "Reason", new Date(), testSchedule);

        testSet.add(habit0);
        testSet.add(habit1);
        testSet.add(habit2);

        User user = new User(name);
        user.setHabits(testSet);
        assertEquals("Habits not added properly or returned improperly",testSet, user.getHabits());
    }

    @Test
    public void testSetFollowing(){
        String name = "TestUser";
        ArrayList<String> testSet = new ArrayList<>();

        testSet.add("first");
        testSet.add("second");
        testSet.add("third");

        User user = new User(name);
        user.setFollowing(testSet);
        assertEquals("Habits not added properly or returned improperly",testSet, user.getFollowing());
    }

    @Test
    public void testSetFollower(){
        String name = "TestUser";
        ArrayList<String> testSet = new ArrayList<>();

        testSet.add("first");
        testSet.add("second");
        testSet.add("third");

        User user = new User(name);
        user.setFollowers(testSet);
        assertEquals("Habits not added properly or returned improperly",testSet, user.getFollowers());
    }

    @Test
    public void testSetRequests(){
        String name = "TestUser";
        ArrayList<String> testSet = new ArrayList<>();

        testSet.add("first");
        testSet.add("second");
        testSet.add("third");

        User user = new User(name);
        user.setRequests(testSet);
        assertEquals("Habits not added properly or returned improperly",testSet, user.getRequests());
    }
}
