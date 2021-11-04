package org.techtown.huhaclife;

import android.graphics.drawable.Drawable;

public class AlarmItem {
    public Drawable icon;
    public String content;
    public String month;
    public String day;

    public AlarmItem(String content, String month, String day){
        this.content=content;
        this.month=month;
        this.day=day;
    }

}
