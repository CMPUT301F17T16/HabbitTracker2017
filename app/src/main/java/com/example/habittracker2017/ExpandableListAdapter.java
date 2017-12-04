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
 * Created by Jonah Cowan on 2017-12-03.
 * Takes two arrays of habits and assosiated username
 */

public class ExpandableListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private ArrayList<String> userNames;
    private HashMap<String, ArrayList<String>> habits;

    public ExpandableListAdapter(Context context, ArrayList<String> userNames, HashMap<String, ArrayList<String>> habits) {
        this.context = context;
        this.userNames = userNames;
        this.habits = habits;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition){
        return this.habits.get(this.userNames.get(groupPosition)).get(childPosition);
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
