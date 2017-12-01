package com.example.habittracker2017;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.List;

public class StatView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stat_view);

        PieChart chart = (PieChart) findViewById(R.id.pieChart);

        List<PieEntry> entries = new ArrayList<>();

        entries.add(new PieEntry(50f, "Completed"));
        entries.add(new PieEntry(50f, "Missed"));

        PieDataSet set = new PieDataSet(entries, "Election Results");
        PieData data = new PieData(set);
        chart.setData(data);
        chart.invalidate();
    }
}
