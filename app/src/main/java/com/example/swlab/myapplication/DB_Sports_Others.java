package com.example.swlab.myapplication;

/**
 * Created by swlab on 2017/5/1.
 */

public class DB_Sports_Others {
    private String sportdate;
    private String cal;
    private String count;
    private String sporttime;

    public DB_Sports_Others(){
    }

    public DB_Sports_Others(String sportdate, String cal, String count, String sporttime) {
        this.sportdate = sportdate;
        this.cal = cal;
        this.count = count;
        this.sporttime = sporttime;
    }

    public String getSportdate() {
        return sportdate;
    }

    public void setSportdate(String sportdate) {
        this.sportdate = sportdate;
    }

    public String getCal() {
        return cal;
    }

    public void setCal(String cal) {
        this.cal = cal;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getSporttime() {
        return sporttime;
    }

    public void setSporttime(String sporttime) {
        this.sporttime = sporttime;
    }
}
