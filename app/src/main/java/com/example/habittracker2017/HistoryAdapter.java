/*
*HistoryAdapter
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
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Space;
import android.widget.TextView;

import org.mockito.internal.matchers.Null;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static com.example.habittracker2017.UserManager.user;

/**
 * Adaptor for viewMyHistory, also has filtering events functionality
 *
 * @author team 16
 * @version 1.0
 * @see viewMyHistory
 * @since 1.0
 */
public class HistoryAdapter extends BaseAdapter implements Filterable {
    private Context context;
    private ArrayList<HabitEvent> eventsList;
    private ArrayList<HabitEvent> filteredEvents;
    private EventFilter eventFilter;
    private PopupWindow changeStatusPopUp;

    private static class ViewHolder {
        TextView title;
        TextView date;
    }

    /**
     * Constructor for HistoryAdapter
     * @param context
     * @param eventsList
     */
    public HistoryAdapter(Context context, ArrayList<HabitEvent> eventsList) {
        this.context = context;
        this.eventsList = eventsList;
        this.filteredEvents = eventsList;

        getFilter();
    }

    /**
     * Get filteredEvents size
     * @return size of filteredEvents list
     */
    @Override
    public int getCount() {
        return filteredEvents.size();
    }

    /**
     * Get element in filteredEvents at index i
     * @param i
     * @return element in filteredEvents
     */
    @Override
    public Object getItem(int i) {
        return filteredEvents.get(i);
    }

    /**
     *
     * @param i
     * @return 0
     */
    @Override
    public long getItemId(int i) {
        return 0;
    }

    /**
     * Inflate view and set view contents
     * @param position
     * @param view
     * @param parent
     * @return view
     */
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        final ViewHolder viewHolder;
        final HabitEvent event = (HabitEvent) getItem(position);

        if (view == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.history_list, null);
            viewHolder.title = view.findViewById(R.id.event_name);
            viewHolder.date = view.findViewById(R.id.event_date);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.title.setText(event.getHabit() + " event");
        DateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd");
        String eventDate = simpleDate.format(event.getDate());
        viewHolder.date.setText(eventDate+"  Comment: "+event.getComment());

        return view;

    }

    /**
     * Create one eventFilter object
     * @return Filter
     */
    @Override
    public Filter getFilter() {
        if (eventFilter == null) {
            eventFilter = new EventFilter();
        }
        return eventFilter;
    }

    /**
     * A filter that filter events
     * @version 1.0
     * @see Filter
     * @since 1.0
     */
    private class EventFilter extends Filter {

        /**
         * Filter events by Habit type and comments using user input keyword
         * @param constraint
         * @return FilterResults
         */
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults filterResults = new FilterResults();
            if (constraint != null && constraint.length() > 0) {
                ArrayList<HabitEvent> tempList = new ArrayList<HabitEvent>();

                for (HabitEvent event : eventsList) {
                    if (event.getComment().toLowerCase().contains(constraint.toString().toLowerCase())) {
                        tempList.add(event);
                    } else if (event.getHabit().toLowerCase().contains(constraint.toString().toLowerCase())) {
                        tempList.add(event);
                    }
                }
                filterResults.count = tempList.size();
                filterResults.values = tempList;

            } else {
                filterResults.count = eventsList.size();
                filterResults.values = eventsList;
            }
            return filterResults;
        }

        /**
         * filterEvents is set to filter result for displaying the search result
         * @param constraint
         * @param results
         */
        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filteredEvents = (ArrayList<HabitEvent>) results.values;
            notifyDataSetChanged();
        }
    }

    /**
     * Receive all events that user created, update the eventList
     * @param eventsList
     */
    public void update(ArrayList<HabitEvent> eventsList) {
        this.eventsList = eventsList;
        this.filteredEvents = eventsList;
        this.notifyDataSetChanged();
    }

    /**
     * Inflate pop up window view, set view contents and display
     * Shows the details of habit event at position "pos", able to edit event detail and delete the event
     * @param context
     * @param pos
     */
    public void showDetailPopup(Context context, int pos) {

        final HabitEvent popEvent = filteredEvents.get(pos);


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
        ImageView image = (ImageView) layout.findViewById(R.id.popEvent_pic);
        if (popEvent.getBitmapString() != null) {
            image.setImageBitmap(getBitmapFromString(popEvent.getBitmapString()));
        }
        TextView location = (TextView) layout.findViewById(R.id.popEvent_location);
        if (popEvent.getLocation()!=null){
            List<Address> addresses;
            Geocoder geocoder= new Geocoder(context, Locale.getDefault());
            try{addresses=geocoder.getFromLocation(popEvent.getLocation().getLatitude(),popEvent.getLocation().getLongitude(),1);
                String address=addresses.get(0).getAddressLine(0);
                String city=addresses.get(0).getLocality();
                String prov=addresses.get(0).getAdminArea();
                String country=addresses.get(0).getCountryName();
                String postalCode=addresses.get(0).getPostalCode();
                location.setText(address);
                location.append(" "+city+" "+prov+" "+country+" "+postalCode);
            }
            catch (IOException e){e.printStackTrace();}
        }

        changeStatusPopUp.setContentView(layout);
        changeStatusPopUp.setWidth(LinearLayout.LayoutParams.WRAP_CONTENT);
        changeStatusPopUp.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        changeStatusPopUp.setFocusable(true);

        changeStatusPopUp.setBackgroundDrawable(new BitmapDrawable());

        changeStatusPopUp.showAtLocation(layout, Gravity.CENTER, 0, 0);


        Button delete = layout.findViewById(R.id.event_delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Habit> userHabit = user.getHabits();
                for (Habit habit : userHabit) {
                    if (popEvent.getHabit().equals(habit.getTitle())) {
                        habit.deleteEvent(popEvent);
                        UserManager.save();
                        eventsList.remove(popEvent);
                        filteredEvents = eventsList;
                        notifyDataSetChanged();
                    }
                }
                changeStatusPopUp.dismiss();
            }
        });

        Button save = (Button) layout.findViewById(R.id.event_change);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popEvent.setComment(comment.getText().toString());
                notifyDataSetChanged();
                UserManager.save();
                changeStatusPopUp.dismiss();
            }
        });
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