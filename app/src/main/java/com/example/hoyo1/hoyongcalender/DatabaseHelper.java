package com.example.hoyo1.hoyongcalender;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {


    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
     try{
         String DROP_SQL="drop table if exists calender";
         db.execSQL(DROP_SQL);
     }catch(Exception ex){

     }
     String CREATE_SQL="create table calender"+"("
             +" id integer NOT NULL PRIMARY KEY autoincrement,"
             +" date text,"
             +" startTime text,"
             +" endTime text,"
             +" content text)";

     try{
         db.execSQL(CREATE_SQL);
     }catch(Exception ex){

     }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String UPDATE_SQL="drop table if exists calender";
        db.execSQL(UPDATE_SQL);
        onCreate(db);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }
}
