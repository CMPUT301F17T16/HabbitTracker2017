package com.example.habittracker2017;


import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.support.constraint.ConstraintLayout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import java.util.ArrayList;

/**
 * Created by jlin7 on 2017/11/12.
 */

public class HistoryAdapter extends ArrayAdapter<HabitEvent> {
    private String eventName;
    private String comment;

    private static class ViewHolder{
        TextView title;
        TextView date;
        EditText habitName;
    }

    public HistoryAdapter(Context context, ArrayList<HabitEvent> events){
        super(context, R.layout.history_list, events);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        //View pop;
        HabitEvent event = getItem(position);
        ViewHolder viewHolder;

        //LayoutInflater inflator = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //pop = inflator.inflate(R.layout.history_event_popup, null);

        if(convertView== null){
            viewHolder=new ViewHolder();
            LayoutInflater inflater=LayoutInflater.from(getContext());
            convertView=inflater.inflate(R.layout.history_list, parent,false);
            viewHolder.title= convertView.findViewById(R.id.event_name);
            viewHolder.date= convertView.findViewById(R.id.event_date);
            //viewHolder.habitName=(EditText)pop.findViewById(R.id.habit_name);

            convertView.setTag(viewHolder);
        }else{
            viewHolder=(ViewHolder) convertView.getTag();
            }

        viewHolder.title.setText(event.getHabit().getTitle());
        //viewHolder.habitName.setText("hello");
        this.eventName=event.getHabit().getTitle();
        viewHolder.date.setText(event.getDate().toString());
        this.comment=event.getComment();

        return convertView;
    }

    public void showDetailPopup(final Activity context) {

        ConstraintLayout viewGroup = context.findViewById(R.id.linearLayout2);


        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = layoutInflater.inflate(R.layout.history_event_popup,null);

        final PopupWindow changeStatusPopUp = new PopupWindow(context);
        EditText name= (EditText)layout.findViewById(R.id.habit_name);
        name.setText(this.eventName);
        EditText comment=(EditText) layout.findViewById(R.id.event_comment);
        comment.setText(this.comment);
        changeStatusPopUp.setContentView(layout);
        changeStatusPopUp.setWidth(LinearLayout.LayoutParams.WRAP_CONTENT);
        changeStatusPopUp.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        changeStatusPopUp.setFocusable(true);

        changeStatusPopUp.setBackgroundDrawable(new BitmapDrawable());

        changeStatusPopUp.showAtLocation(layout, Gravity.CENTER, 0, 0);
    }

}



