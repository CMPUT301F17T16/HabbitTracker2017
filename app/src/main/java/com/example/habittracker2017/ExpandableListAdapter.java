/*
*ExpandableListAdapter
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

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Takes two arrays of habits and associated username's and makes a expandable list
 *
 * Code Largely learned from https://www.androidhive.info/2013/07/android-expandable-list-view-tutorial/
 * Activities the viewHistory does, subclass of fragment
 *
 * @author team 16
 * @version 1.0
 * @see BaseExpandableListAdapter
 * @since 1.0
 */

public class ExpandableListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private ArrayList<String> userNames;
    private HashMap<String, ArrayList<Habit>> habits;

    /**
     * Constructor for ExpandableListAdapter
     * @param context
     * @param userNames
     * @param habits
     */
    public ExpandableListAdapter(Context context, ArrayList<String> userNames, HashMap<String, ArrayList<Habit>> habits) {
        this.context = context;
        this.userNames = userNames;
        this.habits = habits;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition){
        return this.habits.get(this.userNames.get(groupPosition)).get(childPosition).getTitle();
    }

    @Override
    public long getChildId(int groupPosition,int childPosition){
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent){
        final String childText = (String)getChild(groupPosition,childPosition);

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater)this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.expandable_list_child,null);
        }

        TextView listItem = (TextView) convertView.findViewById(R.id.listItem);
        listItem.setText(childText);
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition){
        return this.habits.get(this.userNames.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition){
        return this.userNames.get(groupPosition);
    }

    @Override
    public int getGroupCount(){
        return this.userNames.size();
    }

    @Override
    public long getGroupId(int groupPosition){
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent){
        String headerTitle = (String)getGroup(groupPosition);

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.from(context).inflate(R.layout.expandable_list_group, null);
        }

        TextView listHeader = (TextView) convertView.findViewById(R.id.listHeader);
        listHeader.setText(headerTitle);

        return convertView;
    }

    @Override
    public boolean hasStableIds(){
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition){
        return true;
    }

}
