/*
*OthersFragment
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
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 *
 * @author team 16
 * @version 1.0
 * @see Fragment
 * @since 1.0
 */

public class OthersFragment extends Fragment {

    private ArrayList<User> followedUsers;
    ArrayList<String> followedUserNames;
    protected static ArrayList<HabitEvent> allEvents = new ArrayList<HabitEvent>();
    private ExpandableListAdapter adapter;
    private ExpandableListView expandableListView;
    private ArrayList<Habit> habits;
//    private ArrayList<String> habitTitles = new ArrayList<>();
    private HashMap<String, ArrayList<Habit>> userHabits = new HashMap<>();

    public static OthersFragment newInstance(int position) {
        OthersFragment fragment = new OthersFragment();
        Bundle args = new Bundle();
        args.putInt("position", position);
        fragment.setArguments(args);

        return fragment;
    }

    public OthersFragment() {

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
        View view = inflater.inflate(R.layout.expandable_list, container, false);


        return view;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        User currentUser = UserManager.user;
        if(currentUser != null) {

            followedUserNames = currentUser.getRequests();
            for (String us : followedUserNames) {
                Log.i("us", us);
            }

            //Load in followed users
            RemoteClient.loadUsers task = new RemoteClient.loadUsers();
            task.execute(followedUserNames);
            followedUsers = new ArrayList<>();
            try {
                followedUsers = task.get();
            } catch (Exception e) {
                Log.i("Error", "Something went wrong when we tried to communicate with the elasticsearch server!");
            }

            expandableListView = (ExpandableListView) getView().findViewById(R.id.mainList);

//        ArrayList<String> habitTitles = new ArrayList<>();
            userHabits = new HashMap<>();

            for (int i = 0; i < followedUsers.size(); i++) {
                habits = followedUsers.get(i).getHabits();
                for (Habit habit : habits) {
                    allEvents.add(habit.getLastEvent());
                }
/*            habitTitles.clear();
            for(int j=0;j<habits.size();j++){
                habitTitles.add(habits.get(j).getTitle());
            }*/
                userHabits.put(followedUserNames.get(i), habits);

            }

            adapter = new ExpandableListAdapter(getContext(), followedUserNames, userHabits);

            expandableListView.setAdapter(adapter);


            expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                @Override
                public boolean onChildClick(ExpandableListView expandableListView, View view, int groupPosition, int childPosition, long l) {
                    Intent intent = new Intent(getContext(), OthersStatView.class);
                    intent.putExtra("Username", followedUserNames.get(groupPosition));
                    intent.putExtra("habitLocation", childPosition);

                    getContext().startActivity(intent);
                    return false;
                }
            });

        }else {Toast.makeText(getContext(), "Cannot load user object!", Toast.LENGTH_LONG).show();}

        FloatingActionButton mapbutton = getView().findViewById(R.id.map);
        mapbutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent mapIntent = new Intent(getContext(),mapsActivity.class);
                mapIntent.putExtra("Caller","others");
                startActivity(mapIntent);
            }
        });

    }

}
