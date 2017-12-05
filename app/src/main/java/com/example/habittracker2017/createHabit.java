/*
*createHabit
*
* version 1.0
*
* Dec 3, 2017
*
*Copyright (c) 2017 Team 16 (Jonah Cowan, Alexander Mackenzie, Hao Yuan, Jacy Mark, Shu-Ting Lin), CMPUT301, University of Alberta - All Rights Reserved.
*You may use, distribute, or modify this code under terms and conditions of the Code of Student Behavior at University of Alberta.
*You can find a copy of the license in this project. Otherwise please contact contact@abc.ca.
*
*/

package com.example.habittracker2017;

import android.app.DatePickerDialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import static com.example.habittracker2017.UserManager.user;

/**
 * Activity for create habit
 *
 * @author team 16
 * @version 1.0
 * @see viewManageHabits
 * @since 1.0
 */
public class createHabit extends AppCompatActivity implements View.OnClickListener {

    private EditText habitName;
    private TextView habitStart;
    private EditText reason;
    private Button datePicker;
    private Button button;
    private int day;
    private int month;
    private int year;

    private CheckBox monday;
    private CheckBox tuesday;
    private CheckBox wednesday;
    private CheckBox thursday;
    private CheckBox friday;
    private CheckBox saturday;
    private CheckBox sunday;


    HashMap<Integer, Boolean> habitHash = new HashMap<Integer, Boolean>();

    /**
     * Called when activity is created
     * Set instance variables
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_habit);

        datePicker = (Button) findViewById(R.id.timePicker);
        datePicker.setOnClickListener(this);

        habitName = (EditText) findViewById(R.id.habitName);
        reason = (EditText) findViewById(R.id.reason);

        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(this);

        monday = (CheckBox) findViewById(R.id.monday);
        tuesday = (CheckBox) findViewById(R.id.tuesday);
        wednesday = (CheckBox) findViewById(R.id.wednesday);
        thursday = (CheckBox) findViewById(R.id.thursday);
        friday = (CheckBox) findViewById(R.id.friday);
        saturday = (CheckBox) findViewById(R.id.saturday);
        sunday = (CheckBox) findViewById(R.id.sunday);

    }

    /**
     * Set date checked, put into habitHash
     */
    public void setHabitHash() {
        this.habitHash.put(1, sunday.isChecked());
        this.habitHash.put(2, monday.isChecked());
        this.habitHash.put(3, tuesday.isChecked());
        this.habitHash.put(4, wednesday.isChecked());
        this.habitHash.put(5, thursday.isChecked());
        this.habitHash.put(6, friday.isChecked());
        this.habitHash.put(7, saturday.isChecked());
        this.habitHash.put(1, sunday.isChecked());
    }

    /**
     * Do corresponding tasks when certain button pressed
     * Set date
     * Create habit from parameters. If required field is not given, then habit not created
     * @param v
     */
    @Override
    public void onClick(View v) {
        if (v == datePicker){
            final Calendar c = Calendar.getInstance();
            year = c.get(Calendar.YEAR);
            month = c.get(Calendar.MONTH);
            day = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    datePicker.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                }
            }, year, month, day);
            datePickerDialog.show();
        }

        if (v == button) {
            setResult(RESULT_OK);
            String habitTitleName = habitName.getText().toString();
            DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
            String habitStartString = datePicker.getText().toString();
            setHabitHash();
            Date habitStartDate = null;
            try {
                habitStartDate = format.parse(habitStartString);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            String habitReason = reason.getText().toString();

            /**
             * Try to create habit from parameters
             */
            try {
                if (habitTitleName.equals("")){
                    Toast.makeText(getBaseContext(), "Please enter a proper title! ", Toast.LENGTH_SHORT).show();
                }else if (habitStartDate == null){
                    Toast.makeText(getBaseContext(), "Please select a start date! ", Toast.LENGTH_SHORT).show();
                }else {
                    Habit habit = new Habit(habitTitleName, habitReason, habitStartDate, habitHash);
                    user.addHabit(habit);
                    UserManager.save();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            finish();
        }

    }
}

