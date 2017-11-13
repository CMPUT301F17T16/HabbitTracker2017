package com.example.habittracker2017;

import android.app.Dialog;
import android.content.Context;
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

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by hyuan2 on 2017-11-12.
 */

public class ViewHabitAdapter extends BaseAdapter implements ListAdapter {
    private ArrayList<Habit> list = new ArrayList<Habit>();
    private Context context;

    public ViewHabitAdapter(ArrayList<Habit> list, Context context){
        this.list = list;
        this.context = context;
    }
    @Override
    public int getCount(){
        return list.size();
    }
    @Override
    public Object getItem(int pos){
        return list.get(pos);
    }
    @Override
    public long getItemId(int pos) {
        return 0;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_item, null);
        }
        TextView info =  view.findViewById(R.id.info);
        info.setText(list.get(position).toString());
        return view;
    }
    public void sortHabitOwner(String name){
        ArrayList<Habit> newList = new ArrayList<Habit>();
        for (int i = 0;i<list.size();i++ ){
            if (this.list.get(i).getOwner().equals(name)){
                newList.add(list.get(i));
            }
        }
        this.list = newList;
    }
    public void sortTodayHabit(){
        ArrayList<Habit> newList = new ArrayList<Habit>();
        for (int i = 0;i<list.size();i++ ){
            if (this.list.get(i).isDue()){
                newList.add(list.get(i));
            }
        }
        this.list = newList;
    }
    public void habitDialogShow(Context context, int pos, long id){
        Dialog habitDialog = new Dialog(context);
        habitDialog.setContentView(R.layout.habit_dialog);
        habitDialog.setCancelable(true);

        EditText name = (EditText) habitDialog.findViewById(R.id.habitName);
        EditText start = (EditText) habitDialog.findViewById(R.id.habitStartDate);
        EditText reason = (EditText) habitDialog.findViewById(R.id.reason);

        final String nameHabit = list.get(pos).getTitle();
        String reasonHabit = list.get(pos).getReason();
        Date startHabit = list.get(pos).getStartDate();
        String startHabitString = startHabit.toString();
        HashMap schedule = list.get(pos).getSchedule();

        /*
        for (HashMap.Entry<Integer, String> entry : schedule.entrySet()) {
            Integer key = entry.getKey();
            String value = entry.getValue();


        }*/

        name.setText(nameHabit);
        reason.setText(reasonHabit);
        start.setText(startHabitString);

        habitDialog.show();
    }
}
