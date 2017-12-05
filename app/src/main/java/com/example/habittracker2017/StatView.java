/*
*StatView
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
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.joda.time.DateTime;
import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Passed start date of habit and calculates all scheduled days in between start date and end date
 * compares to habit events, if habit event exists for that date put it in completed array
 * if no habit event found for it put it in missed
 * pie chart vals are completed/(completed + missed) and missed/(completed+missed)
 *
 * todo: controller class
 * todo: calendar view
 * todo: changeable dates
 *
 * @author team 16
 * @version 1.0
 * @see AppCompatActivity
 * @since 1.0
 */

public class StatView extends AppCompatActivity implements View.OnClickListener {

    private int position;
    private Habit habit;
    private Date startDate;
    private Date currentDay;
    private ArrayList<Float> completed;
    private Button startDatePickerButton;
    private Button endDatePickerButton;
    private TextView completeText;
    private TextView missText;
    private int year;
    private int month;
    private int day;
    private PieChart chart;
    private LineChart lineChart;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stat_view);

        position = this.getIntent().getIntExtra("Habit", 0);
        habit = viewTodayFragment.allHabits.get(position);

        completeText = (TextView) findViewById(R.id.complete);
        missText = (TextView) findViewById(R.id.miss);
        TextView statTitle = (TextView) findViewById(R.id.statsHeader);
        statTitle.setText("Statistics for " + habit.getTitle());

        currentDay = Calendar.getInstance().getTime();
        startDate = habit.getStartDate();
        startDatePickerButton = (Button) findViewById(R.id.startDatePicker);
        endDatePickerButton = (Button) findViewById(R.id.endDatePicker);
        startDatePickerButton.setOnClickListener(this);
        endDatePickerButton.setOnClickListener(this);
        DateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String newStartDateString = simpleDateFormat.format(startDate);
        startDatePickerButton.setText(newStartDateString);
        String newEndDateString = simpleDateFormat.format(currentDay);
        endDatePickerButton.setText(newEndDateString);

        completed = StatManager.completedStats(startDate,currentDay,habit);
        /*
        draw pie chart and textViews
         */
        pieChartDraw(completed);

    }

    @Override
    public void onClick(View v) {
        if (v == startDatePickerButton) {
            final Calendar c = Calendar.getInstance();
            year = c.get(Calendar.YEAR);
            month = c.get(Calendar.MONTH);
            day = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    startDatePickerButton.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                    DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
                    String startString = startDatePickerButton.getText().toString();
                    try {
                        startDate = format.parse(startString);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    completed = StatManager.completedStats(startDate,currentDay,habit);
                    pieChartDraw(completed);
                }
            }, year, month, day);
            datePickerDialog.show();
        }

        if (v == endDatePickerButton) {
            final Calendar c = Calendar.getInstance();
            year = c.get(Calendar.YEAR);
            month = c.get(Calendar.MONTH);
            day = c.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    endDatePickerButton.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                    DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
                    String endString = endDatePickerButton.getText().toString();
                    try {
                        currentDay = format.parse(endString);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    currentDay = new Date(currentDay.getTime() + (1000 * 60 * 60 * 24));
                    completed = StatManager.completedStats(startDate,currentDay,habit);
                    pieChartDraw(completed);
                }
            }, year, month, day);
            datePickerDialog.show();
        }
    }

    /**
     * Creates the pieChart and the writes the amount of completed and missed habits
     *
     * @param chartData
     */
    private void pieChartDraw(ArrayList<Float> chartData){

        completeText = (TextView) findViewById(R.id.complete);
        missText = (TextView) findViewById(R.id.miss);
        completeText.setText("Missed: " + chartData.get(2));
        missText.setText("Completed: " + chartData.get(3));

        /*
        Pie chart filling and colouring stuff
         */
        float complete = chartData.get(0);
        float miss = chartData.get(1);
        chart = (PieChart) findViewById(R.id.pieChart);
        List<PieEntry> entries = new ArrayList<>();
        ArrayList<Integer> colors = new ArrayList<Integer>();

        entries.add(new PieEntry(complete, "Completed"));
        entries.add(new PieEntry(miss, "Missed"));

        chart.setHoleRadius(0f);
        chart.getDescription().setEnabled(false);

        colors.add(Color.rgb(0,153,51));//green for complete
        colors.add(Color.rgb(244,66,66));//red for missed


        PieDataSet set = new PieDataSet(entries,"Habits");
        set.setColors(colors);
        PieData data = new PieData(set);
        data.setValueTextSize(20f);
        data.setValueTextColor(Color.BLACK);
        chart.setData(data);
        chart.animateXY(1000, 1000);
        chart.invalidate();
    }
}
