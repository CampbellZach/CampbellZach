package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class AddNewDetailActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    private EditText name;
    private EditText desc;
    private TextView picString;
    private TextView date;
    Bitmap bitmap;
    ItemSQLiteHelper itemSQLiteHelper;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    LocationManager locationManager;

    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_detail);
        itemSQLiteHelper = new ItemSQLiteHelper(getApplicationContext());
        itemSQLiteHelper.getReadableDatabase();


        name = (EditText) findViewById(R.id.artistName);
        desc = (EditText) findViewById(R.id.paraView);
        date = (TextView) findViewById(R.id.dateSelected);
        imageView = (ImageView) findViewById(R.id.imagePreview);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);


    }
    public void dateSetter(View view){
        DateFragment dateFragment = new DateFragment(this);
        dateFragment.show(getSupportFragmentManager(),"DATE");
    }

    public void picture(View view) {
        //no fucking idea on how to implement this
        //startLocationService();
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);




    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            // Get the image from the camera
            Bundle extras = data.getExtras();
            bitmap = (Bitmap) extras.get("data");

            // Display the image in the ImageView
            imageView.setImageBitmap(bitmap);

            // Save the image to the device's storage
            MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, "Image", "Image taken with camera");
        }
    }

    public void startLocationService() {
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            // Request location updates from the location service
            //locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0);
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Location Service Not Enabled")
                    .setMessage("Please enable the location service to use this feature.")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Open the location settings page when the user clicks "OK"
                            Intent locationSettingsIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            startActivity(locationSettingsIntent);
                        }
                    });
        }
    }


    public void addTodb(View view){
        if(name.getText().toString().isEmpty() || desc.getText().toString().isEmpty() || date.getText().toString().isEmpty()|| imageView.getDrawable() == null ){
            AlertDialog.Builder builder = new AlertDialog.Builder(AddNewDetailActivity.this);
            builder.setTitle("You left fields blank. Please enter fill out everything!");

            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent intent = new Intent(getApplicationContext(),AddNewDetailActivity.class);
                    startActivity(intent);
                }
            });
            builder.show();

        }
        else {
            String namedb = String.valueOf(name.getText());
            String descdb = String.valueOf(desc.getText());
            String pic = date.getText() + ".png";
            String datedb = String.valueOf(date.getText());
            FileOutputStream fileOutputStream = null;
            try {
                fileOutputStream = openFileOutput(pic, Context.MODE_APPEND);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);

            itemSQLiteHelper.insert(namedb, descdb, datedb, pic, 1);

            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        }

    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        String dateset = String.format("%d-%d-%d",i,i1,i2);
        date.setText(dateset);
    }
}