package com.example.hoyo1.hoyongcalender;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

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
        mDB=mHelper.getReadableDatabase();
        Cursor c=mDB.query("calender", null, null, null, null, null, null);
        return c;
    }

    public void Insert(String date,String start,String end,String content){
        mDB=mHelper.getWritableDatabase();
        ContentValues cInfo=new ContentValues();
        cInfo.put("date",date);
        cInfo.put("startTime",start);
        cInfo.put("endTime",end);
        cInfo.put("content",content);
        mDB.insert("calender",null,cInfo);
    }

    public void close() {
        mHelper.close();
    }


}
