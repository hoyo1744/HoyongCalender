package com.example.hoyo1.hoyongcalender;

import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.IntentCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.hoyo1.hoyongcalender.DatabaseAgent.DatabaseHandler;
import com.example.hoyo1.hoyongcalender.DatabaseAgent.DatabaseHelper;
import com.example.hoyo1.hoyongcalender.Fragment.dayFragment;
import com.example.hoyo1.hoyongcalender.Fragment.monthFragment;
import com.example.hoyo1.hoyongcalender.Fragment.weekFragment;
import com.example.hoyo1.hoyongcalender.Fragment.weekGridFragment;
import com.prolificinteractive.materialcalendarview.CalendarDay;

import java.sql.Date;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    //캘린더정보 자료형
    public class CalenderInfo{
        public String strID;
        public String strDate;
        public String strStartTime;
        public String strEndTime;
        public String strContent;

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
    public static final int REQUEST_DETAIL_EVENT=10001;


    //디비이름
    private final String dbName="calender";

    //변수선언
    public static ArrayList<CalenderInfo> listCalender;
    public static CalendarDay InitialToday;
    private long backButtonPushTime= 0;
    public static Context mainContext;
    private boolean bIsDatabaseOpen;
    private DatabaseHandler dbHandler;
    private monthFragment mFragment;
    private weekGridFragment wFragment;
    //private weekFragment wFragment;
    private dayFragment dFragment;
    private DatabaseHelper dbHelper;
    public static String strParam;
    private SQLiteDatabase db;
    private Toolbar toolbar;
    private TabLayout tabs;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //앱시작시 오늘의 날짜설정
        InitialToday=CalendarDay.today();

        //메인액티비티컨텍스트
        mainContext=this;

        //초기화
        Init();
        //디비오픈
        OpenDB();
        //리스트로드
        LoadList();

        //월간으로 초기설정
        if(SingletonSelectedTab.getInstance().getPosition()==-1) {
            SingletonSelectedTab.getInstance().setPosition(0);
            InitialOpenFragment(0);
        }else {
            InitialOpenFragment(SingletonSelectedTab.getInstance().getPosition());
            tabs.setScrollPosition(SingletonSelectedTab.getInstance().getPosition(),0f,true);
        }

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
            case R.id.itemAppClose:
                ShowCloseMessage("종료하기","종료하시겠습니까?");
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
                String strContent=data.getExtras().getString("content");
                dbHandler.Insert(strDate,strContent);
                LoadList();



                //2.프레그먼트열기
                LateOpenFragment();
            }else if(resultCode==RESULT_CANCELED){

            }
        }
    }

    public void Init(){
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        listCalender=new ArrayList<CalenderInfo>();
        mFragment=new monthFragment();
        //wFragment=new weekFragment();
        //wFragment=new weekGridFragment();
        //dFragment=new dayFragment();
        bIsDatabaseOpen=false;

        //액션바설정
        setSupportActionBar(toolbar);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);

        //탭설정
        tabs=(TabLayout)findViewById(R.id.tabs);
        tabs.addTab(tabs.newTab().setText("월간"));
        tabs.addTab(tabs.newTab().setText("주간"));
        tabs.addTab(tabs.newTab().setText("일간"));








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
                cInfo.setDate(cursor.getString(1));;
                cInfo.setContent(cursor.getString(4));
                listCalender.add(cInfo);
            }
        }else{
            ShowErrorMessage("DataBase Open Error");
        }

    }

    public void LateOpenFragment(){
        Fragment selected=null;
        if(strParam.equals("month")) {
            selected = mFragment;
            mFragment.ProcessEvent();
        }
        else if(strParam.equals("week")) {

            selected = wFragment;

            /*
            //리스트버전
            wFragment.ProcessEvent();
            CalendarDay today= new CalendarDay(CalendarDay.today().getYear(),CalendarDay.today().getMonth()+1,CalendarDay.today().getDay());
            wFragment.LoadList(today);
            */

            //그리드버전
            wFragment.ProcessEvent();
            wFragment.LoadGrid();


        }
        else {
            selected = dFragment;
            dFragment.SetCalender();
            String today=Integer.toString(CalendarDay.today().getYear())+"-"+Integer.toString(CalendarDay.today().getMonth()+1)+"-"+Integer.toString(CalendarDay.today().getDay());
            dFragment.LoadList(today);
        }

    }

    public void InitialOpenFragment(int position){
        Fragment selected=null;
        if(position==0){
            SingletonSelectedTab.getInstance().setPosition(position);
            strParam="month";
            mFragment=new monthFragment();
            selected=mFragment;
        }else if(position==1){
            SingletonSelectedTab.getInstance().setPosition(position);
            strParam="week";
            wFragment= new weekGridFragment();
            selected=wFragment;
        }else if(position==2){
            SingletonSelectedTab.getInstance().setPosition(position);
            strParam="day";
            dFragment=new dayFragment();
            selected=dFragment;
        }
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container,selected).commit();
    }
    //
    public void KillApp(){
        ActivityCompat.finishAffinity(this);
        System.runFinalizersOnExit(true);
        System.exit(0);
    }



    public void ShowErrorMessage(String content){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        //대화상자설정
        builder.setTitle("에러");
        builder.setMessage(content);
        builder.setIcon(android.R.drawable.ic_dialog_alert);


        //예 버튼 추가
        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                KillApp();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
    public void ShowCloseMessage(String title,String content){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        //대화상자설정
        builder.setTitle(title);
        builder.setMessage(content);
        builder.setIcon(android.R.drawable.ic_dialog_alert);


        //예 버튼 추가
        builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                KillApp();
            }
        }).setNegativeButton("아니오", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
    @Override
    public void onBackPressed(){
        if(System.currentTimeMillis()-backButtonPushTime>=2000){
            backButtonPushTime=System.currentTimeMillis();
        }else if(System.currentTimeMillis()-backButtonPushTime<2000){
            ShowCloseMessage("종료하기","종료하시겠습니까?");
        }
    }







}
