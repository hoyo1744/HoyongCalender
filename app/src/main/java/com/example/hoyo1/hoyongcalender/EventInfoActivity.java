package com.example.hoyo1.hoyongcalender;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import java.sql.Date;
import java.sql.Time;
import java.text.SimpleDateFormat;

public class EventInfoActivity extends AppCompatActivity {

    //선언
    Toolbar toolbar;
    DatePicker date;
    EditText content;
    String strDate;
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
                strContent=content.getText().toString();
                if(strContent.isEmpty()){

                    AlertDialog.Builder builder = new AlertDialog.Builder(EventInfoActivity.this);

                    //대화상자설정
                    builder.setTitle("안내");
                    builder.setMessage("내용을 입력해주세요.");
                    builder.setIcon(android.R.drawable.ic_dialog_alert);


                    //예 버튼 추가
                    builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {

                        }
                    });

                    AlertDialog dialog = builder.create();
                    dialog.show();
                    return false;
                }




                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                intent.putExtra("date",strDate);
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
        content=(EditText)findViewById(R.id.editTextEventContent);



        //액션바설정
        setSupportActionBar(toolbar);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayOptions(actionBar.DISPLAY_HOME_AS_UP);

        //각 피커 디폴트값으로 초기화
        SetDefaultDateAndTime();


        //데이트피커설정
        date.init(date.getYear(),date.getMonth(),date.getDayOfMonth(),new DatePicker.OnDateChangedListener(){
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                strDate=String.format("%d-%d-%d",dayOfMonth,monthOfYear+1,year);
            }
        });

    }


    public void SetDefaultDateAndTime(){
        long now = System.currentTimeMillis();
        Date dateTime=new Date(now);
        SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-YYYY");
        strDate=sdf.format(dateTime);
    }


}
