package com.example.habittracker2017;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import static java.security.AccessController.getContext;

/**
 * Created by Jonah Cowan on 2017-11-30.
 */

public class OthersEventsListAdapter extends BaseAdapter{
    private Context context;
    private ArrayList<User> followedUsers;

    public OthersEventsListAdapter(Context context, ArrayList<User> followedUsers){
        this.followedUsers = followedUsers;
        this.context = context;
    }

    @Override
    public int getCount(){
        return followedUsers.size();
    }

    @Override
    public Object getItem(int position){
        return followedUsers.get(position);
    }

    @Override
    public long getItemId(int position){
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.others_activity_list_item, parent, false);
        }

        User currentUser = (User)getItem(position);
        ArrayList<Habit> currentUserHabits = currentUser.getHabits();
        ArrayList<HabitEvent> currentUserEvents = new ArrayList<>();

        //get all habit events
        for(int i=0; i<currentUserHabits.size();i++){
            currentUserEvents.addAll(currentUserHabits.get(i).getEvents());
        }

        TextView followedUserName = (TextView) convertView.findViewById(R.id.followedUserNameView);
        ListView followedUserEvents = (ListView) convertView.findViewById(R.id.followedUserEventsList);

        HistoryAdapter adapter = new HistoryAdapter(context, currentUserEvents);
        followedUserEvents.setAdapter(adapter);

        followedUserName.setText(currentUser.getName());

        return convertView;
    }
}
