package com.example.hoyo1.hoyongcalender;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    public class CalenderInfo{
        String strID;
        String strDate;
        String strStartTime;
        String strEndTime;
        String strContent;

        public void setID(String strParam){
            this.strID=strParam;
        }
        public void setDate(String strParam){
            this.strDate=strParam;
        }
        public void setStartTime(String strParam){
            this.strStartTime=strParam;
        }
        public void setStrEndTime(String strParam){
            this.strEndTime=strParam;
        }
        public void setContent(String strParam){
            this.strContent=strParam;
        }
        public String getID(){
            return strID;
        }
        public String getDate(){
            return strDate;
        }
        public String getStartTime(){
            return strStartTime;
        }
        public String getEndTime(){
            return strEndTime;
        }
        public String getContent(){
            return strContent;
        }
    };




    //요청메시지
    public static final int REQUEST_ADD_EVENT= 10000;


    //변수선언
    public static ArrayList<CalenderInfo> listCalender;
    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;
    public static String strParam;
    String dbName="calender";
    Toolbar toolbar;
    monthFragment mFragment;
    weekFragment  wFragment;
    dayFragment   dFragment;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //초기화
        Init();




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int menuID=item.getItemId();

        switch(menuID){

            case R.id.itemAddEvent:
                Intent intent = new Intent(getApplicationContext(), EventInfoActivity.class);
                startActivityForResult(intent, REQUEST_ADD_EVENT);
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==REQUEST_ADD_EVENT){
            if(resultCode==RESULT_OK){
                //1.디비에저장


                String strDate= data.getExtras().getString("date");
                String strStart=data.getExtras().getString("start");
                String strEnd=data.getExtras().getString("end");
                String strContent=data.getExtras().getString("content");
                Insert(strDate,strStart,strEnd,strContent);
                getData();

                //2.현재 strParam 프레그먼트열어주기
                Fragment selected=null;
                if(strParam.equals("month"))
                    selected=mFragment;
                else if(strParam.equals("week"))
                    selected=wFragment;
                else
                    selected=dFragment;
                FragmentManager fragmentManager=getSupportFragmentManager();
                FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container,selected).commit();

            }else if(resultCode==RESULT_CANCELED){

            }
        }
    }

    public void Init(){
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        mFragment=new monthFragment();
        wFragment=new weekFragment();
        dFragment=new dayFragment();
        listCalender=new ArrayList<CalenderInfo>();


        //db오픈
        boolean isOpen=openDatabase();
        if(isOpen){

            listCalender.clear();
            CalenderInfo cInfo=new CalenderInfo();
            final Cursor cursor=Select();
            int nRow=cursor.getCount();
            for(int nIdx=0;nIdx<nRow;nIdx++) {
                cursor.moveToNext();
                cInfo.setID(Integer.toString(cursor.getInt(0)));
                cInfo.setDate(cursor.getString(1));
                cInfo.setStartTime(cursor.getString(2));
                cInfo.setStrEndTime(cursor.getString(3));
                cInfo.setContent(cursor.getString(4));
                listCalender.add(cInfo);
            }


        }



        //액션바설정
        setSupportActionBar(toolbar);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);

        //탭설정
        TabLayout tabs=(TabLayout)findViewById(R.id.tabs);
        tabs.addTab(tabs.newTab().setText("월간"));
        tabs.addTab(tabs.newTab().setText("주간"));
        tabs.addTab(tabs.newTab().setText("일간"));


        //월간으로 초기설정
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container,mFragment).commit();

        tabs.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position=tab.getPosition();

                Fragment selected=null;
                if(position==0){
                    strParam="month";
                    selected=mFragment;
                }else if(position==1){
                    strParam="week";
                    selected=wFragment;
                }else if(position==2){
                    strParam="day";
                    selected=dFragment;
                }



                FragmentManager fragmentManager=getSupportFragmentManager();
                FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container,selected).commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });




    }

    private boolean openDatabase(){
        dbHelper=new DatabaseHelper(this,dbName,null,1);
        db=dbHelper.getWritableDatabase();
        return true;

    }

    public Cursor Select(){
        Cursor c=db.query("calender", null, null, null, null, null, null);
        return c;
    }

    public void Insert(String date,String start,String end,String content){
        ContentValues cInfo=new ContentValues();
        cInfo.put("date",date);
        cInfo.put("startTime",start);
        cInfo.put("endTime",end);
        cInfo.put("content",content);

        db.insert("calender",null,cInfo);


    }

    public void getData(){

        listCalender.clear();
        CalenderInfo cInfo=new CalenderInfo();
        Cursor cursor=Select();
        int nRow=cursor.getCount();
        for(int nIdx=0;nIdx<nRow;nIdx++) {
            cursor.moveToNext();
            cInfo.setID(cursor.getString(0));
            cInfo.setDate(cursor.getString(1));
            cInfo.setStartTime(cursor.getString(2));
            cInfo.setStrEndTime(cursor.getString(3));
            cInfo.setContent(cursor.getString(4));
            listCalender.add(cInfo);
        }

    }

}
