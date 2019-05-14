package com.example.hoyo1.hoyongcalender;

import android.content.Intent;
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


public class MainActivity extends AppCompatActivity {


    //요청메시지
    public static final int REQUEST_ADD_EVENT= 10000;


    //변수선언
    public static String strParam;
    Toolbar toolbar;
    monthFragment mFragment;
    weekFragment  wFragment;
    dayFragment   dFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
}
