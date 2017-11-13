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

/**
 * Created by jmark on 2017-11-11.
 */

public class createHabitManager {
    public static Habit habit;
    private static Context context;
    private static final String FILENAME ="file.sav";


    public static void create(String habitName, Date habitStart, String habitReason, HashMap<Integer, Boolean> habitSchedule) throws Exception {
        if (habitName == null) {throw new Exception();}
        habit = new Habit(habitName,habitReason,habitStart,habitSchedule);
        saveToFile();

    }
    public static void saveToFile(){
        try{
            FileOutputStream fos =  context.openFileOutput(FILENAME,Context.MODE_PRIVATE);
            OutputStreamWriter writer = new OutputStreamWriter(fos);

            Gson gson = new Gson();
            gson.toJson(habit, writer);
            writer.flush();

            fos.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
