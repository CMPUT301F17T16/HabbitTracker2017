package com.example.habittracker2017.entityTests;

import android.test.ActivityInstrumentationTestCase2;

import com.example.habittracker2017.User;

import org.junit.Test;

/**
 * Created by hyuan2 on 2017-12-02.
 */

public class UserTests extends ActivityInstrumentationTestCase2{
    public UserTests(){
        super(com.example.habittracker2017.User.class);
    }

    @Test
    public void UserCreationTest(){

        User Testuser = new User("Test222");
        assertEquals(Testuser, "Test222");
    }

    @Test
    public void UserGetHabitTest(){

    }

}
