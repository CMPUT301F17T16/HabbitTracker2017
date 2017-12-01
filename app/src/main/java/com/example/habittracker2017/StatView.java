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
import java.util.List;

public class StatView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stat_view);

        PieChart chart = (PieChart) findViewById(R.id.pieChart);

        List<PieEntry> entries = new ArrayList<>();
        ArrayList<Integer> colors = new ArrayList<Integer>();

        entries.add(new PieEntry(50f, "Completed"));
        entries.add(new PieEntry(50f, "Missed"));

        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());

        PieDataSet set = new PieDataSet(entries,"Habits");
        set.setColors(colors);
        PieData data = new PieData(set);
        data.setValueTextColor(Color.BLACK);
        chart.setData(data);
        chart.invalidate();
    }
}
