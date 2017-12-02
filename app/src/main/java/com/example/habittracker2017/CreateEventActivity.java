package com.example.habittracker2017;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Date;

import static com.example.habittracker2017.UserManager.user;

/**
 * Created by hyuan2 on 2017-11-12.
 */

public class CreateEventActivity extends AppCompatActivity {
    private TextView title;
    private EditText comment;
    private TextView date;
    private Button CreateButton;
    private Button LocationButton;
    private TextView AddressLine;
    private Button PhotoButton;
    private int position;
    private Habit habit;
    private HabitEvent habitevent;
    private Location currentLocation = new Location("gps");
    private ImageView IMG;
    private Bitmap photo;
    private final static int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private final static int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 2;
    private final static int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 3;
    public static final int REQUEST_CODE = 1;
    private static final int ADD_NEW_LOCATION_CODE = 2;

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
        AddressLine = (TextView) findViewById(R.id.address);
        PhotoButton = (Button) findViewById(R.id.button_photo);
        IMG = (ImageView) findViewById(R.id.pic);
        date = (TextView) findViewById(R.id.Date);
        date.setText(new Date().toString());
        final Context context = this;
        habitevent = new HabitEvent(null);
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);

            }
        }

        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);

            }
        }

        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);

            }
        }

        LocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,AddLocationActivity.class);
                if (intent.resolveActivity(getPackageManager())!=null){
                    startActivityForResult(intent, ADD_NEW_LOCATION_CODE);
                }
            }
        });


        PhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO add Photo
                Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if(i.resolveActivity(getPackageManager())!= null)
                {
                    startActivityForResult(i, REQUEST_CODE);
                }
            }
        });

        CreateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO add event
                habitevent.setComment(comment.getText().toString());
                habitevent.setDate(new Date());
                habitevent.setHabit(habit.getTitle());
                if (IMG.getDrawable() != null) {
                    // (128 * 128) * 4 = 65536 bytes which is the maximum allowed
                    // limits every bitmap image to 65536 bytes.
                    if(photo != null) {
                        Bitmap convertedImage = getResizedBitmap(photo, 128, 128);
                        convertedImage.getByteCount();
                        Log.d("Test", "This is convertedImage byte count!" + convertedImage.getByteCount());

                        habitevent.setPicture(convertedImage);
                    }
                }

                habit.addEvent(habitevent);
                UserManager.save();
                finish();
            }
        });
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if(requestCode == REQUEST_CODE){
            if(resultCode == RESULT_OK){
                Bundle extras = data.getExtras();
                photo = (Bitmap) extras.get("data");
                // (128 * 128) * 4 = 65536 bytes which is the maximum allowed
                Bitmap convertedImage = getResizedBitmap(photo, 128, 128);
                convertedImage.getByteCount();
                Log.d("Test", "This is convertedImage byte count!" + convertedImage.getByteCount());
                IMG.setImageBitmap(convertedImage);
            }
        }

        if(requestCode == ADD_NEW_LOCATION_CODE){
            if (resultCode == RESULT_OK){
                Double latitude = data.getDoubleExtra("new_latitude",0);
                Double longtitude = data.getDoubleExtra("new_longitude",0);
                String address = data.getStringExtra("new_address");
                currentLocation.setLatitude(latitude);
                currentLocation.setLongitude(longtitude);
                AddressLine.setText(address);
                habitevent.setLocation(currentLocation);
            }
        }

    }

    @Override
    public void onStart(){
        super.onStart();
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            case MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
            case MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
    public Bitmap getResizedBitmap(Bitmap image, int maxWidth, int maxHeight) {

        int width = maxWidth;
        int height = maxHeight;

        return Bitmap.createScaledBitmap(image, width, height, true);
    }
}

