package com.example.finalproject;

import android.graphics.Bitmap;

public class Item {
    public String name;
    public String desc;
    public String date;
    public Bitmap img;
    public int location;

    public Item(String name, String date, String desc , Bitmap img, int location){
        this.name = name;
        this.date = date;
        this.desc = desc;
        this.img = img;
        this.location = location;
    }



}
