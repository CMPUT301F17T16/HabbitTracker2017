package com.example.habittracker2017;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

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
 * Created by jmark on 2017-11-11.
 */

public class viewManageHabits extends AppCompatActivity {
    protected ListView Habits;
    protected static ArrayList<Habit> allHabits = new ArrayList<Habit>();
    protected static ViewHabitAdapter adapter;
    private static final String FILENAME ="habits.sav";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Habit habit = new Habit("testTitle","testReason",new Date(),null,user.getName());
        allHabits.add(habit);
        setContentView(R.layout.manage_habits);
        Habits = (ListView) findViewById(R.id.listHabits);

    }

    @Override
    public void onStart(){
        super.onStart();
//        loadFromFile();
//        allHabits = user.getHabits();
        adapter = new ViewHabitAdapter(allHabits,this);
        Log.d("Array length: ", allHabits.toString());
        Habits.setAdapter(adapter);
    }

    /**
     * loadFromFile
     *
     * reference https://github.com/joshua2ua/lonelyTwitter
     */
    private void loadFromFile() {
        try {
            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));
            Gson gson = new Gson();
            Type listType = new TypeToken<ArrayList<Habit>>(){}.getType();
            allHabits = gson.fromJson(in, listType);

        } catch (FileNotFoundException e) {
            allHabits = new ArrayList<Habit>();
        }
    }

}
