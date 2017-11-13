package com.example.habittracker2017;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;

/**
 * Created by jlin7 on 2017/11/12.
 */

public class HistoryAdapter extends ArrayAdapter<HabitEvent> {

    private static class ViewHolder{
        TextView title;
        TextView date;
    }

    public HistoryAdapter(Context context, ArrayList<HabitEvent> events){
        super(context, R.layout.history_list, events);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        HabitEvent event = getItem(position);
        ViewHolder viewHolder;
        if(convertView== null){
            viewHolder=new ViewHolder();
            LayoutInflater inflater=LayoutInflater.from(getContext());
            convertView=inflater.inflate(R.layout.history_list, parent,false);
            viewHolder.title= convertView.findViewById(R.id.event_name);
            viewHolder.date= convertView.findViewById(R.id.event_date);

            convertView.setTag(viewHolder);
        }else{
            viewHolder=(ViewHolder) convertView.getTag();
            }

        viewHolder.title.setText(event.getHabit().getTitle());
        viewHolder.date.setText(event.getDate().toString());

        return convertView;
    }

}
