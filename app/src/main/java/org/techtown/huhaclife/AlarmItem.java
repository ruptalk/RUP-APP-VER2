package org.techtown.huhaclife;

import android.graphics.drawable.Drawable;

public class AlarmItem {

    private Drawable icon;
    public String content, month, day;

    public AlarmItem() { }

    public AlarmItem(String content){
        this.icon=icon;
        this.content=content;
    }

    public AlarmItem(String content, String month, String day){
        this.icon=icon;
        this.content=content;
        this.month=month;
        this.day=day;
    }

    public Drawable getIcon(){ return icon; }

    public void setIcon(Drawable icon) { this.icon= icon; }

    public String getContent() { return content; }

    public void setContent(String content) { this.content=content; }

    public String getMonth() { return month; }

    public void setMonth(String month) { this.month=month; }

    public String getDay() { return day; }

    public void setDay(String day) { this.day=day; }

//    public Drawable icon;
//    public String content;
//    public String month;
//    public String day;
//
//    public AlarmItem(String content, String month, String day){
//        this.content=content;
//        this.month=month;
//        this.day=day;
//    }

}
