package com.example.hoyo1.hoyongcalender;

public class SingletonSelectedTab {

    private int position=-1;

    public SingletonSelectedTab(){}

    public void setPosition(int pos){
        this.position=pos;
    }
    public int getPosition(){
        return this.position;
    }



    private static class SingletonSelectedTabHolder{
        public static final SingletonSelectedTab instance=new SingletonSelectedTab();
    }

    public static SingletonSelectedTab getInstance(){
        return SingletonSelectedTabHolder.instance;
    }
}
