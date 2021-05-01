package com.company;

import java.io.IOException;
import java.io.OutputStream;
import java.net.*;
import java.nio.charset.StandardCharsets;

public class Server {

    private int port;

    Server(int port){
        this.port = port;
    }

    public static void main(String[] args)  {

        Server myServer = new Server(7777);

        try {

            //initiate the server
            ServerSocket serverSocket = new ServerSocket(myServer.port);

            //make the server listen to all clients
            while(true){
                Socket clientSocket = serverSocket.accept();
                ClientSocketHandler myHandler = new ClientSocketHandler(clientSocket);
                myHandler.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
