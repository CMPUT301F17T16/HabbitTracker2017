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
 *Other's Activities fragment shows a list of other users that this user is following
 * and each user followed contains a list of their habits and the habit's most recent event
 *
 * @author team 16
 * @version 1.0
 * @see User
 * @see UserManager
 * @see mapsActivity
 * @see OthersStatView
 * @see ExpandableListAdapter
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

    /**
     * Required empty constructor
     */
    public OthersFragment() {

    }

    /**
     * Required, but not to be used with fragment
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
     * Inflate the expandable list for Other's Activities view
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return view
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.expandable_list, container, false);


        return view;
    }

    /**
     * When activity created, load all following users of current user
     * Get each following user's all habit, and the habit's latest event
     * There is expandable list for every following user for list of their habits
     * When click on View Stat button, OthersStatView Activity is called to show habit stat
     * Click on habit's event button to show its most recent event with informative details
     * Click on the map button to show following's event around current user location
     * @param savedInstanceState
     */
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
            followedUserNames = new ArrayList<String>();
            for (User user : followedUsers){
                followedUserNames.add(user.getName());
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
