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

public class HabitTracker2017MainActivity extends AppCompatActivity{

    private Context context;
    private SectionsPagerAdapter sectionspagerAdapter;
    private ViewPager viewPager;
    protected static final String FILENAME ="habits.sav";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit_tracker2017);
        context = this;


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

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            switch (position) {
                case 0:
                    return new MyHabitFragment();
                default:
                    return new OthersHabitFragment();
            }
        }
        public int getCount(){return 2;}
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
    public void createHabit(View view){
        viewManageHabits.allowRefresh = true;
        Intent intent = new Intent(this, createHabit.class);      /* Button that used to create a new habit */
        startActivity(intent);
    }
}
