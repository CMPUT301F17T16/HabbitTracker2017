/*
*OthersEventsListElementAdapter
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
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static java.security.AccessController.getContext;

/**
 *
 *
 * @author team 16
 * @version 1.0
 * @see BaseAdapter
 * @since 1.0
 */

public class OthersEventsListElementAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Habit> habits;

    public OthersEventsListElementAdapter(Context context, ArrayList<Habit> habits){
        this.habits = habits;
        this.context = context;
    }

    @Override
    public int getCount(){
        return habits.size();
    }

    @Override
    public Object getItem(int position){
        return habits.get(position);
    }

    @Override
    public long getItemId(int position){
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.others_activity_internal_item, parent, false);
        }
        final Habit currentHabit = (Habit)getItem(position);

        TextView habitName = (TextView) convertView.findViewById(R.id.habitNameView);
        Button eventButton = (Button) convertView.findViewById(R.id.eventsButton);

        habitName.setText(currentHabit.getTitle());

        eventButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(!currentHabit.getEvents().isEmpty()){
                    //Show habit Events
                    Intent intent = new Intent(context, StatView.class);
                    context.startActivity(intent);

                }else{
                    Toast.makeText(context, "No Events in this habit", Toast.LENGTH_LONG).show();
                }
            }
        });

        return convertView;
    }
}
