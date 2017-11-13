package com.example.habittracker2017;

import android.content.Loader;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

public class CreateEventActivity extends AppCompatActivity {
    private TextView title;
    private EditText comment;
    private Button CreateButton;
    private Button LocationButton;
    private Button PhotoButton;
    private CheckBox finished;
    private int position;
    private Habit habit;
    private HabitEvent habitevent;
    private Location currentLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);
        position = this.getIntent().getIntExtra("Habit",0);
        habit = viewTodayFragment.allHabits.get(position);
        title = (TextView) findViewById(R.id.habitName);
        title.setText(habit.getTitle());
        comment = (EditText) findViewById(R.id.comment);
        CreateButton = (Button) findViewById(R.id.button_create);
        LocationButton = (Button) findViewById(R.id.button_location);
        PhotoButton = (Button) findViewById(R.id.button_photo);
        finished = (CheckBox) findViewById(R.id.finish);

        LocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO add location
            }
        });

        PhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO add Photo
            }
        });

        CreateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO add event
            }
        });
    }
}
