package com.example.hoyo1.hoyongcalender.Grid;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.nio.channels.GatheringByteChannel;
import java.util.ArrayList;

public class GridAdapter extends BaseAdapter {
    public static final int ITEM_VIEW_MAX=2;
    public static final int ITEM_VIEW_TEXT=0;
    public static final int ITEM_VIEW_EMPTY=1;

    //프로필어댑터
    ArrayList<GridSingerItem> Items=new ArrayList<GridSingerItem>();
    public Context adapterContext;

    public GridAdapter(Context context){
        adapterContext=context;
    }


    public void removeAll(){
        Items.clear();
    }
    @Override
    public int getItemViewType(int position) {
        return Items.get(position).getType();
    }

    @Override
    public int getViewTypeCount() {
        return ITEM_VIEW_MAX;
    }
    public void addItem(GridSingerItem item){

        Items.add(item);
    }
    @Override
    public int getCount() {
        return Items.size();

    }

    @Override
    public Object getItem(int position) {
        return Items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return  position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        int viewType=getItemViewType(position);

        if(convertView==null){
            GridSingerItem item=Items.get(position);

            switch (viewType){
                case ITEM_VIEW_TEXT:
                    GridTextView gridTextView=new GridTextView(adapterContext);
                    gridTextView.setGravity(Gravity.CENTER);
                    gridTextView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    gridTextView.setContent(item.getContent());
                    convertView=gridTextView;
                    break;
                case ITEM_VIEW_EMPTY:
                    GridEmptyView gridEmptyView=new GridEmptyView(adapterContext);
                    gridEmptyView.setGravity(Gravity.CENTER);
                    gridEmptyView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    gridEmptyView.setContent();
                    convertView = gridEmptyView;
                    break;
            }
        }


        return convertView;

    }
}
