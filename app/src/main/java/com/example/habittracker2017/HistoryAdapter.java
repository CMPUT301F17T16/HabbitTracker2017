package com.example.habittracker2017;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.location.Address;
import android.location.Geocoder;
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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static com.example.habittracker2017.UserManager.user;

/**
 * Created by jlin7 on 2017/11/12.
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

    public HistoryAdapter(Context context, ArrayList<HabitEvent> eventsList) {
        this.context = context;
        this.eventsList = eventsList;
        this.filteredEvents = eventsList;

        getFilter();
    }

    //fix
    @Override
    public int getCount() {
        return filteredEvents.size();
    }

    @Override
    public Object getItem(int i) {
        return filteredEvents.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

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
        viewHolder.date.setText(eventDate);

        return view;

    }

    @Override
    public Filter getFilter() {
        if (eventFilter == null) {
            eventFilter = new EventFilter();
        }
        return eventFilter;
    }

    private class EventFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults filterResults = new FilterResults();
            if (constraint != null && constraint.length() > 0) {
                ArrayList<HabitEvent> tempList = new ArrayList<HabitEvent>();

                for (HabitEvent event : eventsList) {
                    if (event.getComment().toLowerCase().contains(constraint.toString().toLowerCase())) {
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

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filteredEvents = (ArrayList<HabitEvent>) results.values;
            notifyDataSetChanged();
        }
    }

    public void update(ArrayList<HabitEvent> eventsList) {
        this.eventsList = eventsList;
        this.filteredEvents = eventsList;
        this.notifyDataSetChanged();
        /*Log.i("notify change","true");  //for debugging
        Log.i("this eventList:",String.valueOf(this.eventsList.size()));
        Log.i("filteredEvents:",String.valueOf(this.filteredEvents.size()));*/
    }

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
        if (popEvent.getPicture() != null) {
            image.setImageBitmap(popEvent.getPicture());
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
}