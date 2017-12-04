/*
*User
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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

import io.searchbox.annotations.JestId;

/**
 * Represents a user, and includes the usernames of all users associated with this user.
 *
 * @author team 16
 * @version 1.0
 * @since 1.0
 */
public class User {
    @JestId
    private String name;
    private ArrayList<Habit> habits;
    private ArrayList<String> following;
    private ArrayList<String> followers;
    private ArrayList<String> requests;

    /**
     * Creates a new user with a given username.
     * @param name The username of the new user.
     */
    public User(String name){
        this.name = name;
        habits = new ArrayList<Habit>();
        following = new ArrayList<String>();
        followers = new ArrayList<String>();
        requests = new ArrayList<String>();
    }

    /**
     * Adds a new habit to this user.
     * @param habit The habit to add.
     */
    public void addHabit(Habit habit){
        habits.add(habit);
    }

    /**
     * Deletes a habit.
     * @param habit The habit to delete.
     */
    public void deleteHabit(Habit habit){
        habits.remove(habit);
    }

    /**
     * Adds a username to following.
     * @param name The username to add.
     */
    public void addFollowing(String name){
        if(!following.contains(name)){
            following.add(name);
        }
    }

    /**
     * Adds a username to follower.
     * @param name The username to add.
     */
    public void addFollower(String name){
        followers.add(name);
    }

    /**
     * Adds a username to request.
     * @param name The username to add.
     */
    public void addRequest(String name){
        requests.add(name);
    }

    public String getName() {
        return name;
    }

    public ArrayList<Habit> getHabits() {
        return habits;
    }

    public ArrayList<String> getFollowing() {
        return following;
    }

    public ArrayList<String> getFollowers() {
        return followers;
    }

    public ArrayList<String> getRequests() {
        return requests;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setHabits(ArrayList<Habit> habits) {
        this.habits = habits;
    }

    public void setFollowing(ArrayList<String> following) {
        this.following = following;
    }

    public void setFollowers(ArrayList<String> followers) {
        this.followers = followers;
    }

    public void setRequests(ArrayList<String> requests) {
        this.requests = requests;
    }

    public ArrayList<HabitEvent> getAllEvents(){
        ArrayList<HabitEvent> allEvents = new ArrayList<HabitEvent>();
        for (Habit habit : this.habits){
            allEvents.addAll(habit.getEvents());
        }
        Collections.sort(allEvents, new Comparator<HabitEvent>() {
            @Override
            public int compare(HabitEvent o1, HabitEvent o2) {
                return o2.getDate().compareTo(o1.getDate());
            }
        });
        return allEvents;
    }
}
