/*
*ExpandableListAdapter
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
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Base64;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import static com.example.habittracker2017.UserManager.user;

/**
 * Created by Jonah Cowan on 2017-12-03.
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
    private PopupWindow changeStatusPopUp;

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
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent){
        final String childText = (String)getChild(groupPosition,childPosition);

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater)this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.expandable_list_child,null);
        }

        Button viewStat = (Button) convertView.findViewById(R.id.button_stat);
        Button viewEvent = (Button) convertView.findViewById(R.id.button_event);

        TextView listItem = (TextView) convertView.findViewById(R.id.listItem);
        listItem.setText(childText);

        viewEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDetailPopup(v.getContext(),habits.get(userNames.get(groupPosition)).get(childPosition));
            }
        });

        viewStat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), OthersStatView.class);
                intent.putExtra("Username", userNames.get(groupPosition));
                intent.putExtra("habitLocation", childPosition);

                v.getContext().startActivity(intent);
            }
        });
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

    /**
     * Inflate pop up window view, set view contents and display
     * Shows the details of habit event at position "pos", able to edit event detail and delete the event
     * @param context
     * @param habit
     */
    public void showDetailPopup(Context context, Habit habit) {

        final HabitEvent popEvent = habit.getLastEvent();

        if (popEvent!=null) {

            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = layoutInflater.inflate(R.layout.history_event_popup, null);

            changeStatusPopUp = new PopupWindow(context);
            TextView name = (TextView) layout.findViewById(R.id.habit_name);
            name.setText(popEvent.getHabit());
            TextView date = (TextView) layout.findViewById(R.id.popEvent_date);
            DateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd");
            String popDate = simpleDate.format(popEvent.getDate());
            date.setText(popDate);
            final EditText comment = (EditText) layout.findViewById(R.id.event_comment);
            comment.setText(popEvent.getComment());
            comment.setKeyListener(null);
            ImageView image = (ImageView) layout.findViewById(R.id.popEvent_pic);
            if (popEvent.getBitmapString() != null) {
                image.setImageBitmap(getBitmapFromString(popEvent.getBitmapString()));
            }
            TextView location = (TextView) layout.findViewById(R.id.popEvent_location);
            if (popEvent.getLocation() != null) {
                List<Address> addresses;
                Geocoder geocoder = new Geocoder(context, Locale.getDefault());
                try {
                    addresses = geocoder.getFromLocation(popEvent.getLocation().getLatitude(), popEvent.getLocation().getLongitude(), 1);
                    String address = addresses.get(0).getAddressLine(0);
                    String city = addresses.get(0).getLocality();
                    String prov = addresses.get(0).getAdminArea();
                    String country = addresses.get(0).getCountryName();
                    String postalCode = addresses.get(0).getPostalCode();
                    location.setText(address);
                    location.append(" " + city + " " + prov + " " + country + " " + postalCode);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            changeStatusPopUp.setContentView(layout);
            changeStatusPopUp.setWidth(LinearLayout.LayoutParams.WRAP_CONTENT);
            changeStatusPopUp.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
            changeStatusPopUp.setFocusable(true);

            changeStatusPopUp.setBackgroundDrawable(new BitmapDrawable());

            changeStatusPopUp.showAtLocation(layout, Gravity.CENTER, 0, 0);
            Button delete = layout.findViewById(R.id.event_delete);
            Button save = (Button) layout.findViewById(R.id.event_change);
            delete.setVisibility(View.GONE);
            save.setVisibility(View.GONE);
        }else {
            Toast.makeText(context, "This habit does not have a habit event!", Toast.LENGTH_SHORT).show();
        }


    }

    /**
     * Turn bitmap in string format to bitmap format
     * @param bitmapString
     * @return
     */
    private Bitmap getBitmapFromString(String bitmapString) {

        byte[] decodedString = Base64.decode(bitmapString, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        return decodedByte;
    }
}
