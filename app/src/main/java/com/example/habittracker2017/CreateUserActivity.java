/*
*CreateUserActivity
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
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.concurrent.ExecutionException;

/**
 * Activity for creating user
 *
 * @author team 16
 * @version 1.0
 * @see AppCompatActivity
 * @since 1.0
 */
public class CreateUserActivity extends AppCompatActivity {
    private EditText nameText;
    private Button doneButton;
    private TextView errorMessage;
    private Context context;

    /**
     * Called when activity created
     * Set instance variables
     * Set button onClickListener
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);

        nameText = (EditText) findViewById(R.id.newUsername);
        doneButton = (Button) findViewById(R.id.acceptUsername);
        errorMessage = (TextView) findViewById(R.id.errorMessage);

        context = this;

        doneButton.setOnClickListener(new View.OnClickListener() {
            //Creates new user, then returns to main view.
            public void onClick(View v) {
                String newName = nameText.getText().toString();
                if(checkIfNameFree(newName)){
                    try {
                        UserManager.createUser(newName);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Intent intent = new Intent(context, HabitTracker2017MainActivity.class);
                    startActivity(intent);
                } else {
                    errorMessage.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    /**
     * Checks the availability of a given username.
     * @param name the username to check
     * @return True if the name is available, false otherwise.
     */
    private boolean checkIfNameFree(String name){
        RemoteClient.checkExists test = new RemoteClient.checkExists();
        test.execute(name);
        try {
            return !test.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return false;
    }
}
