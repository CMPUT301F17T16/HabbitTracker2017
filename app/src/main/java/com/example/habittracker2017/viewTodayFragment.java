/*
*viewTodayFragment
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
 *This is Today's habit fragment , can view a list of habits that are due today, and able to create habit event when clicked on habit
 *
 * @author team 16
 * @version 1.0
 * @see CreateEventActivity
 * @see User
 * @see ViewHabitAdapter
 * @since 1.0
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

    /**
     * Not to be used with fragment
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
           /* mPosition = getArguments().getInt("position");*/
        }
    }

    /**
     * Inflate today's habit view
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return view
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.todays_habits, container, false);

        return view;
    }

    /**
     *When activity created, get all user habits and sort out today's habit for display
     * If today's habit have not yet create an event, then click on the habit to open create event activity
     * If the an event has already been created for the habit, then user is not allowed to create another event for that habit
     * @param savedInstanceState
     */
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

    /**
     * Refresh the fragment view by detaching the old one and attaching updated one
     */
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
