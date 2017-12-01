package com.example.habittracker2017;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Alex on 2017-12-01.
 * Adapts a list of strings indicating users who have requested access to the user's history to
 * a list with button to accept these requests.
 */

public class RequestAdapter<T> extends ArrayAdapter<String> {
    private static final String PRESSTEXT = "Done";             //The text a button displays after it has been pressed

    public RequestAdapter(Context context, int element, ArrayList<String> list){
        super(context, element, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //The following seven lines are taken from StackExchange
        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.request_list_item, null);
        }

        final String name = getItem(position);

        if (name != null) {
            //Finds all needed UI elements
            TextView nameText = (TextView) v.findViewById(R.id.requestName);
            final Button acceptButton = (Button) v.findViewById(R.id.requestAccept);

            //Displays username
            nameText.setText(name);

            //Adds listener so the button can function
            acceptButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    UserManager.user.addFollower(name);
                    UserManager.save();
                    acceptButton.setEnabled(false);
                    acceptButton.setText(PRESSTEXT);
                }
            });
        }
        return v;
    }
}
