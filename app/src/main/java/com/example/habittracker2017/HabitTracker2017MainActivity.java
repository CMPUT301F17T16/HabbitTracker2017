/*
*HabitTracker2017MainActivity
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

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.os.Bundle;
import android.view.View;

import android.widget.TabHost;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;

import static com.example.habittracker2017.UserManager.user;

/**
 *Initialize app's structure
 * @author team 16
 * @version 1.0
 * @see UserManager
 * @see CreateUserActivity
 * @since 1.0
 */
public class HabitTracker2017MainActivity extends AppCompatActivity{


    private SectionsPagerAdapter sectionspagerAdapter;
    private ViewPager viewPager;

    /**
     * Called when activity is created
     * Prompts for create user if user does not exist
     * Setup view
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit_tracker2017);


        //Starts UserManager singleton, if it does not exist.
        UserManager.init(this.getApplicationContext());

        //Goes to user creation activity is there is no user found
        if(UserManager.user == null){
            Intent intent = new Intent(this, CreateUserActivity.class);
            startActivity(intent);
        }

        sectionspagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        viewPager = (ViewPager) findViewById(R.id.container);
        viewPager.setAdapter(sectionspagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    /**
     * SectionsPagerAdapter sets app's Fragment view
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        /**
         * SectionsPagerAdapter constructor
         * @param fm
         */
        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        /**
         * Returns Fragment view given position
         * @param position
         * @return Fragment
         */
        @Override
        public Fragment getItem(int position) {

            switch (position) {
                case 0:
                    return new MyHabitFragment();
                default:
                    return new OthersHabitFragment();
            }
        }

        /**
         *Returns tab count
         * @return 2 as there are two main tabs
         */
        public int getCount(){return 2;}

        /**
         *Returns tab name at position
         * @param position
         * @return CharSquence which is the tab name
         */
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "My Habit";
                case 1:
                    return "Other's Habit";
            }
            return null;
        }
    }

    /**
     * Start createHabit activity
     * @param view
     */
    public void createHabit(View view){
        viewManageHabits.allowRefresh = true;
        Intent intent = new Intent(this, createHabit.class);      /* Button that used to create a new habit */
        startActivity(intent);
    }
}
