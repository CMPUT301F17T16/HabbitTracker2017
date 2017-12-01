package com.example.habittracker2017;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by jmark on 2017-12-01.
 * Passed start date of habit and calculates all scheduled days in between start date and end date
 * compares to habit events, if habit event exists for that date put it in completed array
 * if no habit event found for it put it in missed
 * pie chart vals are completed/(completed + missed) and missed/(completed+missed)
 *
 * todo: controller class
 * todo: calendar view
 * todo: changeable dates
 */

public class StatView extends AppCompatActivity {

    Date currentDay = Calendar.getInstance().getTime();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stat_view);

        PieChart chart = (PieChart) findViewById(R.id.pieChart);

        List<PieEntry> entries = new ArrayList<>();
        ArrayList<Integer> colors = new ArrayList<Integer>();

        entries.add(new PieEntry(50f, "Completed"));
        entries.add(new PieEntry(50f, "Missed"));

        chart.setHoleRadius(0f);
        chart.getDescription().setEnabled(false);

        colors.add(Color.rgb(0,153,51));//green for complete
        colors.add(Color.rgb(244,66,66));//red for missed


        PieDataSet set = new PieDataSet(entries,"Habits");
        set.setColors(colors);
        PieData data = new PieData(set);
        data.setValueTextColor(Color.BLACK);
        chart.setData(data);
        chart.invalidate();
    }
}
