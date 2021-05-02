package client;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;

public class Client {


    public static void main(String[] args){

        try{
            int port = 7777;
            Socket clientSocket = new Socket("localhost", port);
            System.out.println("Client started");

            BufferedReader userNotification = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Enter a notification:\n");
            String message = userNotification.readLine();

            BufferedReader userTime = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Enter time:\n");
            String timeToNotify = userTime.readLine();


            PrintWriter outMessage = new PrintWriter(clientSocket.getOutputStream(), true);
            outMessage.println(message);

            PrintWriter outTime = new PrintWriter(clientSocket.getOutputStream(), true);
            outTime.println(timeToNotify);

            BufferedReader serverOutput = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            System.out.println(serverOutput.readLine());

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
