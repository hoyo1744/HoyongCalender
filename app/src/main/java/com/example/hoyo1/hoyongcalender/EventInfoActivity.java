package com.example.hoyo1.hoyongcalender;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import java.sql.Time;

public class EventInfoActivity extends AppCompatActivity {

    //선언
    Toolbar toolbar;
    DatePicker date;
    TimePicker startTime;
    TimePicker endTime;
    EditText content;
    String strDate;
    String strStartTime;
    String strEndTime;
    String strContent;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_info);

        //초기화
        Init();



    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_add_event,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int menuItemID=item.getItemId();
        switch(menuItemID){
            case R.id.confirmMenu:



                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                intent.putExtra("date",strDate);
                intent.putExtra("start",strStartTime);
                intent.putExtra("end",strEndTime);
                strContent=content.getText().toString();
                intent.putExtra("content",strContent);
                setResult(RESULT_OK,intent);
                finish();

                break;
            case android.R.id.home:

                setResult(RESULT_CANCELED);
                finish();
                break;

        }


        return super.onOptionsItemSelected(item);

    }
    public void Init(){


        toolbar=(Toolbar)findViewById(R.id.toolbar);
        date=(DatePicker)findViewById(R.id.datePicker);
        startTime=(TimePicker)findViewById(R.id.startTime);
        endTime=(TimePicker)findViewById(R.id.endTime);
        content=(EditText)findViewById(R.id.editTextEventContent);



        //액션바설정
        setSupportActionBar(toolbar);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);


        //데이트피커설정
        date.init(date.getYear(),date.getMonth(),date.getDayOfMonth(),new DatePicker.OnDateChangedListener(){
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                strDate=String.format("%d-%d-%d",dayOfMonth,monthOfYear,year);
            }
        });

        //스타트타임피커설정
        startTime.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                strStartTime=String.format("%d:%d",hourOfDay,minute);
            }
        });

        //앤드타임피커설정
        endTime.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                strEndTime=String.format("%d:%d",hourOfDay,minute);
            }
        });






    }


}
