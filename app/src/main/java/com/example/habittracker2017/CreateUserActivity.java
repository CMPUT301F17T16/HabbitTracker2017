package com.example.habittracker2017;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CreateUserActivity extends AppCompatActivity {
    private EditText nameText;
    private Button doneButton;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);

        nameText = (EditText) findViewById(R.id.newUsername);
        doneButton = (Button) findViewById(R.id.acceptUsername);
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
                    //TODO Show some notification here when checkIfNameFree works
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
       return true;         //TODO Implement this in Part 5
    }
}
