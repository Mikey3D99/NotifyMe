package com.company;

//this class handles multiple clients and their messages

import java.io.*;
import java.net.Socket;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.TreeMap;

public class ClientSocketHandler extends Thread {

    private final Socket clientSocket;
    private final TreeMap<Time, String> notificationQueue;

    //constructor
    ClientSocketHandler(Socket newSocket) {
        this.clientSocket = newSocket;
        this.notificationQueue = new TreeMap<>();
    }

    private Time convertToTime(String time) throws Exception {
        int hours;
        int minutes;

        String[] result = time.split(":");
        hours = Integer.parseInt(result[0]);
        minutes = Integer.parseInt(result[1]);


        return new Time(hours, minutes);

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
                String msg = "Notification: " + notificationInput + "\n";
                System.out.println(msg);
                socketsOutputStream.write(msg.getBytes());
            }

            //after receiving the time to notify, add the notification to the TreeMap, and check if the order is correct

            if((timeInput = myReader.readLine()) != null){
                String msg = "You typed: " + timeInput + "\n";
                System.out.println(msg);
            }
            assert timeInput != null;
            try{
                // add key and value to the map
                Time timeToNotify = convertToTime(timeInput);
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

        /*for(Map.Entry<Time, String> entry : this.notificationQueue.entrySet()){
            System.out.println("Key: " + entry.getKey() + ". Value: " + entry.getValue());
        }*/

        //after doing some operations on the socket, close it...
        clientSocket.close();
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
