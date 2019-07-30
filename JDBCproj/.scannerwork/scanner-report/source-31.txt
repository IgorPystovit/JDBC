package com.epam.igorpystovit.model.datetime;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

public class DateTimeParser {
    public Time timeParser(String stringTime){
        Time time = null;
        if ((stringTime.length() != 6) && (stringTime.length() != 8)){
            throw new IllegalArgumentException();
        }
        try{
            time = Time.valueOf(stringTime);
        } catch (IllegalArgumentException e){
            int hours = Integer.valueOf(stringTime.substring(0,2));
            int minutes = Integer.valueOf(stringTime.substring(2,4));
            int seconds = Integer.valueOf(stringTime.substring(4,6));
            time = Time.valueOf(LocalTime.of(hours,minutes,seconds));
        }
        return time;
    }

    public Date dateParser(String stringDate) throws IllegalArgumentException{
        Date date = null;
        if ((stringDate.length() != 8) && (stringDate.length() != 10)){
            throw new IllegalArgumentException();
        }
        try{
            date = Date.valueOf(stringDate);
        } catch (IllegalArgumentException e){
            int years = Integer.valueOf(stringDate.substring(0,4));
            int months = Integer.valueOf(stringDate.substring(4,6));
            int days = Integer.valueOf(stringDate.substring(6,8));
            date = Date.valueOf(LocalDate.of(years,months,days));
        }
        return date;
    }

    public static boolean isStringDateParsable(String stringDate){
        boolean isParsable = true;
        try{
            new DateTimeParser().dateParser(stringDate);
        } catch (IllegalArgumentException e){
            isParsable = false;
        }
        return isParsable;
    }

    public static boolean isStringTimeParsable(String stringTime){
        boolean isParsable = true;
        try{
            new DateTimeParser().timeParser(stringTime);
        } catch (IllegalArgumentException e){
            isParsable = false;
        }
        return isParsable;
    }
}
