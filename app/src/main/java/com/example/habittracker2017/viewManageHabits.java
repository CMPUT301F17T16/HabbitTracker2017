/*
*viewManageHabits
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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;

import static com.example.habittracker2017.UserManager.user;

/**
 * This is All Habit's fragment, able to view a list of habits of user, create a new habit, edit habit when clicked on the habit
 *
 * @author team 16
 * @version 1.0
 * @see EditHabitActivity
 * @see ViewHabitAdapter
 * @see User
 * @since 1.0
 */

public class viewManageHabits extends Fragment {
    protected ListView Habits;
    protected static ArrayList<Habit> allHabits = new ArrayList<Habit>();
    protected static ViewHabitAdapter adapter;
    protected static boolean allowRefresh = false;

    public static viewManageHabits newInstance(int position) {
        viewManageHabits fragment = new viewManageHabits();
        Bundle args = new Bundle();
        args.putInt("position", position);
        fragment.setArguments(args);

        return fragment;
    }

    public viewManageHabits(){
        // Required empty public constructor
    }

    /**
     * Required, but not to be used with fragment
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * Inflate All habits view
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return view
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.manage_habits, container, false);

        return view;
    }

    /**
     * Get all habits of user to display.
     * When a habit is clicked, start the EditHabitActivity
     * @param savedInstanceState
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
//        loadFromFile();
        try{ allHabits = user.getHabits();}catch (Exception e){e.printStackTrace();}
        Habits = (ListView) getView().findViewById(R.id.listHabits);
        adapter = new ViewHabitAdapter(allHabits,getActivity());
        Habits.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        Habits.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                allowRefresh = true;
                Intent intent = new Intent(getContext(),EditHabitActivity.class);
                intent.putExtra("Habit",position);
                getContext().startActivity(intent);
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
