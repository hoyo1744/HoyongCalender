package com.example.hoyo1.hoyongcalender.decorator;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;

import com.example.hoyo1.hoyongcalender.R;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.spans.DotSpan;

import java.sql.Date;


public class EventDayDecorator implements DayViewDecorator {

    private Drawable drawable;
    private int color;


    private CalendarDay date;

    public EventDayDecorator(CalendarDay date) {
        this.date = date;
    }

    public EventDayDecorator(CalendarDay date,int color, Activity context) {
        drawable = context.getResources().getDrawable(R.drawable.ic_brightness_1_black_24dp);
        this.color = color;
        this.date = date;
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return date != null && day.equals(date);

    }

    @Override
    public void decorate(DayViewFacade view) {
/*
        view.addSpan(new StyleSpan(Typeface.BOLD));
        view.addSpan(new RelativeSizeSpan(1.4f));
        view.addSpan(new ForegroundColorSpan(Color.RED));
*/
        //view.setSelectionDrawable(drawable);
        view.addSpan(new DotSpan(10, color)); // 날자밑에 점
    }

    public void setDate(Date date) {
        this.date = CalendarDay.from(date);
    }
}