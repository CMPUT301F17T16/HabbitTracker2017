package com.example.habittracker2017;

import android.os.AsyncTask;
import android.util.Log;

import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;
import com.searchly.jestdroid.JestDroidClient;

import java.util.ArrayList;
import java.util.List;

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

    public static class loadUsers extends AsyncTask<String, Void, ArrayList<User>> {

        /**
         * This method takes a list of usernames and return the user objects associated with each of
         * these names, as long as those users are being followed by the user who runs this search.
         * @param names A list of users to fetch.
         * @return A list of users found.
         */
        @Override
        protected ArrayList<User> doInBackground(String... names) {
            verifySettings();
            ArrayList<User> results = new ArrayList<>();

            for(String name: names){
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
