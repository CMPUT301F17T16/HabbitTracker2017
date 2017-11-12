package com.example.habittracker2017;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class createHabit extends AppCompatActivity {
    private static final String FILENAME = "file.sav";

    private EditText habitName;
    private EditText habitStart;
    private EditText reason;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_habit);

        habitName = (EditText) findViewById(R.id.habitName);
        habitStart = (EditText) findViewById(R.id.habitStart);
        reason = (EditText) findViewById(R.id.reason);
        Button button = (Button) findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                setResult(RESULT_OK);
                String habitTitleName = habitName.getText().toString();
                String habitStartDate = habitStart.getText().toString();
                String habitReason = reason.getText().toString();
                
            }
        });

    }
}
