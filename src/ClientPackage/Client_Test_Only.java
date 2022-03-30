package ClientPackage;

import CommunicationPackage.MessageSender;

import java.io.*;
import java.net.*;
import java.util.*;

/**
 * A test-version client: for data gathering
 * generate large amount of numbers at one time
 */
public class Client_Test_Only {
    public static List<Integer> numberList = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        if (args.length != 2) {
            System.err.println("Usage: java HelloClient <host name> <port number>");
            System.exit(1);
        }
        String hostName = args[0];
        int portNumber = Integer.parseInt(args[1]);

        try (Socket myClientSocket = new Socket(hostName,portNumber);
             ObjectOutputStream oos = new ObjectOutputStream(myClientSocket.getOutputStream()); //used to transmit arraylist and messages to server
             ObjectInputStream ois = new ObjectInputStream(myClientSocket.getInputStream()); //used to receive result array and messages from server
        )
        {
            //------------ reads the first message from the server and prints it (hello) --------------
            MessageSender messageSender1 = (MessageSender) ois.readObject();
            System.out.println("\n"+messageSender1.getMessage());
            System.out.println();

            System.out.println("Hint: system will randomly generate numbers for you.\nHow many numbers in total? Please enter the amount:");

            int num_amount;
            int client_choice;

            while(true) {
                Scanner scanner = new Scanner(System.in);
                if (scanner.hasNextInt()) {
                    num_amount = scanner.nextInt(); // reads user's input
                    if (num_amount > 0) {
                        break;
                    } else {
                        System.out.println("Invalid input. Please enter a positive number.");
                    }
                } else {
                    System.out.println("Invalid input. Please enter a correct number.");
                }
            }

            Random r = new Random();
            for (int i = 0; i < num_amount; i++) {
                int num = r.nextInt(999)+1;
                Client.numberList.add(num);
            }

            System.out.println("\nNow please select a type of number operation service (enter the corresponding choice number provided by the server at the beginning)");
            while(true) {
                Scanner scanner = new Scanner(System.in);
                if (scanner.hasNextInt()) {
                    client_choice = scanner.nextInt(); // reads user's input
                    if (client_choice > 0 && client_choice < 4) {
                        break;
                    } else {
                        System.out.println("Invalid input. Please enter a correct choice.");
                    }
                } else {
                    System.out.println("Invalid input. Please enter a correct choice.");
                }
            }

            //-----------send numbers to server-------------
            MessageSender messageSender2 = new MessageSender("Client: Numbers have reached, waiting to be calculated:", Client.numberList,client_choice);
            oos.writeObject(messageSender2);//---output

            System.out.println("\nYour numbers are " + Client.numberList);
            System.out.print("\n----------------------------------------\nLoading the processed result from server");
            for (int i = 0; i < 6; i++) {
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.print(".");
                if (i == 5){
                    System.out.println("\n");
                }
            }

            //-----------receive results from server-------------
            MessageSender messageSender3 = (MessageSender) ois.readObject();
            System.out.println("\n" + messageSender3.getMessage());
            System.out.println("\nThe RESULT is: " + Arrays.toString(messageSender3.getArr()));
            System.out.println("\nExecution time: " + messageSender3.getExecution_time() + " seconds");

            System.out.println("\nCommunication with server " + hostName + " was successful! Now closing...");
            System.out.println("\n-----------End of communication-----------");
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + hostName);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " + hostName);
            e.printStackTrace();
            System.exit(1);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
