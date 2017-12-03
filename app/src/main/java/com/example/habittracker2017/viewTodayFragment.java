package com.example.habittracker2017;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;

import static com.example.habittracker2017.UserManager.user;

/**
 * Created by hyuan2 on 2017-11-12.
 */

public class viewTodayFragment extends Fragment {
    protected ListView todaysHabit;
    protected static ArrayList<Habit> allHabits = new ArrayList<Habit>();
    protected static ViewHabitAdapter adapter;
    private boolean allowRefresh = false;

    public static viewTodayFragment newInstance(int position) {
        viewTodayFragment fragment = new viewTodayFragment();
        Bundle args = new Bundle();
        args.putInt("position", position);
        fragment.setArguments(args);

        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
           /* mPosition = getArguments().getInt("position");*/
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.todays_habits, container, false);

        return view;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        try{ allHabits = user.getHabits();}catch (Exception e){e.printStackTrace();}
        todaysHabit = (ListView) getView().findViewById(R.id.todaysHabit);
        adapter = new ViewHabitAdapter(allHabits,getActivity());
        adapter.sortTodayHabit();
        todaysHabit.setAdapter(adapter);
        todaysHabit.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                allowRefresh = true;
                HabitEvent todayEvent = allHabits.get(position).getLastEvent();
                if (todayEvent != null) {
                    if (DateUtils.isToday(todayEvent.getDate().getTime())) {
                        Toast.makeText(getContext(), "You have already done this habit today!", Toast.LENGTH_LONG).show();
                    } else {
                        Intent intent = new Intent(getContext(), CreateEventActivity.class);
                        intent.putExtra("Habit", position);
                        getContext().startActivity(intent);
                    }
                }
                //If no events exist create the event
                else {
                    Intent intent = new Intent(getContext(), CreateEventActivity.class);
                    intent.putExtra("Habit", position);
                    getContext().startActivity(intent);
                }
            }
        });
    }
    @Override
    public void onResume() {
        super.onResume();
        if (allowRefresh)
        {
            allowRefresh = false;
            getFragmentManager().beginTransaction().detach(this).attach(this).commit();
        }
    }
}
