package com.example.habittracker2017;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class createHabit extends AppCompatActivity implements View.OnClickListener {

    private EditText habitName;
    private TextView habitStart;
    private EditText reason;
    private Button datePicker;
    private Button button;
    private int day;
    private int month;
    private int year;


    HashMap<Integer, Boolean> habitHash = new HashMap<Integer, Boolean>();
    /*Placeholder Schedule*/
    public void setHabitHash(HashMap<Integer, Boolean> habitSchedule) {
        this.habitHash = habitSchedule;
        habitHash.put(1, true);
        habitHash.put(2, true);
        habitHash.put(3, true);
        habitHash.put(4, true);
        habitHash.put(5, true);
        habitHash.put(6, true);
        habitHash.put(7, true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_habit);

        datePicker = (Button) findViewById(R.id.timePicker);
        datePicker.setOnClickListener(this);

        habitName = (EditText) findViewById(R.id.habitName);
        habitStart = (TextView) findViewById(R.id.habitStart);
        reason = (EditText) findViewById(R.id.reason);

        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v == datePicker){
            final Calendar c = Calendar.getInstance();
            year = c.get(Calendar.YEAR);
            month = c.get(Calendar.MONTH);
            day = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            habitStart.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                        }
                    }, year, month, day);
            datePickerDialog.show();
        }

        if (v == button) {
            setResult(RESULT_OK);
            String habitTitleName = habitName.getText().toString();
            DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
            String habitStartString = habitStart.getText().toString();
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
                createHabitManager.create(habitTitleName, habitStartDate, habitReason, habitHash);
            } catch (Exception e) {
                e.printStackTrace();
            }
            finish();
        }

    }
}

