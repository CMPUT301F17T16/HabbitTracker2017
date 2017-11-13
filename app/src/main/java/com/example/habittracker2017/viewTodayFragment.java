package com.example.habittracker2017;


import android.content.Intent;
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

import static com.example.habittracker2017.UserManager.user;

/**
 * Created by hyuan2 on 2017-11-12.
 */

public class viewTodayFragment extends Fragment {
    protected ListView todaysHabit;
    protected static ArrayList<Habit> allHabits = new ArrayList<Habit>();
    protected static ViewHabitAdapter adapter;
    protected static final String FILENAME ="habits.sav";

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
        loadFromFile();
        todaysHabit = (ListView) getView().findViewById(R.id.todaysHabit);
        adapter = new ViewHabitAdapter(allHabits,getActivity());
        if (user!=null){adapter.sortHabitOwner(user.getName());}
        adapter.sortTodayHabit();
        todaysHabit.setAdapter(adapter);
        todaysHabit.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(),CreateEventActivity.class);
                intent.putExtra("Habit",position);
                getContext().startActivity(intent);
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
