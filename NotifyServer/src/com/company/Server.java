package com.company;

import java.io.IOException;
import java.io.OutputStream;
import java.net.*;
import java.nio.charset.StandardCharsets;

public class Server {


    public static void main(String[] args)  {
        int port = 7777;
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            while(true){
                Socket clientSocket = serverSocket.accept();
                OutputStream outputStream = clientSocket.getOutputStream();

                outputStream.write("About to connect to the client...".getBytes(StandardCharsets.UTF_8));

                ClientSocketHandler myHandler = new ClientSocketHandler(clientSocket);
                myHandler.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
