package com.example.finalproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;

public class ItemSQLiteHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "Item.sqlite";
    private static final int DB_VERSION = 1;
    Cursor cursor;
    Context context;
    public ItemSQLiteHelper(Context context){
        super(context,DB_NAME,null,DB_VERSION);
        this.context = context;
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String create = "CREATE TABLE Item(_id INTEGER PRIMARY KEY AUTOINCREMENT, name STRING, description STRING,date STRING, img STRING, location INTEGER)";
        sqLiteDatabase.execSQL(create);
    }
    void insert(String name,String desc,String date, String img,int location) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name",name);
        contentValues.put("description",desc);
        contentValues.put("date",date);
        contentValues.put("img",String.valueOf(img));
        contentValues.put("location",location);
        sqLiteDatabase.insert("Item",null,contentValues);
    }

    int getCount(){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM Item",null);
        return cursor.getCount();
    }

    public Bitmap getBit(int x){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        cursor = sqLiteDatabase.query("Item", new String[]{"name","description","date", "img","location"},
                null,
                null,
                null,
                null,
                null);
        if(cursor.moveToPosition(x)){
            File file = context.getFileStreamPath(cursor.getString(3));
            if(file.exists()){
                Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                return bitmap;
            }
        }
        return null;
    }
    public Item get(int x){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        cursor = sqLiteDatabase.query("Item", new String[]{"name","description","date", "img","location"},
                null,
                null,
                null,
                null,
                null);
        if(cursor.moveToPosition(x)){
            File file = context.getFileStreamPath(cursor.getString(3));
            if(file.exists()) {
                Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                Item item = new Item(cursor.getString(0),cursor.getString(1), cursor.getString(2),bitmap, cursor.getInt(4));
                return item;

            }
        }
        return null;
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
