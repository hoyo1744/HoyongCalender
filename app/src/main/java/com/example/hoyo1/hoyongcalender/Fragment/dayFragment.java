package com.example.hoyo1.hoyongcalender.Fragment;

import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.IntentCompat;
import android.support.v7.app.AlertDialog;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.hoyo1.hoyongcalender.EventInfoActivity;
import com.example.hoyo1.hoyongcalender.List.SingerAdapter;
import com.example.hoyo1.hoyongcalender.List.SingerItem;
import com.example.hoyo1.hoyongcalender.MainActivity;
import com.example.hoyo1.hoyongcalender.R;
import com.prolificinteractive.materialcalendarview.CalendarDay;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link dayFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link dayFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class dayFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public dayFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment dayFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static dayFragment newInstance(String param1, String param2) {
        dayFragment fragment = new dayFragment();
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
    private final int LIST_NUMS=1;
    private SingerAdapter adapter;
    private TextView dayCalender;
    private String selectedDate="";
    private String selectedYear="";
    private String selectedMonth="";
    private String selectedDay="";
    private String currentMonth;
    private String currentYear;
    private String currentDay;
    private ListView listVIew;
    private String dayOfWeek;
    private ImageButton FastBtn;
    private ImageButton FutureBtn;



    private final View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v == FastBtn) {
                CalculateFastDate();
                GetDayOfWeek(selectedDate);
                LoadList(selectedDate);
                dayCalender.setText(dayOfWeek+","+selectedDate);

            } else if (v == FutureBtn) {
                CalculateFutureDate();
                GetDayOfWeek(selectedDate);
                LoadList(selectedDate);
                dayCalender.setText(dayOfWeek+","+selectedDate);

            }
        }
    };


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //초기화
        Init();

        //캘린더 세팅
        SetCalender();

        //리스트헤더설정
        SetListHeader();

        //리스트로드
        LoadList(selectedDate);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_day, container, false);
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

    public void SetCalender() {

        //오늘날짜설정
        currentYear= Integer.toString(CalendarDay.today().getYear());
        currentMonth=Integer.toString(CalendarDay.today().getMonth()+1);
        currentDay=Integer.toString(CalendarDay.today().getDay());

        if(currentMonth.length()==1){
            currentMonth="0"+currentMonth;
        }
        if(currentDay.length()==1){
            currentDay="0"+currentDay;
        }

        //오늘요일설정
        String date=currentYear+"-"+currentMonth+"-"+currentDay;
        GetDayOfWeek(date);



        //선택된 날짜설정
        if(selectedYear.isEmpty())
            selectedYear=currentYear;
        if(selectedMonth.isEmpty())
            selectedMonth=currentMonth;
        if(selectedDay.isEmpty())
            selectedDay=currentDay;
        if(selectedDate.isEmpty())
            selectedDate=currentYear+"-"+currentMonth+"-"+currentDay;


        //캘린더설정
        dayCalender.setText(dayOfWeek+","+selectedDate);


    }

    public void CalculateFastDate(){
        //선택된날짜변경
        Calendar cal = Calendar.getInstance();

        Date date =Date.valueOf(selectedDate);
        cal.setTime(date);
        cal.add(Calendar.DATE, -1);

        SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
        String temp=dateFormat.format(cal.getTime());

        String[] item=temp.split("-");




        selectedYear=item[0];
        selectedMonth=item[1];
        selectedDay=item[2];
        selectedDate=selectedYear+"-"+selectedMonth+"-"+selectedDay;


    }
    public void CalculateFutureDate(){
        //선택된날짜변경
        Calendar cal = Calendar.getInstance();

        Date date =Date.valueOf(selectedDate);
        cal.setTime(date);
        cal.add(Calendar.DATE, 1);

        SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
        String temp=dateFormat.format(cal.getTime());

        String[] item=temp.split("-");

        selectedYear=item[0];
        selectedMonth=item[1];
        selectedDay=item[2];
        selectedDate=selectedYear+"-"+selectedMonth+"-"+selectedDay;


    }
    public void GetDayOfWeek(String source) {


        Date date=Date.valueOf(source);
        Calendar cal=Calendar.getInstance();
        cal.setTime(date);
        int dayNum=cal.get(Calendar.DAY_OF_WEEK);
        switch(dayNum){
            case 1:
                dayOfWeek="Sun";
                break;
            case 2:
                dayOfWeek="Mon";
                break;
            case 3:
                dayOfWeek="Tue";
                break;
            case 4:
                dayOfWeek="Wed";
                break;
            case 5:
                dayOfWeek="Thu";
                break;
            case 6:
                dayOfWeek="Fri";
                break;
            case 7:
                dayOfWeek="Sat";
                break;
        }
    }

    public void LoadList(String date){
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
    public String TranslateCompareDate(String date){
        String[] Item=date.split("-");
        if(Item[1].charAt(0)=='0'){
            Item[1]=String.valueOf(Item[1].charAt(1));
        }
        if(Item[2].charAt(0)=='0'){
            Item[2]=String.valueOf(Item[2].charAt(1));
        }
        String strCompareDate=Item[2]+"-"+Item[1]+"-"+Item[0];
        return strCompareDate;
    }
    public void SetListHeader(){
        View header = getLayoutInflater().inflate(R.layout.list_head, null, false);
        listVIew.addHeaderView(header);
    }
    public void Init(){
        dayCalender=(TextView)getView().findViewById(R.id.dayCalender);
        listVIew=(ListView)getView().findViewById(R.id.dayListView);
        FastBtn=(ImageButton)getView().findViewById(R.id.previous);
        FutureBtn=(ImageButton)getView().findViewById(R.id.next);
        FutureBtn.setOnClickListener(onClickListener);
        FastBtn.setOnClickListener(onClickListener);
        adapter=new SingerAdapter(getContext());


        //컨텍스트메뉴설정
        registerForContextMenu(listVIew);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        //super.onCreateContextMenu(menu, v, menuInfo);

        //헤드예외
        AdapterView.AdapterContextMenuInfo info=(AdapterView.AdapterContextMenuInfo)menuInfo;
        int pos=info.position;
        if(pos==0)
            return ;

        getActivity().getMenuInflater().inflate(R.menu.menu_detail_event, menu);
    }
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        try {
            int pos = info.position;
            //헤드 예외처리
            if (pos == 0)
                return super.onContextItemSelected(item);

            pos -= LIST_NUMS;

            SingerItem singerItem = (SingerItem) adapter.getItem(pos);
            switch (item.getItemId()) {
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
                return super.onContextItemSelected(item); }


    }

    public void ReFresh(){
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.detach(this).attach(this).commit();
    }








}
