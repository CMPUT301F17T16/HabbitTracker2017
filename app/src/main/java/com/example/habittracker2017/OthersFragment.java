package com.example.habittracker2017;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by hyuan2 on 2017-11-12.
 */

public class OthersFragment extends Fragment {
    public static OthersFragment newInstance(int position) {
        OthersFragment fragment = new OthersFragment();
        Bundle args = new Bundle();
        args.putInt("position", position);
        fragment.setArguments(args);

        return fragment;
    }

    public OthersFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
           /* mPosition = getArguments().getInt("position");*/
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.others_activity, container, false);

/*        User currentUser = UserManager.user;
        if(currentUser == null){
            return view;
        }
        ArrayList<String> followedUserNames = currentUser.getFollowing();

        //Load in followed users
        RemoteClient.loadUsers task = new RemoteClient.loadUsers();
        task.execute(followedUserNames);
        ArrayList<User> followedUsers = new ArrayList<>();
        try{
            followedUsers = task.get();
        }catch(Exception e){
            Log.i("Error", "Something went wrong when we tried to communicate with the elasticsearch server!");
        }

        ListView othersActivities = (ListView) view.findViewById(R.id.followedUserListView);
        OthersEventsListAdapter adapter = new OthersEventsListAdapter(getContext(), followedUsers);
        othersActivities.setAdapter(adapter);*/


        return view;
    }

}
