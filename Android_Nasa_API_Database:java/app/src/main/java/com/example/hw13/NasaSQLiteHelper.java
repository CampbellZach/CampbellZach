package com.example.hw13;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;

public class NasaSQLiteHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "nasa.sqlite";
    private static final int DB_VERSION = 1;
    Cursor cursor;
    Context context;

    public NasaSQLiteHelper(Context context){
        super(context,DB_NAME,null,DB_VERSION);
        this.context = context;
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String create = "CREATE TABLE nasa(_id INTEGER PRIMARY KEY AUTOINCREMENT, date STRING, img STRING)";
        sqLiteDatabase.execSQL(create);

    }
    void insert(String date, String img) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("date",date);
        contentValues.put("img",String.valueOf(img));
        sqLiteDatabase.insert("nasa",null,contentValues);
    }
    int getCount(){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM nasa",null);
        return cursor.getCount();
    }

    public Bitmap getBit(int x){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        cursor = sqLiteDatabase.query("nasa", new String[]{"date", "img"},
                null,
                null,
                null,
                null,
                null);
        if(cursor.moveToPosition(x)){
            File file = context.getFileStreamPath(cursor.getString(1));
            if(file.exists()){
                Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                return bitmap;
            }
        }
        return null;
    }
    public Nasa get(int x){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        cursor = sqLiteDatabase.query("nasa", new String[]{"date", "img"},
                null,
                null,
                null,
                null,
                null);
        if(cursor.moveToPosition(x)){
            File file = context.getFileStreamPath(cursor.getString(1));
            if(file.exists()) {
                Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                Nasa nasa = new Nasa(cursor.getString(0),bitmap);
                return nasa;

            }
            }
        return null;
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
