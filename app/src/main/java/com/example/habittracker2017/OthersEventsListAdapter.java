/*
*OthersEventsListAdapter
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
 *Adapter for other's activity tab
 *
 * @author team 16
 * @version 1.0
 * @see BaseAdapter
 * @since 1.0
 */

public class OthersEventsListAdapter extends BaseAdapter{
    private Context context;
    private ArrayList<User> followedUsers;

    public OthersEventsListAdapter(Context context, ArrayList<User> followedUsers){
        this.followedUsers = followedUsers;
        this.context = context;
    }

    /**
     *
     * @return number of followed users
     */
    @Override
    public int getCount(){
        return followedUsers.size();
    }

    /**
     *
     * @param position
     * @return the followed user element at position
     */
    @Override
    public Object getItem(int position){
        return followedUsers.get(position);
    }

    /**
     *
     * @param position
     * @return
     */
    @Override
    public long getItemId(int position){
        return position;
    }

    /**
     * Inflate and set content of other's activity tab
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.others_activity_list_item, parent, false);
        }

        User currentUser = (User)getItem(position);
        ArrayList<Habit> currentUserHabits = currentUser.getHabits();

        TextView followedUserName = (TextView) convertView.findViewById(R.id.followedUserNameView);
        ListView followedUserHabits = (ListView) convertView.findViewById(R.id.followedUserEventsList);

        OthersEventsListElementAdapter adapter = new OthersEventsListElementAdapter(context, currentUserHabits);
        followedUserHabits.setAdapter(adapter);

        followedUserName.setText(currentUser.getName());

        return convertView;
    }
}
