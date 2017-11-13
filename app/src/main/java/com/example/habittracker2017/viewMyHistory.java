package com.example.habittracker2017;


import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import static com.example.habittracker2017.UserManager.user;

/**
 * Created by jlin7 on 2017/11/12.
 */

public class viewMyHistory extends Fragment {

    //private HashMap<Integer, Boolean> testSchedule;

    public static viewMyHistory newInstance(int position) {
        viewMyHistory fragment = new viewMyHistory();
        Bundle args = new Bundle();
        args.putInt("position", position);
        fragment.setArguments(args);

        return fragment;
    }

    public viewMyHistory(){}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.my_history);
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_history, container, false);

        /*testSchedule.put(0, false);       this stops the app, so I set schedule to null*/

        /*test create a habit and habit event to display in my history tab*/
        Habit habit = new Habit("Title", "Reason", new Date(), null,"user1");
        String comment = "Test comment";
        HabitEvent event = new HabitEvent(comment);
        habit.addEvent(event);

        ListView listView=view.findViewById(R.id.myHistory_list);
        HistoryAdapter adapter = new HistoryAdapter(getActivity(), habit.getEvents());
        listView.setAdapter(adapter);

        return view;
    }


    /*fix: when click on map, app stops*/

//    public void openMap(View view) {
//        Uri gmmIntentUri = Uri.parse("geo:37.7749,-122.4194");
//        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
//        mapIntent.setPackage("com.google.android.apps.maps");
//        if (mapIntent.resolveActivity(getPackageManager()) != null) {
//            startActivity(mapIntent);
//        }
//    }
}
