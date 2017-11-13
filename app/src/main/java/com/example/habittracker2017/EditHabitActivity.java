package com.example.habittracker2017;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
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

public class EditHabitActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText habitName;
    private TextView habitStart;
    private EditText reason;
    private Button datePicker;
    private Button SaveButton;
    private Button CreateEventButton;
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
    private int position;
    private Habit habit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_habit);
        position = this.getIntent().getIntExtra("Habit",0);
        habit = viewManageHabits.allHabits.get(position);
        datePicker = (Button) findViewById(R.id.timePicker);
        datePicker.setOnClickListener(this);
        datePicker.setText(habit.getStartDate().toString());
        HashMap<Integer, Boolean> schedule = habit.getSchedule();
        habitName = (EditText) findViewById(R.id.habitName);
        habitName.setText(habit.getTitle());
        reason = (EditText) findViewById(R.id.reason);
        reason.setText(habit.getReason());


        SaveButton = (Button) findViewById(R.id.button_save);
        SaveButton.setOnClickListener(this);
        CreateEventButton = (Button) findViewById(R.id.button_create_event);

        monday = (CheckBox) findViewById(R.id.monday);
        tuesday = (CheckBox) findViewById(R.id.tuesday);
        wednesday = (CheckBox) findViewById(R.id.wednesday);
        thursday = (CheckBox) findViewById(R.id.thursday);
        friday = (CheckBox) findViewById(R.id.friday);
        saturday = (CheckBox) findViewById(R.id.saturday);
        sunday = (CheckBox) findViewById(R.id.sunday);
        sunday.setChecked(schedule.get(1));
        monday.setChecked(schedule.get(2));
        tuesday.setChecked(schedule.get(3));
        wednesday.setChecked(schedule.get(4));
        thursday.setChecked(schedule.get(5));
        friday.setChecked(schedule.get(6));
        saturday.setChecked(schedule.get(7));
    }

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

        if (v==CreateEventButton) {
//            Intent intent = new Intent(this,CreateEventActivity.class);
            Toast.makeText(getBaseContext(), datePicker.getText().toString(), Toast.LENGTH_SHORT).show();
        }

        if (v == SaveButton) {
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
                //createHabitManager.create(habitTitleName, habitStartDate, habitReason, habitHash,user.getName());
                if (habitTitleName.equals("")){
                    Toast.makeText(getBaseContext(), "Please enter a proper title! ", Toast.LENGTH_SHORT).show();
                }else if (habitStartDate == null){
                    Toast.makeText(getBaseContext(), "Please select a start date! ", Toast.LENGTH_SHORT).show();
                }else {
                    habit.setTitle(habitTitleName);
                    habit.setStartDate(habitStartDate);
                    habit.setReason(habitReason);
                    habit.setSchedule(habitHash);
                    viewManageHabits.adapter.notifyDataSetChanged();
                    saveToFile();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            finish();
        }

    }
    public void saveToFile(){
        try{
            FileOutputStream fos = openFileOutput(viewManageHabits.FILENAME, Context.MODE_PRIVATE);
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

