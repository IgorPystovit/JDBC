package com.epam.igorpystovit;

import java.sql.Time;
import java.util.Date;

public class DateTimeComparator {
    private Date date;
    private Time time;

    public DateTimeComparator(){}
    public DateTimeComparator(Date date,Time time){
        this.date = date;
        this.time = time;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public Time getTime() {
        return time;
    }

    public boolean compare(Date date,Time time){
        if (!(this.date.toString().equals(date.toString())) && (this.time.toString().equals(time.toString()))){
            System.out.println(date+" "+this.date);
            System.out.println(time+" "+this.time);
        }
        return (this.date.toString().equals(date.toString())) && (this.time.toString().equals(time.toString()));
    }
}
