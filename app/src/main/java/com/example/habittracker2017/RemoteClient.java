package com.example.habittracker2017;

import android.os.AsyncTask;
import android.util.Log;

import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;
import com.searchly.jestdroid.JestDroidClient;

import java.util.ArrayList;
import java.util.List;

import io.searchbox.core.Delete;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;

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

    public static class loadUsers extends AsyncTask<ArrayList<String>, Void, ArrayList<User>> {

        /**
         * This method takes a list of usernames and return the user objects associated with each of
         * these names, as long as those users are being followed by the user who runs this search.
         * @param names A list of users to fetch.
         * @return A list of users found.
         */
        @Override
        protected ArrayList<User> doInBackground(ArrayList<String>... names) {
            verifySettings();
            ArrayList<User> results = new ArrayList<>();

            for(String name: names[0]){
                String query = "{\"query\" : {" +
                    "\"bool\" : { \"filter\": [" +
                        "\"term\" : { \"name\" : \"" + name + "\" }" +
                        "\"term\" : { \"followers\" : \"" + UserManager.user.getName() + "\" }" +
                    "]}" +
                "}}";
                Search search = new Search.Builder(query).addIndex(INDEX).addType("user").build();

                try {
                    SearchResult result = client.execute(search);
                    if(result.isSucceeded()){
                        results.add(result.getSourceAsObject(User.class));
                    }
                }
                catch (Exception e) {
                    Log.i("Error", "Something went wrong when we tried to communicate with the elasticsearch server!");
                }
            }
            return results;
        }
    }

    public static class checkRequests extends AsyncTask<Void, Void, ArrayList<String>> {

        /**
         * This method checks to see which users have tagged themselves as following the local user,
         * and returns a list of users that are not actually following them.
         * @return A list of usernames of users that want to follow this user.
         */
        @Override
        protected ArrayList<String> doInBackground(Void... voids) {
            verifySettings();
            ArrayList<String> results = new ArrayList<>();

            if(UserManager.user == null){
                return results;
            }

            String query = "{\"query\" : {\"term\" : { \"following\" : \"" + UserManager.user.getName() + "\" }}}";
            Search search = new Search.Builder(query).addIndex(INDEX).addType("user").build();

            try {
                SearchResult result = client.execute(search);
                if(result.isSucceeded()){
                    List<User> users = result.getSourceAsObjectList(User.class);
                    for(User user: users){
                        results.add(user.getName());
                    }
                }
            }
            catch (Exception e) {
                Log.i("Error", "Something went wrong when we tried to communicate with the elasticsearch server!");
            }
            results.removeAll(UserManager.user.getFollowers());
            return results;
        }
    }

    public static class checkExists extends AsyncTask<String, Void, Boolean> {

        /**
         * This method takes a username, checks if it is currently being used, and returns true if it
         * is and false if it is not.
         * @param name The username to check.
         * @return The boolean evaluation of the username's existence.
         */
        @Override
        protected Boolean doInBackground(String... name) {
            verifySettings();

            String query = "{\"query\" : {\"term\" : { \"name\" : \"" + name[0] + "\" }}}";
            Search search = new Search.Builder(query).addIndex(INDEX).addType("user").build();
            try {
                SearchResult result = client.execute(search);
                if(result.isSucceeded()){
                    return (result.getTotal() != 0);
                }
            }
            catch (Exception e) {
                Log.i("Error", "Something went wrong when we tried to communicate with the elasticsearch server!");
            }
            return true;
        }
    }

    public static class deleteUser extends AsyncTask<Void, Void, Void> {

        /**
         * This method deletes the currently existing server object for the user.
         */
        @Override
        protected Void doInBackground(Void... voids) {
            verifySettings();

            Delete search = new Delete.Builder(UserManager.user.getName()).index(INDEX).type("user").build();

            try {
                client.execute(search);
            }
            catch (Exception e) {
                Log.i("Error", "Something went wrong when we tried to communicate with the elasticsearch server!");
            }
            return null;
        }
    }

    private static void verifySettings() {
        if (client == null) {
            DroidClientConfig.Builder builder = new DroidClientConfig.Builder(DATABASE);
            DroidClientConfig config = builder.build();

            JestClientFactory factory = new JestClientFactory();
            factory.setDroidClientConfig(config);
            client = (JestDroidClient) factory.getObject();
        }
    }
}
