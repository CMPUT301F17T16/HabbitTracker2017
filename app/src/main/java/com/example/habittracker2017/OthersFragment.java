/*
*OthersFragment
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

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ListView;

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

        User currentUser = UserManager.user;
        if(currentUser == null){
            return view;
        }
        ArrayList<String> followedUserNames = currentUser.getFollowing();

        //Load in followed users
        RemoteClient.loadUsers task = new RemoteClient.loadUsers();
        task.execute(followedUserNames);
        ArrayList<User> followedUsers = new ArrayList<>();
        try{
            followedUsers = task.get();
        }catch(Exception e){
            Log.i("Error", "Something went wrong when we tried to communicate with the elasticsearch server!");
        }

        ExpandableListView expandableListView = (ExpandableListView) view.findViewById(R.id.mainList);

        ArrayList<Habit> habits;
        ArrayList<String> habitTitles = new ArrayList<>();
        HashMap<String, ArrayList<String>> userHabits = new HashMap<>();

        for(int i=0;i<followedUsers.size();i++){
            habits = followedUsers.get(i).getHabits();
            habitTitles.clear();
            for(int j=0;j<habits.size();j++){
                habitTitles.add(habits.get(j).getTitle());
            }
            userHabits.put(followedUserNames.get(i),habitTitles);

        }

        ExpandableListAdapter adapter = new ExpandableListAdapter(getContext(),followedUserNames, userHabits);

        expandableListView.setAdapter(adapter);

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int groupPosition, int childPosition, long l) {

                return false;
            }
        });

/*        ListView othersActivities = (ListView) view.findViewById(R.id.followedUserListView);
        ExpandableListAdapter adapter = new ExpandableListAdapter(getContext(), followedUsers);
        othersActivities.setAdapter(adapter);*/


        return view;
    }

}
