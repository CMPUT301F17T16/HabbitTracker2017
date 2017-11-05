package com.example.habittracker2017;

import android.os.Bundle;
import android.widget.TabHost;

/**
 * Created by Jonah Cowan on 2017-11-04.
 */

public class TabManager {
    private TabHost parentTabs;
    private TabHost childTabs;
    private boolean userTabsActive = true;


    public TabManager(TabHost parentTabs, TabHost childTabs){
        this.parentTabs = parentTabs;
        this.childTabs = childTabs;
    }

    public void setChildTabsUser(){
        TabHost.TabSpec spec;

        spec = childTabs.newTabSpec("TodaysHabits");
        spec.setContent(R.id.subTab1);
        spec.setIndicator("Today's Habits");
        childTabs.addTab(spec);

        spec = childTabs.newTabSpec("MyHistory");
        spec.setContent(R.id.subTab2);
        spec.setIndicator("My History");
        childTabs.addTab(spec);

        spec = childTabs.newTabSpec("ManageHabits");
        spec.setContent(R.id.subTab3);
        spec.setIndicator("Manage Habits");
        childTabs.addTab(spec);

        userTabsActive = true;

        childTabs.setCurrentTab(0);
    }


    public void setChildTabsOther(){
        TabHost.TabSpec spec;

        spec = childTabs.newTabSpec("OthersActivity");
        spec.setContent(R.id.subTab1);
        spec.setIndicator("Other's Habits");
        childTabs.addTab(spec);

        spec = childTabs.newTabSpec("ManageSharing");
        spec.setContent(R.id.subTab2);
        spec.setIndicator("Manage Activity Sharing");
        childTabs.addTab(spec);

        userTabsActive = false;

        childTabs.setCurrentTab(0);
    }


    public void setParentTabs(){
        TabHost.TabSpec spec;

        spec = parentTabs.newTabSpec("UserTab");
        spec.setContent(R.id.tab1);
        spec.setIndicator("My Habits");
        parentTabs.addTab(spec);

        spec = parentTabs.newTabSpec("OtherTab");
        spec.setContent(R.id.tab2);
        spec.setIndicator("Other's Habits");
        parentTabs.addTab(spec);

        parentTabs.setCurrentTab(0);
    }

    public boolean getUserTabsActive(){
        return userTabsActive;
    }
}
