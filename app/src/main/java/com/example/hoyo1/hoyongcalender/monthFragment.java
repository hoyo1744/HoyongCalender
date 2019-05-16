package com.example.hoyo1.hoyongcalender;

import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.hoyo1.hoyongcalender.decorator.EventDayDecorator;
import com.example.hoyo1.hoyongcalender.decorator.OneDayDecorator;
import com.example.hoyo1.hoyongcalender.decorator.SaturdayDecorator;
import com.example.hoyo1.hoyongcalender.decorator.SundayDecorator;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link monthFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link monthFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class monthFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public monthFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment monthFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static monthFragment newInstance(String param1, String param2) {
        monthFragment fragment = new monthFragment();
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


    //선언
    MaterialCalendarView monthCalender;



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //초기화
        monthCalender=(MaterialCalendarView)getView().findViewById(R.id.monthCaleder);

        //캘린더옵션세팅
        SetCalender();

        //오늘날짜 설정
        SetToday();

        //이벤트설정
        ProcessEvent();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_month, container, false);


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

    public void SetToday(){
        //오늘날짜 설정
        OneDayDecorator oneDayDecorator;
        oneDayDecorator=new OneDayDecorator();
        monthCalender.addDecorators(oneDayDecorator);
    }
    public void ProcessEvent(){

        int nEventNum=MainActivity.listCalender.size();

        for(int nIdx=0;nIdx<nEventNum;nIdx++){
            String strID=MainActivity.listCalender.get(nIdx).strID;
            String strDate=MainActivity.listCalender.get(nIdx).strDate;
            String strContent=MainActivity.listCalender.get(nIdx).strContent;

            String[] item=strDate.split("-");

            CalendarDay day;
            day=CalendarDay.from(Integer.parseInt(item[2]),Integer.parseInt(item[1])-1,Integer.parseInt(item[0]));

            EventDayDecorator eventDayDecorator;
            eventDayDecorator=new EventDayDecorator(day, Color.RED,getActivity());
            monthCalender.addDecorators(eventDayDecorator);
        }

    }
    public void SetCalender(){
        //달력설정
        monthCalender.state().edit()
                .setFirstDayOfWeek(Calendar.SUNDAY)
                .setCalendarDisplayMode(CalendarMode.MONTHS).commit();

        monthCalender.addDecorators(new SundayDecorator(),new SaturdayDecorator());
    }



}
