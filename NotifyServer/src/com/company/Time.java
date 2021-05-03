package com.company;

import java.util.Calendar;

public class Time implements Comparable <Time> {
    private int hour;
    private int minute;

    Time(int hour, int minute){
        this.hour = hour;
        this.minute = minute;
    }

    public int getHour(){
        return this.hour;
    }

    public int getMinute(){
        return this.minute;
    }


    public void setHour(int hour, int minute){
        this.hour = hour;
        this.minute = minute;
    }

    public long calculateThreadDelay(Time time){
        Calendar rightNow = Calendar.getInstance();

        long MILLISECONDS_IN_AN_HOUR = 1000 * 60 * 60;
        long MILLISECONDS_IN_A_MINUTE = 1000 * 60;

        int toSubtractHour =  Math.abs(time.getHour() - rightNow.get(Calendar.HOUR_OF_DAY));
        int toSubtractMinute =   Math.abs(time.getMinute() - rightNow.get(Calendar.MINUTE));

        return toSubtractHour * MILLISECONDS_IN_AN_HOUR + toSubtractMinute * MILLISECONDS_IN_A_MINUTE;
    }

    @Override
    public int compareTo(Time o) {
        if (this.hour > o.getHour()) {
                return 1;
        }
        else if (this.hour < o.getHour()) {
            return -1;
        }
        else {
            if(this.minute > o.getMinute()){
                return 1;
            }
            else if(this.minute < o.getMinute()){
                return -1;
            }
            return 0;
        }
    }
}
