package com.example.habittracker2017;


import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.support.v7.widget.Toolbar;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * Created by jlin7 on 2017/11/12.
 */

public class viewMyHistory extends Fragment {

    //private HashMap<Integer, Boolean> testSchedule;
    protected static ArrayList<HabitEvent> allEvents = new ArrayList<HabitEvent>();
    protected static final String FILENAME ="events.sav";
    protected ListView habitEvents;
    protected static viewHistoryAdapter adapter;

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
        //setHasOptionsMenu(true);
    }
    //@RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.my_history, container, false);

        Toolbar toolbar=(Toolbar)view.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

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

        /*final Habit habit = new Habit("My Event", "Reason", new Date(), null,"user1");
        String comment = "Test comment";
        HabitEvent event = new HabitEvent(comment);
        habit.addEvent(event);

        final ListView listView=view.findViewById(R.id.myHistory_list);
        final HistoryAdapter adapter = new HistoryAdapter(getActivity(), habit.getEvents());
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Object slectedEvent= listView.getItemAtPosition(position);
                adapter.showDetailPopup(getActivity());
            }
        });*/

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        loadFromFile();
        habitEvents =(ListView)getView().findViewById(R.id.myHistory_list);
        adapter= new viewHistoryAdapter(allEvents,getActivity());
        habitEvents.setAdapter(adapter);
        habitEvents.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Object slectedEvent= habitEvents.getItemAtPosition(position);
                //adapter.showDetailPopup(getActivity());
            }
        });


    }

    @Override
    public void  onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        //MenuInflater inflater = getActivity().getMenuInflater();
        menuInflater.inflate(R.menu.options_menu, menu);

        SearchManager searchManager=(SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        MenuItem searchMenuItem=menu.findItem(R.id.search);
        SearchView searchView= (SearchView) searchMenuItem .getActionView();

        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        });
        //return true;
    }



    /**
     * loadFromFile
     *
     * reference https://github.com/joshua2ua/lonelyTwitter
     */
    private void loadFromFile() {
        try {
            FileInputStream fis = getContext().openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));
            Gson gson = new Gson();
            Type listType = new TypeToken<ArrayList<Habit>>(){}.getType();
            allEvents = gson.fromJson(in, listType);

        } catch (FileNotFoundException e) {
            allEvents = new ArrayList<HabitEvent>();
        }
    }
}
