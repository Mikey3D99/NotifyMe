package client;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;


public class Client {


    public static void main(String[] args){

        try{
            Client myClient = new Client();

            Socket clientSocket = new Socket("localhost", 7777);
            System.out.println("Client started");

            int numberOfNotifications = myClient.sendNotifications(myClient,  clientSocket); // send notifications

            myClient.receiveNotifications(numberOfNotifications, clientSocket); // receive them with desired delay

        }
        catch(ConnectException e){
            System.out.println("Server is not running! ");
        }
        catch (IOException e) {
            System.out.println("Something wnet terribly wrong!");
            e.printStackTrace();
        }

    }

    private int sendNotifications( Client myClient, Socket clientSocket) throws IOException {

        int numberOfNotifications = 0;

        while(true){ // send notifications until

            myClient.sendDataToServer(clientSocket, "Enter a notification:\n", false); //if the data format is incorrect, try it again
            numberOfNotifications++;

            while(true){
                try{
                    myClient.sendDataToServer(clientSocket, "Enter the time of the notification:\n", true);
                    break;

                }catch(IOException e){
                    System.out.println("Invalid date format, try again...");
                }
            }

            if(!myClient.sendDataToServer(clientSocket, "Continue?(yes to continue):\n", false)){
                break;
            }

        }
        return numberOfNotifications;

    }

    private void receiveNotifications(int numberOfNotifications, Socket clientSocket ) throws IOException {
        String notification;
        int stop = 0;

        while(true) {
            BufferedReader serverOutput = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            if((notification = serverOutput.readLine()) != null) //get the message back from the server
                System.out.println(notification);

            if(stop == numberOfNotifications)
                break;
            stop++;
        }
    }

    private boolean sendDataToServer(Socket clientSocket, String prompt, boolean isDate) throws IOException {

        BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
        String message;
        boolean ret = true;

        if (isDate) {
            System.out.println(prompt); //input
            message = userInput.readLine();
            try{
                validateTime(message);
            }
            catch(InvalidDateException e){
                throw new IOException("Could not validate the time passed by user");
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
            System.out.println(prompt); //input
            message = userInput.readLine();
        }

        //output the message
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        out.println(message);
        return ret;
    }


    //example correct format: 2020-12-1 14:14
    private void validateTime(String dateTime) throws InvalidDateException {
        try{
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            format.parse(dateTime);
        }catch (ParseException e){
            throw new InvalidDateException("Incorrect date format from Client side!");
        }
    }

}
