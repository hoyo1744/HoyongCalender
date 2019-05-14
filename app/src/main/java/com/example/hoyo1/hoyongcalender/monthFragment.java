package com.example.hoyo1.hoyongcalender;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.desai.vatsal.mydynamiccalendar.MyDynamicCalendar;
import com.desai.vatsal.mydynamiccalendar.EventModel;
import com.desai.vatsal.mydynamiccalendar.GetEventListListener;
import com.desai.vatsal.mydynamiccalendar.OnDateClickListener;
import com.desai.vatsal.mydynamiccalendar.OnEventClickListener;
import com.desai.vatsal.mydynamiccalendar.OnWeekDayViewClickListener;

import java.util.ArrayList;
import java.util.Date;

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

    //변수선언
    MyDynamicCalendar monthCalendar;
    //변수선언끝


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //monthCalender
        monthCalendar = (MyDynamicCalendar) getView().findViewById(R.id.monthCalendar);
        monthCalendar.showMonthView();


        ProcessEvent();



        monthCalendar.setOnDateClickListener(new OnDateClickListener() {
            @Override
            public void onClick(Date date) {


            }
            @Override
            public void onLongClick(Date date) {
                Log.e("date", String.valueOf(date));

            }
        });


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

    private void showMonthView() {
        monthCalendar.showMonthView();
    }



    public void ProcessEvent(){

        int nEventNum=MainActivity.listCalender.size();

        for(int nIdx=0;nIdx<nEventNum;nIdx++){
            String strID=MainActivity.listCalender.get(nIdx).strID;
            String strDate=MainActivity.listCalender.get(nIdx).strDate;
            String strStart=MainActivity.listCalender.get(nIdx).strStartTime;
            String strEnd=MainActivity.listCalender.get(nIdx).strEndTime;
            String strContent=MainActivity.listCalender.get(nIdx).strContent;

            monthCalendar.addEvent(strDate, strStart, strEnd, strContent,R.drawable.ic_brightness_1_black_24dp);

        }

    }

}
