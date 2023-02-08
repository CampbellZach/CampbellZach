package com.example.hw13;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.HandlerThread;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class MyHandlerThread extends HandlerThread {
    private Context context;
    private Handler mainHandler;
    NasaSQLiteHelper nasaSQLiteHelper;
    String date;

    public MyHandlerThread(Context context, Handler mainHandler, String date) {
        super("MyHandlerThread");
        this.context = context;
        this.mainHandler = mainHandler;
        this.nasaSQLiteHelper = new NasaSQLiteHelper(context);
        this.date = date;
    }

    @Override
    public void run() {
        try {
            String adder = String.format("&date=%s",date);
            URL url = new URL("https://api.nasa.gov/planetary/apod?api_key=XNvwae8S5mBp5054n2ihYAZpH1QbTPYN0Qhq8YbK"+adder);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(url.openStream()));
            String line = bufferedReader.readLine();
            JSONObject jsonObject = new JSONObject(line);
            String img = jsonObject.getString("url");
            String date =jsonObject.getString("date");

            URL imgURL = new URL(img);
            InputStream inputStream = imgURL.openStream();
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            inputStream.close();
            FileOutputStream fileOutputStream = context.openFileOutput(date+".png",Context.MODE_PRIVATE);
            bitmap.compress(Bitmap.CompressFormat.PNG,100,fileOutputStream);
            nasaSQLiteHelper.insert(date,date +".png");
            fileOutputStream.close();

            mainHandler.sendEmptyMessage(0);


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
