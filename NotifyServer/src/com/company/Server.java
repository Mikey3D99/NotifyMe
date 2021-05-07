package com.company;

import java.io.IOException;
import java.io.OutputStream;
import java.net.*;
import java.nio.charset.StandardCharsets;

public class Server {

    public static void main(String[] args)  {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(7777);
            while(true){
                Socket clientSocket = serverSocket.accept();
                OutputStream outputStream = clientSocket.getOutputStream();

                outputStream.write("About to connect to the client...\n".getBytes(StandardCharsets.UTF_8));

                ClientSocketHandler myHandler = new ClientSocketHandler(clientSocket);
                myHandler.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if(serverSocket != null){
                try{
                    serverSocket.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
