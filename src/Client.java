import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Client implements Serializable {
    public List<Integer> numberList = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        Client client = new Client();

        if (args.length != 2) {
            System.err.println("Usage: java HelloClient <host name> <port number>");
            System.exit(1);
        }

        String hostName = args[0];
        int portNumber = Integer.parseInt(args[1]);

        try (Socket myClientSocket = new Socket(hostName, portNumber);
             PrintWriter output = new PrintWriter(myClientSocket.getOutputStream(), true);
             BufferedReader input = new BufferedReader(new InputStreamReader(myClientSocket.getInputStream()));
             ObjectOutputStream oos = new ObjectOutputStream(myClientSocket.getOutputStream()); //used to transmit arraylist
             ObjectInputStream ois = new ObjectInputStream(myClientSocket.getInputStream());
             )
        {
            Scanner scanner = new Scanner(System.in);
            int userInput;
            System.out.println(input.readLine()); // reads the first message from the server and prints it
            System.out.println("Now you can send the integer numbers you want to operate to the server.\nHow many numbers in total? Please enter the amount.");
            while(true) {
                if (scanner.hasNextInt()) {
                    userInput = scanner.nextInt(); // reads user's input
                    //output.println(userInput); //---output
                    break;
                } else {
                    System.out.println("Invalid input. Please enter a correct number.");
                }
            }
            for (int i = 0; i < userInput; i++) {
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
                        userInput = scanner.nextInt(); // reads user's input
                        break;
                    } else {
                        System.out.println("Invalid input. Please enter a correct number.");
                    }
                }
                client.numberList.add(userInput);
            }
            oos.writeObject(client);//---output
            System.out.println(input.readLine()); // reads server's ack and prints it
            System.out.println("-----------End of communication-----------");
            System.out.println("\nCommunication with server " + hostName + " was successful! Now closing...");
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + hostName);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " + hostName);
            e.printStackTrace();
            System.exit(1);
        }

    }
}
