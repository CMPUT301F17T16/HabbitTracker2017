/*
*viewMyHistory
*
* version 1.0
*
* Dec 3, 2017
*
*Copyright (c) 2017 Team 16, CMPUT301, University of Alberta - All Rights Reserved.
*You may use, distribute, or modify this code under terms and conditions of the Code of Student Behavior at University of Alberta.
*You can find a copy of the license in this project. Otherwise please contact contact@abc.ca.
*
*/

package com.example.habittracker2017;


import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.support.v7.widget.Toolbar;
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
import java.util.Locale;

import static com.example.habittracker2017.UserManager.user;

/**
 * Activities the viewHistory does, subclass of fragment
 *
 * @author team 16
 * @version 1.0
 * @see Fragment
 * @since 1.0
 */

public class viewMyHistory extends Fragment {

    protected static ArrayList<HabitEvent> allEvents = new ArrayList<HabitEvent>();
    protected ListView habitEvents;
    private HistoryAdapter adapter;
    private EditText searchEvent;

    public static viewMyHistory newInstance(int position) {
        viewMyHistory fragment = new viewMyHistory();
        Bundle args = new Bundle();
        args.putInt("position", position);
        fragment.setArguments(args);

        return fragment;
    }

    /**
     * Required empty public constructor
     */
    public viewMyHistory(){}

    /**
     * Not to be used with fragment
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * Called when the view is first created. Things to be showed and functionality provided when app starts.
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return view
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.my_history, container, false);

        Toolbar toolbar=(Toolbar)view.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

        FloatingActionButton mapbutton = view.findViewById(R.id.map);
        mapbutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent mapIntent = new Intent(getContext(),mapsActivity.class);
                mapIntent.putExtra("Caller","mine");
                startActivity(mapIntent);
            }
        });

        return view;
    }

    /**
     * Called when activity is created, views and functionality provided.
     * @param savedInstanceState
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        try{ allEvents = user.getAllEvents();}catch (Exception e){}
        habitEvents =(ListView)getView().findViewById(R.id.myHistory_list);

        adapter= new HistoryAdapter(getActivity(),allEvents);
        habitEvents.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        habitEvents.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Object slectedEvent= habitEvents.getItemAtPosition(position);
                adapter.showDetailPopup(getActivity(),position);
                /*Toast.makeText(getContext(), String.valueOf(position), Toast.LENGTH_SHORT).show();*/
            }
        });

        searchEvent=(EditText)getView().findViewById(R.id.searchEvent);
        searchEvent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                adapter.getFilter().filter(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


    }

    /**
     * Called when activity is resumed.
     * Update events shown in history
     */
    @Override
    public void onResume(){
        super.onResume();
        allEvents=user.getAllEvents();
        adapter.update(allEvents);

    }
}
