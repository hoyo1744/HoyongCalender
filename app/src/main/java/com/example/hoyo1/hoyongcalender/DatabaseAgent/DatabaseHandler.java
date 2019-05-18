package com.example.hoyo1.hoyongcalender.DatabaseAgent;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.hoyo1.hoyongcalender.MainActivity;

public class DatabaseHandler {

    DatabaseHelper mHelper;
    SQLiteDatabase mDB;

    public DatabaseHandler(Context context,String name){
        mHelper=new DatabaseHelper(context,name,null,1);
    }


    public static DatabaseHandler Open(Context context,String name){
        return new DatabaseHandler(context,name);
    }


    public Cursor Select(){
        try {
            mDB = mHelper.getReadableDatabase();
            Cursor c = mDB.query("calender", null, null, null, null, null, null);
            return c;
        }catch(Exception e){
            ((MainActivity)MainActivity.mainContext).ShowErrorMessage("Database Select Error");
            return null;
        }
    }

    public void Insert(String date,String content){
        try {
            mDB = mHelper.getWritableDatabase();
            ContentValues cInfo = new ContentValues();
            cInfo.put("date", date);
            cInfo.put("content", content);
            mDB.insert("calender", null, cInfo);
        }catch(Exception e){
            ((MainActivity)MainActivity.mainContext).ShowErrorMessage("Database Insert Error");
        }
    }

    public void close() {
        mHelper.close();
    }


}
