package com.example.habittracker2017;

import android.*;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
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
import android.widget.Switch;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
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
    private Switch LocationSwitch;
    private Button PhotoButton;
    private int position;
    private Habit habit;
    private HabitEvent habitevent;
    private Location currentLocation;
    private ImageView IMG;
    private Bitmap photo;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private final static int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private final static int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 2;
    private final static int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 3;
    public static final int REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);
        position = this.getIntent().getIntExtra("Habit",0);
        habit = user.getHabits().get(position);
        title = (TextView) findViewById(R.id.habitName);
        title.setText(habit.getTitle());
        comment = (EditText) findViewById(R.id.comment);
        CreateButton = (Button) findViewById(R.id.button_create);
        LocationSwitch = (Switch) findViewById(R.id.button_location);
        PhotoButton = (Button) findViewById(R.id.button_photo);
        IMG = (ImageView) findViewById(R.id.pic);
        date = (TextView) findViewById(R.id.Date);
        date.setText(new Date().toString());

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
                habitevent = new HabitEvent(comment.getText().toString(),null,null);
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
                if (LocationSwitch.isChecked()){
                    locationManager = (LocationManager) getApplicationContext().getSystemService(getApplicationContext().LOCATION_SERVICE);
                    locationListener = new LocationListener() {
                        public void onLocationChanged(Location location) {
                            // Called when a new location is found by the network location provider.
                            currentLocation = location;
                        }

                        public void onStatusChanged(String provider, int status, Bundle extras) {}

                        public void onProviderEnabled(String provider) {}

                        public void onProviderDisabled(String provider) {}
                    };

                    // Register the listener with the Location Manager to receive location updates
                    try {
                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
                    }
                    catch (SecurityException e){
                        Log.d("location", e.toString());
                    }
                    try {
                        currentLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                        locationManager.removeUpdates(locationListener);
                    }
                    catch(SecurityException e){
                        Log.d("Location error", e.toString());
                    }
                    habitevent.setLocation(currentLocation);
                }
                ////added 2 lines
                /*viewMyHistory.allEvents.add(habitevent);*/
                /*viewMyHistory.adapter.notifyDataSetChanged();*/
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
    public void saveToFile(){
        try{
            FileOutputStream fos = openFileOutput(viewManageHabits.FILENAME, Context.MODE_PRIVATE);
            OutputStreamWriter writer = new OutputStreamWriter(fos);
            Gson gson = new Gson();
            gson.toJson(viewMyHistory.allEvents, writer); ///changed
            writer.flush();
            fos.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

