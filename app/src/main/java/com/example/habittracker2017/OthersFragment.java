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
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by hyuan2 on 2017-11-12.
 */

public class OthersFragment extends Fragment {

    private ArrayList<User> followedUsers;
    ArrayList<String> followedUserNames;
    protected static ArrayList<HabitEvent> allEvents = new ArrayList<HabitEvent>();

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
       followedUserNames = currentUser.getFollowing();

        //Load in followed users
        RemoteClient.loadUsers task = new RemoteClient.loadUsers();
        task.execute(followedUserNames);
        followedUsers = new ArrayList<>();
        try{
            followedUsers = task.get();
        }catch(Exception e){
            Log.i("Error", "Something went wrong when we tried to communicate with the elasticsearch server!");
        }

        ExpandableListView expandableListView = (ExpandableListView) view.findViewById(R.id.mainList);

        ArrayList<Habit> habits;
        ArrayList<String> habitTitles = new ArrayList<>();
        HashMap<String, ArrayList<Habit>> userHabits = new HashMap<>();

        for(int i=0;i<followedUsers.size();i++){
            habits = followedUsers.get(i).getHabits();
            for (Habit habit : habits){
                allEvents.add(habit.getLastEvent());
            }
/*            habitTitles.clear();
            for(int j=0;j<habits.size();j++){
                habitTitles.add(habits.get(j).getTitle());
            }*/
            userHabits.put(followedUserNames.get(i),habits);

        }

        ExpandableListAdapter adapter = new ExpandableListAdapter(getContext(),followedUserNames, userHabits);

        expandableListView.setAdapter(adapter);

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int groupPosition, int childPosition, long l) {
                Intent intent = new Intent(getContext(), OthersStatView.class);
                intent.putExtra("Username",followedUserNames.get(groupPosition));
                intent.putExtra("habitLocation",childPosition);

                getContext().startActivity(intent);
                return false;
            }
        });

        FloatingActionButton mapbutton = view.findViewById(R.id.map);
        mapbutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent mapIntent = new Intent(getContext(),mapsActivity.class);
                mapIntent.putExtra("Caller","others");
                startActivity(mapIntent);
            }
        });

/*        ListView othersActivities = (ListView) view.findViewById(R.id.followedUserListView);
        ExpandableListAdapter adapter = new ExpandableListAdapter(getContext(), followedUsers);
        othersActivities.setAdapter(adapter);*/


        return view;
    }

}
