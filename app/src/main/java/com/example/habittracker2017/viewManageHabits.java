package com.example.habittracker2017;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import java.util.Date;

import static com.example.habittracker2017.UserManager.user;

/**
 * Created by jmark on 2017-11-11.
 */

public class viewManageHabits extends Fragment {
    protected ListView Habits;
    protected static ArrayList<Habit> allHabits = new ArrayList<Habit>();
    protected static ViewHabitAdapter adapter;
    protected static final String FILENAME ="habits.sav";

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
        loadFromFile();
        Habits = (ListView) getView().findViewById(R.id.listHabits);
        adapter = new ViewHabitAdapter(allHabits,getActivity());
        adapter.sortHabitOwner(user.getName());
        Habits.setAdapter(adapter);
        Habits.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                adapter.habitDialogShow(getActivity(),position,id);
            }
        });
    }

    /**
     * loadFromFile
     *
     * reference https://github.com/joshua2ua/lonelyTwitter
     */
    private void loadFromFile() {
        try {
            FileInputStream fis = getContext().openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));
            Gson gson = new Gson();
            Type listType = new TypeToken<ArrayList<Habit>>(){}.getType();
            allHabits = gson.fromJson(in, listType);

        } catch (FileNotFoundException e) {
            allHabits = new ArrayList<Habit>();
        }
    }

}
