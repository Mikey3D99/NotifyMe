package com.company;

//this class handles multiple clients and their messages

import java.io.*;
import java.net.Socket;
import java.nio.Buffer;
import java.nio.charset.StandardCharsets;

public class ClientSocketHandler extends Thread {

    private final Socket clientSocket;

    //constructor
    ClientSocketHandler(Socket newSocket) {
        this.clientSocket = newSocket;
    }

    private void handleClientSocket() throws IOException, InterruptedException {

        //get input and output from the socket

        OutputStream socketsOutputStream = clientSocket.getOutputStream();
        InputStream socketsInputStream = clientSocket.getInputStream();

        //get input from client
        BufferedReader myReader = new BufferedReader( new InputStreamReader(socketsInputStream));
        String notificationInput;
        String timeInput;

        if((notificationInput = myReader.readLine()) != null){
            String msg = "You typed: " + notificationInput + "\n";
            System.out.println(msg);
            socketsOutputStream.write(msg.getBytes());
        }
        if((timeInput = myReader.readLine()) != null){
            String msg = "You typed: " + timeInput + "\n";
            System.out.println(msg);
        }
        
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
