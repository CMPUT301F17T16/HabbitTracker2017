package com.example.habittracker2017;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import com.google.gson.Gson;

/**
 * Created by Alex on 2017-11-10.
 * This a singleton responsible for loading, saving, creating, and holding the current user's User object.
 * All habits and events for the current user can be accessed through it through UserManager.user.
 */

public class UserManager {
    private static UserManager instance;
    public static User user;
    private static Context context;
    private static final String FILENAME = "user.sav";
    private static boolean pendingSave;
    private BroadcastReceiver networkStateReceiver;

    /**
     * Loads the current user's data into this object if it exists, otherwise flags user as null.
     * @param context The context used to load and save data.
     */
    private UserManager(Context context){
        this.context = context;
        pendingSave = false;

        //This implementation of a BroadcastReceiver is taken from Stackoverflow user Xmister,
        // and was originally posted on Jul 12, 2016. All credit for this receiver ges to him.
        networkStateReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                checkPendingSave();
            }
        };
        context.registerReceiver(networkStateReceiver, new IntentFilter(android.net.ConnectivityManager.CONNECTIVITY_ACTION));

        try {
            FileInputStream fis = context.openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));
            Gson gson = new Gson();

            user = gson.fromJson(in, User.class);
        } catch (FileNotFoundException e) {
            user = null;
        }
    }

    /**
     * Creates this singleton if it does not already exist.
     * @param context The context used to load and save data.
     */
    public static void init(Context context){
        if(instance == null){
            instance = new UserManager(context);
        }
    }

    /**
     * Creates and saves a new user for this device, assuming one does not already exist.
     * @param name The username of the new user object.
     * @throws Exception On trying to create a user when one already exists.
     */
    public static void createUser(String name) throws Exception {
        if(user != null){throw new Exception();}
        user = new User(name);
        save();
    }


    /**
     * Saves the user's current data to file and to the remote database
     */
    public static void save(){
        try {
            FileOutputStream fos = context.openFileOutput(FILENAME,
                    Context.MODE_PRIVATE);
            OutputStreamWriter writer = new OutputStreamWriter(fos);
            Gson gson = new Gson();
            gson.toJson(user, writer);
            writer.flush();

            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(InternetStatus.CheckInternetConnection(context)){
            new RemoteClient.saveUser().execute(user);
        } else {
            pendingSave = true;
        }
    }

    private void checkPendingSave(){
        if(pendingSave && InternetStatus.CheckInternetConnection(context)){
            new RemoteClient.saveUser().execute(user);
            pendingSave = false;
        }
    }

    public static void load(){
        if(true){

        }
    }
}
