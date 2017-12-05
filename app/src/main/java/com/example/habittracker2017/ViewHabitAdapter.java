/*
*ViewHabitAdapter
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

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static com.example.habittracker2017.UserManager.user;

/**
 *Adaptor for habit
 *
 * @author team 16
 * @version 1.0
 * @see BaseAdapter
 * @since 1.0
 */

public class ViewHabitAdapter extends BaseAdapter implements ListAdapter {
    private ArrayList<Habit> list = new ArrayList<Habit>();
    private Context context;

    /**
     * Constructor for ViewHabitAdapter
     * @param list habits for display
     * @param context
     */
    public ViewHabitAdapter(ArrayList<Habit> list, Context context){
        this.list = list;
        this.context = context;
    }

    /**
     *
     * @return size of list
     */
    @Override
    public int getCount(){
        return list.size();
    }

    /**
     *
     * @param pos
     * @return element of list at pos
     */
    @Override
    public Object getItem(int pos){
        return list.get(pos);
    }

    /**
     *
     * @param pos
     * @return
     */
    @Override
    public long getItemId(int pos) {
        return 0;
    }

    /**
     * Inflate view for list item, and set view content for display
     * @param position
     * @param convertView
     * @param parent
     * @return view
     */
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_item, null);
        }
        TextView info =  view.findViewById(R.id.info);
        TextView startDate = view.findViewById(R.id.start_date);
        info.setText(list.get(position).toString());
        startDate.setText("Start date: " + list.get(position).getStartDateString());
        return view;
    }

    /**
     * This method sorts out Habits that is due today from all habits of user
     */
    public void sortTodayHabit(){
        ArrayList<Habit> newList = new ArrayList<Habit>();
        for (int i = 0;i<list.size();i++ ){
            if (this.list.get(i).isDue()){
                newList.add(list.get(i));
            }
        }
        this.list = newList;
    }
}
