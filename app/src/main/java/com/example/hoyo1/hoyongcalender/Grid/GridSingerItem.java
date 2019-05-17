package com.example.hoyo1.hoyongcalender.Grid;

public class GridSingerItem {
    private int type;
    String content;

    public GridSingerItem(String content,int type){
        this.content=content;
        this.type=type;
    }

    public int getType(){
        return this.type;
    }
    public void setType(int type){
        this.type=type;
    }

    public String getContent(){
        return this.content;
    }
    public void setContet(String content){
        this.content=content;
    }
}
