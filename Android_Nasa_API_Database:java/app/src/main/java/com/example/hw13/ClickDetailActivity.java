package com.example.hw13;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class ClickDetailActivity extends AppCompatActivity {
    ImageView imgView;
    TextView dateView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_click_detail);

        NasaSQLiteHelper nasaSQLiteHelper = new NasaSQLiteHelper(getApplicationContext());

        imgView = findViewById(R.id.finalImageView);
        dateView = findViewById(R.id.finalTextView);



        int pos = getIntent().getIntExtra("pos",-1);

        Nasa nasa = nasaSQLiteHelper.get(pos);

        imgView.setImageBitmap(nasa.img);
        dateView.setText(nasa.date);
    }
}