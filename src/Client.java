import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Client implements Serializable {
    public static List<Integer> numberList = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        if (args.length != 2) {
            System.err.println("Usage: java HelloClient <host name> <port number>");
            System.exit(1);
        }

        String hostName = args[0];
        int portNumber = Integer.parseInt(args[1]);

        try (Socket myClientSocket = new Socket(hostName, portNumber);
             ObjectOutputStream oos = new ObjectOutputStream(myClientSocket.getOutputStream()); //used to transmit arraylist
             ObjectInputStream ois = new ObjectInputStream(myClientSocket.getInputStream())
             )
        {
            //System.out.println(input.readLine()); //--- reads the first message from the server and prints it (hello)
            MessageSender messageSender1 = (MessageSender) ois.readObject();
            System.out.println(messageSender1.message);
            System.out.println();

            System.out.println("Now you can send the integer numbers you want to operate to the server.\nHow many numbers in total? Please enter the amount.");
            Scanner scanner = new Scanner(System.in);
            int num_amount;
            int num_input;
            while(true) {
                if (scanner.hasNextInt()) {
                    num_amount = scanner.nextInt(); // reads user's input
                    break;
                } else {
                    System.out.println("Invalid input. Please enter a correct number.");
                }
            }
            for (int i = 0; i < num_amount; i++) {
                switch (i){
                    case 0:
                        System.out.println("Enter the first number:");
                        break;
                    case 1:
                        System.out.println("Enter the second number:");
                        break;
                    case 2:
                        System.out.println("Enter the third number:");
                        break;
                    default:
                        System.out.println("Enter the " + (i+1) + "th number:");
                        break;
                }
                while(true) {
                    if (scanner.hasNextInt()) {
                        num_input = scanner.nextInt(); // reads user's input
                        break;
                    } else {
                        System.out.println("Invalid input. Please enter a correct number.");
                    }
                }
                Client.numberList.add(num_input);
            }
            MessageSender messageSender2 = new MessageSender("Client: Numbers have reached, waiting to be calculated:", null, Client.numberList);
            oos.writeObject(messageSender2);//---output

            MessageSender messageSender3 = (MessageSender) ois.readObject();
            System.out.println(messageSender3.message);
            System.out.println("The result is:");
            for (int num:messageSender3.arr) {
                System.out.print(num + " ");
            }
            System.out.println("\n-----------End of communication-----------");
            System.out.println("\nCommunication with server " + hostName + " was successful! Now closing...");
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
