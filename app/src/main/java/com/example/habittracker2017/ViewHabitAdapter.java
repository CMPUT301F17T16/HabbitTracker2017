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
    public void habitDialogShow(Context context, final int pos, long id){
        Dialog habitDialog = new Dialog(context);
        habitDialog.setContentView(R.layout.habit_dialog);
        habitDialog.setCancelable(true);

        EditText name = (EditText) habitDialog.findViewById(R.id.habitName);
        EditText start = (EditText) habitDialog.findViewById(R.id.habitStartDate);
        EditText reason = (EditText) habitDialog.findViewById(R.id.reason);
        TextView scheduleView = (TextView) habitDialog.findViewById(R.id.habitSchedule);

        final String nameHabit = list.get(pos).getTitle();
        String reasonHabit = list.get(pos).getReason();
        Date startHabit = list.get(pos).getStartDate();
        String scheduleHabit = " ";
        String startHabitString = startHabit.toString();
        HashMap schedule = list.get(pos).getSchedule();

        Iterator myVeryOwnIterator = schedule.keySet().iterator();
        while(myVeryOwnIterator.hasNext()) {
            Integer key=(Integer) myVeryOwnIterator.next();
            Boolean value=(Boolean) schedule.get(key);

            if (value == true){
                scheduleHabit = scheduleHabit + DayOfWeek.of(key).toString();
            }
        }

        name.setText(nameHabit);
        reason.setText(reasonHabit);
        start.setText(startHabitString);
        scheduleView.setText("Every" + scheduleHabit);

        /*
        Button delete = (Button) habitDialog.findViewById(R.id.habitDelete);
        delete.setOnClickListener(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                user.deleteHabit(list.get(pos).Habit);
            }
        });
*/
        habitDialog.show();
    }
}
