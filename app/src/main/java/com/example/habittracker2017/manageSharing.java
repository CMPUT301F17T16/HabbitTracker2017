package com.example.habittracker2017;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.concurrent.ExecutionException;

/**
 * Created by hyuan2 on 2017-11-12.
 */

public class manageSharing extends Fragment {

    private static RequestAdapter<String> adapter;

    private Button addRequest;
    private EditText nameText;
    private ListView list;

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
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        addRequest = view.findViewById(R.id.requestButton);
        nameText = view.findViewById(R.id.requestInput);
        list = view.findViewById(R.id.requestList);

        addRequest.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                String username = nameText.getText().toString();
                nameText.setText("");
                if(!username.equals("")){
                    UserManager.user.addFollowing(username);
                    UserManager.save();
                }
            }
        });
    }

    @Override
    public void onStart(){
        super.onStart();
        //Get requests to this user, and adds adapter to them.
        RemoteClient.checkRequests task = new RemoteClient.checkRequests();
        task.execute();
        try {
            adapter = new RequestAdapter<>(this.getContext(), R.layout.request_list_item, task.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
