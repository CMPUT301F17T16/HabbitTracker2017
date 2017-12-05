/*
*manageSharing
*
* version 1.0
*
* Dec 3, 2017
*
*Copyright (c) 2017 Team 16 (Jonah Cowan, Alexander Mackenzie, Hao Yuan, Jacy Mark, Shu-Ting Lin), CMPUT301, University of Alberta - All Rights Reserved.
*You may use, distribute, or modify this code under terms and conditions of the Code of Student Behavior at University of Alberta.
*You can find a copy of the license in this project. Otherwise please contact contact@abc.ca.
*
*/

package com.example.habittracker2017;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import static com.example.habittracker2017.UserManager.user;

/**
 * Activities the viewHistory does, subclass of fragment
 *
 * @author team 16
 * @version 1.0
 * @see Fragment
 * @since 1.0
 */

public class manageSharing extends Fragment {

    private static RequestAdapter<String> adapter;
    private static FollowManagerAdapter adapter1,adapter2;

    private ArrayList<String> followers;
    private ArrayList<String> following;
    private ArrayList<String> requests;
    private String selection;

    private Button addRequest;
    private ListView requestList;
    private TextView followerList,followingList;
    //boolean allowRefresh = false;

    /**
     *
     * @param position
     * @return
     */
    public static manageSharing newInstance(int position) {
        manageSharing fragment = new manageSharing();
        Bundle args = new Bundle();
        args.putInt("position", position);
        fragment.setArguments(args);

        return fragment;
    }

    public manageSharing() {

    }

    /**
     * Required, but not to be used with fragment
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
           /* mPosition = getArguments().getInt("position");*/
        }

    }

    /**
     * Inflate manage_sharing view
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return view
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.manage_sharing, container, false);

        return view;
    }

    /**
     * Called when activity is created
     * Set instance variables
     * Set addRequest onClick
     * Get requests, followers, following of this user, then set adapter for view
     * @param savedInstanceState
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        addRequest = getView().findViewById(R.id.requestButton);
        requestList = getView().findViewById(R.id.requestList);
        followerList = getView().findViewById(R.id.followedList);
//        followingList = getView().findViewById(R.id.followingList);

        addRequest.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                openPopup();
            }
        });

        //Get requests to this user, and adds an adapter to them.
        RemoteClient.checkRequests task = new RemoteClient.checkRequests();
        task.execute();
        try {
            requests = task.get();
            adapter = new RequestAdapter<>(this.getContext(), R.layout.request_list_item, requests);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        requestList.setAdapter(adapter);

        try{followers = UserManager.user.getFollowers();
            following = UserManager.user.getFollowing();

//            adapter1 = new FollowManagerAdapter(followers,getActivity());
//            adapter2 = new FollowManagerAdapter(following,getActivity());
//
//            followerList.setAdapter(adapter1);
//            followingList.setAdapter(adapter2);

        if (followers.size()>0) {
            followerList.setText("");
            for (String otherUser : followers) {
                followerList.append(otherUser + "\n");
            }
            followerList.append("----------------------------------------------------------------");
        }
//        followingList.setText("");
//        for (String otherUser: following){
//            followingList.append(otherUser+"\n");
//        }
        }
        catch ( Exception e){e.printStackTrace();}
    }

    /**
     * On activity resume, update follower, following, request list to view
     */
    @Override
    public void onResume() {
        super.onResume();
        followers = UserManager.user.getFollowers();
        following = UserManager.user.getFollowing();
        if (followers.size()>0) {
            followerList.setText("");
            for (String otherUser : followers) {
                followerList.append(otherUser + "\n");
            }
            followerList.append("----------------------------------------------------------------");
        }
//        followingList.setText("");
//        for (String otherUser: following){
//            followingList.append(otherUser+"\n");
//        }
//        adapter1.notifyDataSetChanged();
//        adapter2.notifyDataSetChanged();
        RemoteClient.checkRequests task = new RemoteClient.checkRequests();
        task.execute();
        try {
            requests.clear();
            requests.addAll(task.get());
            adapter.notifyDataSetChanged();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser){
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser){
            followers = UserManager.user.getFollowers();
            following = UserManager.user.getFollowing();
            if (followers.size()>0) {
                followerList.setText("");
                for (String otherUser : followers) {
                    followerList.append(otherUser + "\n");
                }
                followerList.append("----------------------------------------------------------------");
            }
//            followingList.setText("");
//            for (String otherUser: following){
//                followingList.append(otherUser+"\n");
//            }
//        adapter1.notifyDataSetChanged();
//        adapter2.notifyDataSetChanged();
            RemoteClient.checkRequests task = new RemoteClient.checkRequests();
            task.execute();
            try {
                requests = task.get();
                adapter.notifyDataSetChanged();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     *Inflate pop up window view, set pop up window button onClick
     * Input user name to be searched and click Search to show matching users
     * Click on user to be added then click OK to add to your following list
     * Click Cancel to cancel request (or click outside the pop up window)
     */
    private void openPopup(){
        LayoutInflater layoutInflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = layoutInflater.inflate(R.layout.activity_user_search_popup, null);

        final PopupWindow searchPopUp = new PopupWindow(this.getContext());
        searchPopUp.setContentView(layout);
        searchPopUp.setWidth(LinearLayout.LayoutParams.WRAP_CONTENT);
        searchPopUp.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        searchPopUp.setFocusable(true);
        searchPopUp.setBackgroundDrawable(new BitmapDrawable());
        searchPopUp.showAtLocation(layout, Gravity.CENTER, 0, 0);

        final EditText input = layout.findViewById(R.id.requestName);
        final ListView list = layout.findViewById(R.id.requestResults);
        final Button search = layout.findViewById(R.id.requestSearch);
        final Button accept = layout.findViewById(R.id.requestAccept);
        final Button cancel = layout.findViewById(R.id.requestCancel);
        final ArrayList<String> usernames = new ArrayList<>();
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this.getContext(), android.R.layout.simple_list_item_1, usernames);

        accept.setEnabled(false);
        list.setAdapter(adapter);

        search.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                RemoteClient.searchUsername task = new RemoteClient.searchUsername();
                task.execute(input.getText().toString());
                try {
                    usernames.clear();
                    usernames.addAll(task.get());
                    adapter.notifyDataSetChanged();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        });

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView parent, View v, int position, long id){
                accept.setEnabled(true);
                selection = ((TextView) v).getText().toString();
            }
        });

        accept.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                UserManager.user.addRequest(selection);
                UserManager.save();
                searchPopUp.dismiss();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                searchPopUp.dismiss();
            }
        });
    }
}
