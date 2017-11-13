package com.example.habittracker2017;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import static junit.framework.Assert.assertTrue;

/**
 * Created by jlin7 on 2017/11/12.
 */

public class viewMyHistory extends AppCompatActivity{
    private HashMap<Integer, Boolean> testSchedule;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_history);

        Log.d("in history","correct");

        testSchedule.put(1, true);
        Habit habit = new Habit("Title", "Reason", new Date(), testSchedule);
        String comment = "Test comment";
        HabitEvent event = new HabitEvent(comment);
        habit.addEvent(event);



        HistoryAdapter adapter = new HistoryAdapter(this, habit.getEvents());
        ListView listView=findViewById(R.id.myHistory_list);
        listView.setAdapter(adapter);

        adapter.add(event);
        adapter.notifyDataSetChanged();


    }

    /*fix: when click on map, app stops*/

    public void openMap(View view) {
        /*Uri gmmIntentUri = Uri.parse("geo:37.7749,-122.4194");
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        if (mapIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(mapIntent);
        }*/
    }
}
