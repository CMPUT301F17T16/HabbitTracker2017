package com.example.habittracker2017;

import java.util.ArrayList;

/**
 * Created by Alex on 2017-10-20.
 * Represents a user, and includes the usernames of all users associated with this user.
 */

public class User {
    private String name;
    private ArrayList<Habit> habits;
    private ArrayList<String> following;
    private ArrayList<String> followers;
    private ArrayList<String> requests;

    /**
     * Creates a new user with a given username.
     * @param name The username of the new user.
     */
    User(String name){
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
        following.add(name);
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
}
