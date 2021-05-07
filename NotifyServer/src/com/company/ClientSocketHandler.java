package com.company;

//this class handles multiple clients and their messages

import java.io.*;
import java.net.Socket;
import java.util.*;

public class ClientSocketHandler extends Thread {

    private final Socket clientSocket;
    private final TreeMap<Calendar, String> notificationQueue;

    //constructor
    ClientSocketHandler(Socket newSocket) {
        this.clientSocket = newSocket;
        this.notificationQueue = new TreeMap<>();
    }

    private void handleClientSocket() throws IOException, InterruptedException, NumberFormatException {

        //get input and output from the socket

        OutputStream socketsOutputStream = clientSocket.getOutputStream();
        InputStream socketsInputStream = clientSocket.getInputStream();

        //get input from client
        BufferedReader myReader = new BufferedReader( new InputStreamReader(socketsInputStream));
        String notificationInput;
        String timeInput;
        String doContinue;

        while(true){

            if((notificationInput = myReader.readLine()) != null){
            }

            //after receiving the time to notify, add the notification to the TreeMap, and check if the order is correct

            if((timeInput = myReader.readLine()) != null){
            }
            assert timeInput != null;
            try{
                // add key and value to the map
                Calendar timeToNotify = parseToCalendarDate(timeInput);


                System.out.println(timeInput);
                this.notificationQueue.put(timeToNotify, notificationInput);

            }catch(Exception e) {
                throw new IOException("blad");
            }

            if((doContinue = myReader.readLine()) != null){
                if(!doContinue.contains("yes")){
                    break;
                }
            }
        }
      //  for(Map.Entry<Calendar, String> entry : this.notificationQueue.entrySet()){
     //       System.out.println( "Note: " +  entry.getValue() + " Time: " + entry.getKey().getTime() + " Time between now and the time: " + calculateDelay(entry.getKey()));
     //   }
        sendTheMessageBack();
        //after doing some operations on the socket, close it...
        clientSocket.close();
    }

    private long calculateDelay(Calendar date){

        Calendar currentDate = Calendar.getInstance();
        return date.getTimeInMillis() - currentDate.getTimeInMillis();
    }

    private void sendTheMessageBack() throws IOException, InterruptedException {

        //after receiving all of the data, queue it
        for(Map.Entry<Calendar, String> entry : this.notificationQueue.entrySet()){

            Calendar toDelay = entry.getKey();
            String toSend = entry.getValue();

            System.out.println( "Note: " +  entry.getValue() + " Time: " + entry.getKey().getTime() + " Time between now and the time: " + calculateDelay(entry.getKey()));

            // if the difference is negative, just continue, because the time for the notification to be sent has already expired
            try{
                Thread.sleep(calculateDelay(toDelay));
            }catch( IllegalArgumentException e){
                System.out.println("This notification already expired!");
                continue;
            }

            PrintWriter out;
            out = new PrintWriter(this.clientSocket.getOutputStream(), true);
            out.println("Notification: " + toSend);

        }
    }


    /*  Timer timer = new Timer();
   TimerTask sendTheMessageBack = new TimerTask() { //anonymous class to depict a task

       @Override
       public void run() {
           try {
               PrintWriter out;
               out = new PrintWriter(clientSocket.getOutputStream(), true);
               out.println(toSend);
           } catch (IOException e) {
               System.out.println("Error while trying to send back the message!");
           }
       }
   };
           //timer.schedule(sendTheMessageBack, toDelay.getTime());


*/
    private Calendar parseToCalendarDate(String dateTime) throws InvalidDateFormatException{
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

    private Calendar setCalendarDate( int year, int month, int day, int hour, int minute) throws InvalidDateFormatException {

        //check if the date is valid, and if not - do not throw further
        try{
            validateDate(year, month, day, hour, minute);

        }catch(InvalidDateFormatException e){
            System.out.println("Invalid date while setting Calendar date!");
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

    //override method from parent
    @Override
    public void run() {
        try {
            handleClientSocket();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
