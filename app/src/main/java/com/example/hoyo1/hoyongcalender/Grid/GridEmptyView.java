package com.example.hoyo1.hoyongcalender.Grid;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.hoyo1.hoyongcalender.R;

import org.w3c.dom.Text;

public class GridEmptyView extends LinearLayout{

    TextView gridEmptyTextView;
    public GridEmptyView(Context context) {
        super(context);
        Init(context);
    }

    public void Init(Context context){
        LayoutInflater inflater=(LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.grid_empty_view,this,true);

        gridEmptyTextView=(TextView)findViewById(R.id.gridEmptyTextView);
    }
    public void setContent(){
        gridEmptyTextView.setText("");
    }

}
