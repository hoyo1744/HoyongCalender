package com.example.hoyo1.hoyongcalender.Grid;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.example.hoyo1.hoyongcalender.Common.AutoResizeTextView;
import com.example.hoyo1.hoyongcalender.R;

public class GridEmptyView extends LinearLayout{

    AutoResizeTextView gridEmptyTextView;



    public GridEmptyView(Context context) {
        super(context);
        Init(context);
    }
    public GridEmptyView(){
        super(null);
    }
    public GridEmptyView(Context context, AutoResizeTextView gridEmptyTextView) {
        super(context);
        this.gridEmptyTextView = gridEmptyTextView;
    }


    public void Init(Context context){
        LayoutInflater inflater=(LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.grid_empty_view,this,true);

        gridEmptyTextView=(AutoResizeTextView)findViewById(R.id.gridEmptyTextView);
    }
    public void setContent(){
        gridEmptyTextView.setText("");
    }

}
