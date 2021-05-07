package com.company;

import java.util.Calendar;

public class DataTimeHandler {

    public long calculateDelay(Calendar date){

        Calendar currentDate = Calendar.getInstance();
        return date.getTimeInMillis() - currentDate.getTimeInMillis();
    }

    public Calendar parseToCalendarDate(String dateTime) throws InvalidDateFormatException{
        int year;
        int month;
        int day;
        int hour;
        int minute;

        // date and time format: 2021-12-1 14:14
        String[] result = dateTime.split(" "); //divide into date and time

        String date = result[0];
        String time = result[1];

        String[] dateData = date.split("-");
        String[] timeData = time.split(":");


        try {
            // parse date to int
            year = Integer.parseInt(dateData[0]);
            month = Integer.parseInt(dateData[1]);
            day = Integer.parseInt(dateData[2]);

            // parse time to int
            hour = Integer.parseInt(timeData[0]);
            minute = Integer.parseInt(timeData[1]);
        }catch(NumberFormatException e){
            throw new InvalidDateFormatException("Error while parsing integers!");
        }

        return setCalendarDate(year, month, day, hour, minute);
    }

    public Calendar setCalendarDate(int year, int month, int day, int hour, int minute) throws InvalidDateFormatException {

        //check if the date is valid, and if not - do not throw further
        try{
            validateDate(year, month, day, hour, minute);

        }catch(InvalidDateFormatException e){
            System.out.println("Invalid date while setting Calendar date!");
            throw new InvalidDateFormatException("setCalendarDate - err");
        }

        Calendar date = Calendar.getInstance();
        date.set(year, month - 1, day, hour, minute, 0); //month - 1 because index starts at 0, months at 1

        return date;
    }

    private void validateDate(int year, int month, int day, int hour, int minute) throws InvalidDateFormatException{

        if(year < 0 || year > 3000
                || month > 12 || month < 1
                || day > 31 || day < 1
                || hour > 23 || hour < 0
                || minute > 60 || minute < 0){
            throw new InvalidDateFormatException("Date out of bounds!");
        }

        boolean isLeapYear = (year % 2 == 0 && year % 100 == 0 && year % 400 == 0);

        //check leap year
        if( isLeapYear && month == 2 && day > 29){
            throw new InvalidDateFormatException("Leap year error!");
        }

        //check non-leap year
        if( !isLeapYear && month == 2 && day > 28){
            throw new InvalidDateFormatException("Non-leap year error!");
        }

    }
}
