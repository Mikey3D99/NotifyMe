package com.company;

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
