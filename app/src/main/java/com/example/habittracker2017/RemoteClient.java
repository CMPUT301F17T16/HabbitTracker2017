package com.example.habittracker2017;

import android.os.AsyncTask;
import android.util.Log;

import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;
import com.searchly.jestdroid.JestDroidClient;

import io.searchbox.core.Index;

/**
 * Created by Alex on 2017-11-10.
 * This class controls the remote saving and loading of user classes.
 */

class RemoteClient {
    private static JestDroidClient client;
    private static final String DATABASE = "http://cmput301.softwareprocess.es:8080/";
    private static final String INDEX = "cmput301f17t16_habittracker2017";

    public static class saveUser extends AsyncTask<User, Void, Void> {
        @Override
        protected Void doInBackground(User... user) {
            verifySettings();
            String id = user[0].getName();

            Index index = new Index.Builder(user[0]).index(INDEX).type("user").id(id).build();

            try {
                client.execute(index);
            }
            catch (Exception e) {
                Log.i("Error", "The user's data was not saved");
            }

            return null;
        }
    }

    public static class UpdateUsersHabitTask extends AsyncTask<User, Void, Void> {
        @Override
        protected Void doInBackground(User... users){
            verifySettings();

            for (User user : users){
                String query = "";
            }
            return null;
        }
    }

    public static void verifySettings() {
        if (client == null) {
            DroidClientConfig.Builder builder = new DroidClientConfig.Builder(DATABASE);
            DroidClientConfig config = builder.build();

            JestClientFactory factory = new JestClientFactory();
            factory.setDroidClientConfig(config);
            client = (JestDroidClient) factory.getObject();
        }
    }
}
