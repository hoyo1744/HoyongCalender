package com.example.hoyo1.hoyongcalender.Fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;

import com.example.hoyo1.hoyongcalender.EventInfoActivity;
import com.example.hoyo1.hoyongcalender.Grid.GridAdapter;
import com.example.hoyo1.hoyongcalender.Grid.GridSingerItem;
import com.example.hoyo1.hoyongcalender.List.SingerAdapter;
import com.example.hoyo1.hoyongcalender.List.SingerItem;
import com.example.hoyo1.hoyongcalender.List.SingerItemView;
import com.example.hoyo1.hoyongcalender.MainActivity;
import com.example.hoyo1.hoyongcalender.R;
import com.example.hoyo1.hoyongcalender.decorator.EventDayDecorator;
import com.example.hoyo1.hoyongcalender.decorator.OneDayDecorator;
import com.example.hoyo1.hoyongcalender.decorator.SaturdayDecorator;
import com.example.hoyo1.hoyongcalender.decorator.SundayDecorator;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;

import java.util.ArrayList;
import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link weekFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link weekFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class weekGridFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public weekGridFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment weekFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static weekFragment newInstance(String param1, String param2) {
        weekFragment fragment = new weekFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    //변수선언
    private MaterialCalendarView weekCalender;
    private CalendarDay currentShowFirstDay;
    private CalendarDay currentShowLastDay;
    private GridAdapter gridAdapter;
    private CalendarDay currentDay;
    private SingerAdapter adapter;
    private GridView gridView;




    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //초기화
        Init();

        //캘린더 세팅
        SetCalender();

        //오늘날짜 설정
        SetToday();

        //이벤트처리
        ProcessEvent();

        //리스트헤더설정

        //그리드뷰
        LoadGrid();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_week, container, false);

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    /*
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }
    */

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public  void SetToday(){
        //오늘날짜 설정
        OneDayDecorator oneDayDecorator;
        oneDayDecorator=new OneDayDecorator();

        weekCalender.addDecorators(oneDayDecorator);

        //오늘
        currentDay=new CalendarDay(CalendarDay.today().getYear(),CalendarDay.today().getMonth()+1,CalendarDay.today().getDay());

        //각주의 시작날과 마지막날 알아오기
        currentShowFirstDay=GetFirstDayOfWeek(CalendarDay.today());
        currentShowLastDay=GetLastDayOfWeek(CalendarDay.today());


    }

    public void ProcessEvent(){
        int nEventNum= MainActivity.listCalender.size();

        for(int nIdx=0;nIdx<nEventNum;nIdx++){
            String strID=MainActivity.listCalender.get(nIdx).strID;
            String strDate=MainActivity.listCalender.get(nIdx).strDate;
            String strContent=MainActivity.listCalender.get(nIdx).strContent;

            String[] item=strDate.split("-");

            CalendarDay day;
            day=CalendarDay.from(Integer.parseInt(item[2]),Integer.parseInt(item[1])-1,Integer.parseInt(item[0]));

            EventDayDecorator eventDayDecorator;
            eventDayDecorator=new EventDayDecorator(day, Color.RED,getActivity());
            weekCalender.addDecorators(eventDayDecorator);
        }
    }


    public void LoadGrid(){
        gridAdapter.removeAll();


        ArrayList<MainActivity.CalenderInfo> listTemp=new ArrayList<MainActivity.CalenderInfo>();
        listTemp.addAll(MainActivity.listCalender);


        CalendarDay day=currentShowFirstDay;
        boolean bIsExistForWeekAtLeast=false;
        while(true){
            int nEventNum =listTemp.size();
            boolean bIsExist=false;
            String strDate="";
            String strContent="";
            String strCompareDate="";

            if(day.equals(currentShowLastDay) && bIsExistForWeekAtLeast==false) {
                break;
            }
            if(day.equals(currentShowLastDay) && bIsExistForWeekAtLeast==true){
                bIsExistForWeekAtLeast=false;
                day=currentShowFirstDay;
            }
            for(int nIdx=0;nIdx<nEventNum;nIdx++){
                strDate=listTemp.get(nIdx).strDate;
                strContent=listTemp.get(nIdx).strContent;
                strCompareDate=TranslateCompareDate(day);
                if(strDate.equals(strCompareDate)) {
                    gridAdapter.addItem(new GridSingerItem(strContent, GridAdapter.ITEM_VIEW_TEXT));
                    listTemp.remove(nIdx);
                    bIsExist = true;
                    bIsExistForWeekAtLeast=true;
                    break;
                }
            }
            if(bIsExist==false)
                gridAdapter.addItem(new GridSingerItem(strContent, GridAdapter.ITEM_VIEW_EMPTY));
            day=new CalendarDay(day.getYear(),day.getMonth(),day.getDay()+1);
        }

        gridView.setAdapter(gridAdapter);
    }

    public String TranslateCompareDate(CalendarDay date){
        String strCompareDate=Integer.toString(date.getDay())+"-"+Integer.toString(date.getMonth())+"-"+Integer.toString(date.getYear());
        return strCompareDate;
    }
    public void SetCalender(){
        //달력설정
        weekCalender.state().edit()
                .setFirstDayOfWeek(Calendar.SUNDAY)
                .setCalendarDisplayMode(CalendarMode.WEEKS).commit();
        weekCalender.addDecorators(new SundayDecorator(),new SaturdayDecorator());

        //달변경
        weekCalender.setOnMonthChangedListener(new OnMonthChangedListener() {
            @Override
            public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {
                currentShowFirstDay=new CalendarDay(date.getYear(),date.getMonth()+1,date.getDay());
                currentShowLastDay=new CalendarDay(date.getYear(),date.getMonth()+1,date.getDay()+7);
                LoadGrid();
            }
        });

        //날짜선택
        weekCalender.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {

            }
        });
    }
    public void Init(){
        //초기화
        weekCalender=(MaterialCalendarView)getView().findViewById(R.id.weekCaleder);
        gridView=(GridView)getView().findViewById(R.id.weekGridView);
        gridAdapter=new GridAdapter(getContext());
        adapter=new SingerAdapter(getContext());
        registerForContextMenu(gridView);

    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        //super.onCreateContextMenu(menu, v, menuInfo);
        getActivity().getMenuInflater().inflate(R.menu.menu_detail_event, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        try {
            GridSingerItem singerItem = (GridSingerItem) adapter.getItem(info.position);
            switch(item.getItemId()){
                case R.id.itemShowEvent:
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                    //대화상자설정
                    builder.setTitle("자세히보기");
                    builder.setMessage(singerItem.getContent());
                    builder.setIcon(android.R.drawable.ic_dialog_info);


                    //예 버튼 추가
                    builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {

                        }
                    });

                    AlertDialog dialog = builder.create();
                    dialog.show();
                    return true;
                default:
                    return super.onContextItemSelected(item);
            }
        }catch(Exception e){
            return super.onContextItemSelected(item);
        }
    }
    public CalendarDay GetFirstDayOfWeek(CalendarDay today){
        CalendarDay result;
        Calendar cal=today.getCalendar();
        int day=cal.get(Calendar.DAY_OF_WEEK);
        int diffDay=day-1;
        result=new CalendarDay(today.getYear(),today.getMonth()+1,today.getDay()-diffDay);
        return result;
    }
    public CalendarDay GetLastDayOfWeek(CalendarDay today){
        CalendarDay result;
        Calendar cal=today.getCalendar();
        int day=cal.get(Calendar.DAY_OF_WEEK);
        int diffDay=8-day;
        result=new CalendarDay(today.getYear(),today.getMonth()+1,today.getDay()+diffDay);
        return result;

    }

}
