package com.example.habittracker2017;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import static com.example.habittracker2017.UserManager.user;

/**
 * Created by jlin7 on 2017/11/12.
 */

public class viewMyHistory extends Fragment {

    //private HashMap<Integer, Boolean> testSchedule;

    public static viewMyHistory newInstance(int position) {
        viewMyHistory fragment = new viewMyHistory();
        Bundle args = new Bundle();
        args.putInt("position", position);
        fragment.setArguments(args);

        return fragment;
    }

    public viewMyHistory(){}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.my_history);
    }
    //@RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_history, container, false);

        View mapview=inflater.inflate(R.layout.my_history,container,false);
        FloatingActionButton mapbutton = view.findViewById(R.id.map);
        mapbutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String uri = String.format(Locale.ENGLISH, "geo:53.631,-113.3239");
                Uri gmmIntentUri = Uri.parse(uri);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            }
        });

        /*testSchedule.put(0, false);       this stops the app, so I set schedule to null*/

        /*test create a habit and habit event to display in my history tab*/
        final Habit habit = new Habit("Title", "Reason", new Date(), null,"user1");
        String comment = "Test comment";
        HabitEvent event = new HabitEvent(comment);
        habit.addEvent(event);

        final ListView listView=view.findViewById(R.id.myHistory_list);
        HistoryAdapter adapter = new HistoryAdapter(getActivity(), habit.getEvents());
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Object slectedEvent= listView.getItemAtPosition(position);
                showDetailPopup(getActivity());
            }
        });

        return view;
    }

    private void showDetailPopup(final Activity context) {

    ConstraintLayout viewGroup = context.findViewById(R.id.linearLayout2);
    LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(getActivity().LAYOUT_INFLATER_SERVICE);
    View layout = layoutInflater.inflate(R.layout.history_event_popup,null);

    final PopupWindow changeStatusPopUp = new PopupWindow(context);
    changeStatusPopUp.setContentView(layout);
    changeStatusPopUp.setWidth(LinearLayout.LayoutParams.WRAP_CONTENT);
    changeStatusPopUp.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
    changeStatusPopUp.setFocusable(true);

    changeStatusPopUp.setBackgroundDrawable(new BitmapDrawable());

    changeStatusPopUp.showAtLocation(layout, Gravity.CENTER, 0, 0);
    }
}
