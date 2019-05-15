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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    //캘린더정보 자료형
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

    //디비이름
    private final String dbName="calender";

    //변수선언
    public static ArrayList<CalenderInfo> listCalender;
    private boolean bIsDatabaseOpen;
    private DatabaseHandler dbHandler;
    private monthFragment mFragment;
    private weekFragment  wFragment;
    private dayFragment   dFragment;
    private DatabaseHelper dbHelper;
    public static String strParam;
    private SQLiteDatabase db;
    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //초기화
        Init();
        //디비오픈
        OpenDB();
        //리스트로드
        LoadList();

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
                dbHandler.Insert(strDate,strStart,strEnd,strContent);
                LoadList();



                //2.프레그먼트열기
                LateOpenFragment();
            }else if(resultCode==RESULT_CANCELED){

            }
        }
    }

    public void Init(){
        toolbar=(Toolbar)findViewById(R.id.toolbar);

        mFragment=new monthFragment();
        wFragment=new weekFragment();
        dFragment=new dayFragment();
        bIsDatabaseOpen=false;

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
        strParam="month";


        tabs.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position=tab.getPosition();
                InitialOpenFragment(position);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }


    public void OpenDB(){
        if(dbHandler==null){
            dbHandler=DatabaseHandler.Open(MainActivity.this,dbName);
            bIsDatabaseOpen=true;
        }
    }

    public void LoadList(){
        if(bIsDatabaseOpen){
            listCalender.clear();

            Cursor cursor=dbHandler.Select();
            int nRow=cursor.getCount();
            for(int nIdx=0;nIdx<nRow;nIdx++) {
                CalenderInfo cInfo=new CalenderInfo();
                cursor.moveToNext();
                cInfo.setID(cursor.getString(0));
                cInfo.setDate(cursor.getString(1));
                cInfo.setStartTime(cursor.getString(2));
                cInfo.setStrEndTime(cursor.getString(3));
                cInfo.setContent(cursor.getString(4));
                listCalender.add(cInfo);
            }
        }else{
            Log.e("에러","리스트불러오기");
            android.os.Process.killProcess(android.os.Process.myPid());
        }

    }

    public void LateOpenFragment(){
        Fragment selected=null;
        if(strParam.equals("month")) {
            selected = mFragment;
            mFragment.showMonthView();
            mFragment.ProcessEvent();
        }
        else if(strParam.equals("week")) {
            selected = wFragment;
        }
        else {
            selected = dFragment;
        }




        //사실 의미 없었다.
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container,selected).commit();


    }

    public void InitialOpenFragment(int position){
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

}
