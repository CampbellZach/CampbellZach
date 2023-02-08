package com.example.hw13;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.DatePicker;

import java.io.File;

public class MainActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    RecyclerView recyclerView;
    NasaAdapter nasaAdapter;
    static NasaSQLiteHelper nasaSQLiteHelper;
     Handler mainHandler;
     MyHandlerThread myHandlerThread;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        nasaSQLiteHelper = new NasaSQLiteHelper(getApplicationContext());
        nasaAdapter = new NasaAdapter(nasaSQLiteHelper);
        recyclerView.setAdapter(nasaAdapter);

        mainHandler = new Handler(){
            @Override
            public void handleMessage(Message msg){
                if(msg.what == 0){
                    recyclerView.getAdapter().notifyDataSetChanged();
                }
            }
        };



    }
    public void DateClick(View view){
        DateFragment dateFragment = new DateFragment(this);
        dateFragment.show(getSupportFragmentManager(),"DATE");
    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
       String date = String.format("%d-%d-%d",i,i1,i2);
        myHandlerThread = new MyHandlerThread(getApplicationContext(),mainHandler,date);
        myHandlerThread.start();
    }

    @Override
    protected void onStart() {
        super.onStart();
        recyclerView.getAdapter().notifyDataSetChanged();
    }
}