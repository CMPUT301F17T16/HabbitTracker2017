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
 * Created by jmark on 2017-11-11.
 */

public class viewManageHabits extends Fragment {
    protected ListView Habits;
    protected static ArrayList<Habit> allHabits = new ArrayList<Habit>();
    protected static ViewHabitAdapter adapter;
    protected static final String FILENAME ="habits.sav";
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
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.manage_habits, container, false);


        return view;
    }

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
