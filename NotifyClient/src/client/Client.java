package client;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;
import java.util.InputMismatchException;

public class Client {


    public static void main(String[] args){

        try{
            Client myClient = new Client();

            Socket clientSocket = new Socket("localhost", 7777);
            System.out.println("Client started");



            int notificationNumber = 0;
            while(true){
                //if the data format is incorrect, type it
                myClient.sendDataToServer(clientSocket, "Enter a notification:\n", false);
                notificationNumber++;
                while(true){
                    try{
                        myClient.sendDataToServer(clientSocket, "Enter time of the notification:\n", true);
                        break;

                    }catch(IOException e){
                        System.out.println("Invalid date format, try again...");
                    }
                }

                if(!myClient.sendDataToServer(clientSocket, "Continue?(yes to continue):\n", false)){
                    break;
                }

            }
            String notification;

            int i = 0;
            while(i < notificationNumber){
                BufferedReader serverOutput = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                //get the message back from the server
                if((notification = serverOutput.readLine()) != null) System.out.println(notification);
                i++;
            }


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private boolean sendDataToServer(Socket clientSocket, String prompt, boolean isDate) throws IOException {

        BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
        String message;
        boolean ret = true;

        if (isDate) {
            //input
            System.out.println(prompt);
            message = userInput.readLine();
            try{
                validateTime(message);
            }
            catch(InputMismatchException e){
                throw new IOException();
            }
        }
        else if(prompt.equals("Continue?(yes to continue):\n")){
            System.out.println(prompt);
            message = userInput.readLine();
            if(!message.equals("yes")){
                ret = false;
            }
        }
        else{
            //input
            System.out.println(prompt);
            message = userInput.readLine();
        }

        //output the message
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        out.println(message);
        return ret;
    }


    //to do : add my custom exception for this
    private void validateTime(String hour) throws InputMismatchException {
        if(!hour.matches("^(0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$")){
            throw new InputMismatchException("Incorrect hour format");
        }
    }

}
