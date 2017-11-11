package com.example.habittracker2017;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TabHost;

public class HabitTracker2017MainActivity extends AppCompatActivity {

    boolean connection = false;

    TabHost parentTabs;
    TabHost childTabs;
    TabManager tabManager;

    public View inputViewTab1;
    public View inputViewTab2;
    public View inputViewTab3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit_tracker2017);

        //Starts UserManager singleton, if it does not exist.
        UserManager.init(this.getApplicationContext());


        //Check current internet status
        connection = InternetStatus.CheckInternetConnection(HabitTracker2017MainActivity.this);

        //Goes to user creation activity is there is no user found
        if(UserManager.user == null){
            Intent intent = new Intent(this, CreateUserActivity.class);
            startActivity(intent);
        }

        //Find TabHosts in main layout
        parentTabs = (TabHost) findViewById(R.id.parentTabs);
        parentTabs.setup();
        childTabs = (TabHost) findViewById(R.id.childTabs);
        childTabs.setup();

        //create new TabManager
        tabManager = new TabManager(parentTabs,childTabs);

        //display initial tab setup
        tabManager.setParentTabs();
        tabManager.setChildTabsUser();

        final LayoutInflater inflater = LayoutInflater.from(this);

        //show my habits on startup
        ((LinearLayout) findViewById(R.id.subTab1)).removeView(inputViewTab1);
        inputViewTab1 = inflater.inflate(R.layout.todays_habits, null);
        ((LinearLayout) findViewById(R.id.subTab1)).addView(inputViewTab1);

        //listen for clicks on the parent tabs
        parentTabs.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String s) {
                if(parentTabs.getCurrentTab() == 0){
                    childTabs.setCurrentTab(1);
                    childTabs.getTabWidget().removeAllViews();
                    tabManager.setChildTabsUser();
                }else if(parentTabs.getCurrentTab() == 1){
                    childTabs.setCurrentTab(1);
                    childTabs.getTabWidget().removeAllViews();
                    tabManager.setChildTabsOther();
                }
            }
        });

        //listen for clicks on the child tabs
        childTabs.setOnTabChangedListener(new TabHost.OnTabChangeListener(){
            @Override
            public void onTabChanged(String s) {
                if(childTabs.getCurrentTab() == 0){
                    if(tabManager.getUserTabsActive()){
                        //TODO: Today's habits
                        ((LinearLayout) findViewById(R.id.subTab1)).removeView(inputViewTab1);
                        inputViewTab1 = inflater.inflate(R.layout.todays_habits, null);
                        ((LinearLayout) findViewById(R.id.subTab1)).addView(inputViewTab1);
                    }else{
                        //TODO: Other's activity
                        ((LinearLayout) findViewById(R.id.subTab1)).removeView(inputViewTab1);
                        inputViewTab1 = inflater.inflate(R.layout.others_activity, null);
                        ((LinearLayout) findViewById(R.id.subTab1)).addView(inputViewTab1);
                    }
                }else if(childTabs.getCurrentTab() == 1){
                    if(tabManager.getUserTabsActive()){
                        //TODO: My History
                        ((LinearLayout) findViewById(R.id.subTab2)).removeView(inputViewTab2);
                        inputViewTab2 = inflater.inflate(R.layout.my_history, null);
                        ((LinearLayout) findViewById(R.id.subTab2)).addView(inputViewTab2);
                    }else{
                        //TODO: Manage Activity Sharing
                        ((LinearLayout) findViewById(R.id.subTab2)).removeView(inputViewTab2);
                        inputViewTab2 = inflater.inflate(R.layout.manage_sharing, null);
                        ((LinearLayout) findViewById(R.id.subTab2)).addView(inputViewTab2);
                    }
                }else if(childTabs.getCurrentTab() == 2){
                    if(tabManager.getUserTabsActive()) {
                        //TODO: Manage Habits
                        ((LinearLayout) findViewById(R.id.subTab3)).removeView(inputViewTab3);
                        inputViewTab3 = inflater.inflate(R.layout.manage_habits, null);
                        ((LinearLayout) findViewById(R.id.subTab3)).addView(inputViewTab3);
                    }
                }
            }
        });
    }
}
