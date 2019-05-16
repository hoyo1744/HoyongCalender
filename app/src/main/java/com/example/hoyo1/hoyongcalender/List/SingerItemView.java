package com.example.hoyo1.hoyongcalender.List;


import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.hoyo1.hoyongcalender.Common.AutoResizeTextView;
import com.example.hoyo1.hoyongcalender.R;

public class SingerItemView extends LinearLayout {
    AutoResizeTextView textView;

    public SingerItemView(Context context) {
        super(context);
        Init(context);
    }

    public void Init(Context context){
        //getSystemService함수를 통해서 인플레이터객체를 반환
        LayoutInflater inflater=(LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.singer_item,this,true);

        textView=(AutoResizeTextView)findViewById(R.id.weekTextView);
    }

    public void setContent(String strParam){
        textView.setText(strParam);
    }
}
