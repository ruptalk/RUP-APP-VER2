package org.techtown.huhaclife;

public class Mission {
    private String context;
    private int progresspercent;



    private int percent;

    public Mission(String context,int progresspercent,int percent){
        this.context=context;
        this.progresspercent=progresspercent;
        this.percent=percent;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public int getProgresspercent() {
        return progresspercent;
    }

    public void setProgresspercent(int progresspercent) {
        this.progresspercent = progresspercent;
    }

    public int getPercent() {
        return percent;
    }

    public void setPercent(int percent) {
        this.percent = percent;
    }



}
