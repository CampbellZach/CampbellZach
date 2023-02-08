package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class ClickDetailActivity extends AppCompatActivity {
    TextView nameView;
    TextView dateView;
    ImageView imageView;
    TextView descView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_click_detail);

        ItemSQLiteHelper itemSQLiteHelper = new ItemSQLiteHelper(getApplicationContext());

        nameView = findViewById(R.id.finalNameView);
        dateView = findViewById(R.id.finalDateView);
        imageView = findViewById(R.id.finalImageView);
        descView = findViewById(R.id.finalDescView);

        int pos = getIntent().getIntExtra("pos",-1);

        Item item = itemSQLiteHelper.get(pos);
        imageView.setImageBitmap(item.img);
        dateView.setText(item.date);
        nameView.setText(item.name);
        descView.setText(item.desc);

    }
    public void goBack(View view){
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

}