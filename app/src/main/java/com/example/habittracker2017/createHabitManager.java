package com.example.habittracker2017;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Date;
import java.util.HashMap;

import static com.example.habittracker2017.UserManager.user;

/**
 * Created by jmark on 2017-11-11.
 */

public class createHabitManager {
    public static Habit habit;
    private static Context context;
    private static final String FILENAME ="habits.sav";


    public static void create(String habitName, Date habitStart, String habitReason, HashMap<Integer, Boolean> habitSchedule, String owner) throws Exception {
        if (habitName == null) {throw new Exception();}
        habit = new Habit(habitName,habitReason,habitStart,habitSchedule,owner);
        user.addHabit(habit);
        viewManageHabits.allHabits.add(habit);
        viewManageHabits.adapter.notifyDataSetChanged();
        saveToFile();

    }
    public static void saveToFile(){
        try{
            FileOutputStream fos =  context.openFileOutput(FILENAME,Context.MODE_PRIVATE);
            OutputStreamWriter writer = new OutputStreamWriter(fos);
            Gson gson = new Gson();
            gson.toJson(viewManageHabits.allHabits, writer);
            writer.flush();
            fos.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
