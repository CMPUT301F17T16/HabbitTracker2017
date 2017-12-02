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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

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

import static com.example.habittracker2017.UserManager.user;

/**
 * Created by jlin7 on 2017/11/12.
 */

public class viewMyHistory extends Fragment {

    protected static ArrayList<HabitEvent> allEvents = new ArrayList<HabitEvent>();
    protected static final String FILENAME ="events.sav";
    protected ListView habitEvents;
    private HistoryAdapter adapter;
    private SearchView searchView;
    private MenuItem searchMenuItem;

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
                String uri = String.format(Locale.ENGLISH, "geo:53.521,-113.521");
                Uri gmmIntentUri = Uri.parse(uri);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
                /*Intent intent = new Intent(v.getContext(), MapMarker.class);
                viewMyHistory.this.startActivity(intent);*/
            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        try{ allEvents = user.getAllEvents();}catch (Exception e){}
        /*Log.i("here length:",String.valueOf(viewMyHistory.allEvents.size()));
        Log.i("here title:",allEvents.get(0).getHabit());*/
        habitEvents =(ListView)getView().findViewById(R.id.myHistory_list);

        adapter= new HistoryAdapter(getActivity(),allEvents);
        habitEvents.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        habitEvents.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Object slectedEvent= habitEvents.getItemAtPosition(position);
                //adapter.showDetailPopup(getActivity());
                Toast.makeText(getContext(), String.valueOf(position), Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public void onResume(){
        super.onResume();
        allEvents=user.getAllEvents();
        habitEvents.setAdapter(new HistoryAdapter(getActivity(),allEvents));
        /*adapter.swapEvents(allEvents);*/
        Log.i("on Resume:",String.valueOf(user.getAllEvents().size()));
    }

    @Override
    public void  onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        //MenuInflater inflater = getActivity().getMenuInflater();
        menuInflater.inflate(R.menu.options_menu, menu);

        SearchManager searchManager=(SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        searchMenuItem=menu.findItem(R.id.search);
        searchView= (SearchView) searchMenuItem .getActionView();

        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return true;
            }
        });
        //return true;
    }
}
