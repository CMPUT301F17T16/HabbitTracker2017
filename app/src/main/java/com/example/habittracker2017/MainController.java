package com.example.habittracker2017;

import android.content.Context;
import android.location.Location;

/**
 * Created by 82520 on 2017-11-22.
 */

public class MainController {
    MainModel mm = null;
    public MainController(MainModel mm){this.mm = mm;}
    public void startLocationListen(Context context){
        mm.startLocationListen(context);
    }

    public void stopLocationListener(){
        mm.stopLocationListener();
    }

    public Location getLocation(){
        return mm.getLocation();
    }

}
