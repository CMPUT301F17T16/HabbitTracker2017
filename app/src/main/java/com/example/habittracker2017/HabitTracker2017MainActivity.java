package com.example.habittracker2017;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TabHost;

public class HabitTracker2017MainActivity extends AppCompatActivity {

    boolean connection = false;
    TabHost parentTabs;
    TabHost childTabs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit_tracker2017);

        connection = InternetStatus.CheckInternetConnection(HabitTracker2017MainActivity.this);


        parentTabs = (TabHost) findViewById(R.id.parentTabs);
        parentTabs.setup();


        childTabs = (TabHost) findViewById(R.id.childTabs);
        childTabs.setup();

        setParentTabs(parentTabs);
        setChildTabsUser(childTabs);

        parentTabs.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String s) {
                if(parentTabs.getCurrentTab() == 0){
                    childTabs.getTabWidget().removeAllViews();
                    setChildTabsUser(childTabs);
                }else if(parentTabs.getCurrentTab() == 1){
                    childTabs.getTabWidget().removeAllViews();
                    setChildTabsOther(childTabs);
                }
            }
        });
    }

    private void setChildTabsUser(TabHost child){
        TabHost.TabSpec spec;

        spec = child.newTabSpec("TodaysHabits");
        spec.setContent(R.id.subTab1);
        spec.setIndicator("Today's Habits");
        child.addTab(spec);

        spec = child.newTabSpec("MyHistory");
        spec.setContent(R.id.subTab2);
        spec.setIndicator("My History");
        child.addTab(spec);

        spec = child.newTabSpec("ManageHabits");
        spec.setContent(R.id.subTab3);
        spec.setIndicator("Manage Habits");
        child.addTab(spec);

        child.setCurrentTab(0);
    }

    private void setChildTabsOther(TabHost child){
        TabHost.TabSpec spec;

        spec = child.newTabSpec("OthersActivity");
        spec.setContent(R.id.subTab1);
        spec.setIndicator("Today's Habits");
        child.addTab(spec);

        spec = child.newTabSpec("ManageSharing");
        spec.setContent(R.id.subTab2);
        spec.setIndicator("Manage Activity Sharing");
        child.addTab(spec);

        child.setCurrentTab(0);
    }

    private void setParentTabs(TabHost parent){
        TabHost.TabSpec spec;

        spec = parent.newTabSpec("UserTab");
        spec.setContent(R.id.tab1);
        spec.setIndicator("My Habits");
        parent.addTab(spec);

        spec = parent.newTabSpec("OtherTab");
        spec.setContent(R.id.tab2);
        spec.setIndicator("Other's Habits");
        parent.addTab(spec);

        parent.setCurrentTab(0);
    }
}
