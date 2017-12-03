package com.example.habittracker2017;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import static com.example.habittracker2017.UserManager.user;

/**
 * Created by hyuan2 on 2017-11-12.
 */

public class manageSharing extends Fragment {

    private static RequestAdapter<String> adapter;
    private static FollowManagerAdapter adapter1,adapter2;

    private Button addRequest;
    private EditText nameText;
    private ListView requestList,followerList,followingList;
    //boolean allowRefresh = false;


    public static manageSharing newInstance(int position) {
        manageSharing fragment = new manageSharing();
        Bundle args = new Bundle();
        args.putInt("position", position);
        fragment.setArguments(args);

        return fragment;
    }

    public manageSharing() {

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
        View view = inflater.inflate(R.layout.manage_sharing, container, false);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        addRequest = getView().findViewById(R.id.requestButton);
        nameText = getView().findViewById(R.id.requestInput);
        requestList = getView().findViewById(R.id.requestList);
        followerList = getView().findViewById(R.id.followedList);
        followingList = getView().findViewById(R.id.followingList);

        addRequest.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                //allowRefresh = true;
                String username = nameText.getText().toString();
                nameText.setText("");
                if(!username.equals("")){
                    UserManager.user.addFollowing(username);
                    UserManager.save();
                }
            }
        });

        //Get requests to this user, and adds an adapter to them.
        RemoteClient.checkRequests task = new RemoteClient.checkRequests();
        task.execute();
        try {
            adapter = new RequestAdapter<>(this.getContext(), R.layout.request_list_item, task.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        requestList.setAdapter(adapter);

        try{ArrayList<String> followers = UserManager.user.getFollowers();
        ArrayList<String> following = UserManager.user.getFollowing();

        adapter1 = new FollowManagerAdapter(followers,getActivity());
        adapter2 = new FollowManagerAdapter(following,getActivity());

        followerList.setAdapter(adapter1);
        followingList.setAdapter(adapter2);}
        catch ( Exception e){e.printStackTrace();}
    }

 /*   public void onResume() {
        super.onResume();
        if(allowRefresh) {
            allowRefresh = false;
            getFragmentManager().beginTransaction().detach(this).attach(this).commit();
        }
    }*/
}
