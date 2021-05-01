package com.company;

//this class handles multiple clients and their messages

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

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

        //do stuff
        socketsOutputStream.write("Test".getBytes());
        Thread.sleep(2000);
        socketsOutputStream.write("chuj".getBytes());

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
