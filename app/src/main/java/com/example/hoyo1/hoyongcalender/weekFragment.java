package com.example.hoyo1.hoyongcalender;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.desai.vatsal.mydynamiccalendar.MyDynamicCalendar;
import com.desai.vatsal.mydynamiccalendar.EventModel;
import com.desai.vatsal.mydynamiccalendar.GetEventListListener;
import com.desai.vatsal.mydynamiccalendar.OnDateClickListener;
import com.desai.vatsal.mydynamiccalendar.OnEventClickListener;
import com.desai.vatsal.mydynamiccalendar.OnWeekDayViewClickListener;
import com.example.hoyo1.hoyongcalender.List.SingerAdapter;
import com.example.hoyo1.hoyongcalender.List.SingerItem;
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
import java.util.Date;
import java.util.List;
import android.view.Menu;
import android.view.MenuItem;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link weekFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link weekFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class weekFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public weekFragment() {
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
    MaterialCalendarView weekCalender;
    CalendarDay currentShowFirstDay;
    CalendarDay currentDay;
    SingerAdapter adapter;
    ListView listVIew;



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //초기화
        weekCalender=(MaterialCalendarView)getView().findViewById(R.id.monthCaleder);
        listVIew=(ListView)getView().findViewById(R.id.weekListView);
        adapter=new SingerAdapter(getContext());


        //캘린더 세팅
        SetCalender();

        //오늘날짜 설정
        SetToday();

        //이벤트처리
        ProcessEvent();

        //리스트헤더설정
        SetListHeader();

        //리스트뷰
        LoadList(currentDay);




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

        //수정해야함.(-3일)
        currentShowFirstDay=new CalendarDay(CalendarDay.today().getYear(),CalendarDay.today().getMonth()+1,CalendarDay.today().getDay()-3);;


    }

    public void ProcessEvent(){
        int nEventNum=MainActivity.listCalender.size();

        for(int nIdx=0;nIdx<nEventNum;nIdx++){
            String strID=MainActivity.listCalender.get(nIdx).strID;
            String strDate=MainActivity.listCalender.get(nIdx).strDate;
            String strStart=MainActivity.listCalender.get(nIdx).strStartTime;
            String strEnd=MainActivity.listCalender.get(nIdx).strEndTime;
            String strContent=MainActivity.listCalender.get(nIdx).strContent;

            String[] item=strDate.split("-");

            CalendarDay day;
            day=CalendarDay.from(Integer.parseInt(item[2]),Integer.parseInt(item[1])-1,Integer.parseInt(item[0]));

            EventDayDecorator eventDayDecorator;
            eventDayDecorator=new EventDayDecorator(day, Color.RED,getActivity());
            weekCalender.addDecorators(eventDayDecorator);
        }
    }


    public void LoadList(CalendarDay date) {
        //리스트초기화
        adapter.removeAll();

        int nEventNum = MainActivity.listCalender.size();
        for (int nIdx = 0; nIdx < nEventNum; nIdx++) {
            String strDate = MainActivity.listCalender.get(nIdx).strDate;
            String strContent = MainActivity.listCalender.get(nIdx).strContent;

            String strCompareDate=TranslateCompareDate(date);

            if(strDate.equals(strCompareDate)){
                adapter.addItem(new SingerItem(strContent));
            }
        }
        listVIew.setAdapter(adapter);
        //리스트 리프레쉬
        adapter.notifyDataSetChanged();
    }
    public void SetListHeader(){
        View header = getLayoutInflater().inflate(R.layout.list_head, null, false);
        listVIew.addHeaderView(header);
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
                //date란 녀석이다.
                currentShowFirstDay=new CalendarDay(date.getYear(),date.getMonth()+1,date.getDay());

            }
        });

        //날짜선택
        weekCalender.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {

                CalendarDay d= new CalendarDay(date.getYear(),date.getMonth()+1,date.getDay());
                LoadList(d);

            }
        });

    }


}
