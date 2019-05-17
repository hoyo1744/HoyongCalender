package com.example.hoyo1.hoyongcalender.Grid;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.hoyo1.hoyongcalender.R;


public class GridTextView extends LinearLayout {

    TextView gridTextView;
    public GridTextView(Context context) {
        super(context);
        Init(context);
    }


    public void Init(Context context){
        LayoutInflater inflater=(LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.grid_text_view,this,true);

        gridTextView=(TextView)findViewById(R.id.gridTextView);

    }
    public void setContent(String content){
        gridTextView.setText(content);
    }
}
