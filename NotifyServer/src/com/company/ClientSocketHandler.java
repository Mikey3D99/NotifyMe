package com.company;


import java.io.*;
import java.net.Socket;
import java.util.*;


public class ClientSocketHandler extends Thread {   //this class handles multiple clients and their messages

    private final Socket clientSocket;
    private final TreeMap<Calendar, String> notificationQueue;
    private final DataTimeHandler dataTimeHandler;

    //constructor
    ClientSocketHandler(Socket newSocket) {
        this.clientSocket = newSocket;
        this.notificationQueue = new TreeMap<>();
        this.dataTimeHandler = new DataTimeHandler();
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


            notificationInput = myReader.readLine(); // get the message string
            assert notificationInput != null;

            timeInput = myReader.readLine(); // get the time and date string
            assert timeInput != null;

            //after receiving the time to notify, add the notification to the TreeMap, and check if the order is correct
            try{
                // add key and value to the map
                Calendar timeToNotify = dataTimeHandler.parseToCalendarDate(timeInput);


                System.out.println(timeInput);
                this.notificationQueue.put(timeToNotify, notificationInput);

            }catch(Exception e) {
                throw new IOException("error occurred");
            }

            if((doContinue = myReader.readLine()) != null){
                if(!doContinue.contains("yes")){
                    break;
                }
            }
        }
        sendTheMessageBack(); // send back the notifications ...
        clientSocket.close(); //after doing some operations on the socket, close it...
    }

    private void sendTheMessageBack() throws IOException, InterruptedException {

        //after receiving all of the data, queue it
        for(Map.Entry<Calendar, String> entry : this.notificationQueue.entrySet()){

            Calendar toDelay = entry.getKey();
            String toSend = entry.getValue();

            System.out.println( "Note: " +  entry.getValue() + " Time: "
                    + entry.getKey().getTime() + " Time between now and the time: " + dataTimeHandler.calculateDelay(entry.getKey()));

            // if the difference is negative, just continue, because the time for the notification to be sent has already expired
            try{
                Thread.sleep(dataTimeHandler.calculateDelay(toDelay));
            }catch( IllegalArgumentException e){
                System.out.println("This notification already expired!");
                continue;
            }

            PrintWriter out;
            out = new PrintWriter(this.clientSocket.getOutputStream(), true);
            out.println("Notification: " + toSend);

        }
    }

    //override run method from parent
    @Override
    public void run() {
        try {
            handleClientSocket();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
