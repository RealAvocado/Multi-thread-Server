import java.io.*;
import java.net.*;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * "k-1 threads for first k-1 numbers" version
 */
public class Server implements Serializable {
    public static int threadCount = 5;

    public static void main(String[] args) throws IOException {
        int portNumber;
        if (args.length < 1) {
            System.out.println("Warning: You have provided no arguments\nTrying to connect to the default port 8000...");
            portNumber = 8000;
        } else if (args.length == 1) {
            portNumber = Integer.parseInt(args[0]);
        } else {
            System.out.println("Warning: You have provided > 1 arguments\nTrying with the first argument to connect to a port...");
            portNumber = Integer.parseInt(args[0]);
        }

        CountDownLatch downLatch = new CountDownLatch(5);
        while(true) {
            try (ServerSocket myServerSocket = new ServerSocket(portNumber); Socket clientSocket = myServerSocket.accept(); ObjectOutputStream oos = new ObjectOutputStream(clientSocket.getOutputStream()); ObjectInputStream ois = new ObjectInputStream(clientSocket.getInputStream())) {
                System.out.println("Connection established with a new client with IP address: " + clientSocket.getInetAddress() + "\n");
                String output = "Server says: Hello Client \" + \". This is server \"" + myServerSocket.getInetAddress() + "\" providing the square operation service. Now I'm ready to receive your numbers.";
                MessageSender messageSender1 = new MessageSender(output, null, null);
                oos.writeObject(messageSender1);

                MessageSender messageSender2 = (MessageSender) ois.readObject();//---receive
                System.out.println(messageSender2.message);
                for (int i = 0; i < messageSender2.list.size(); i++) {
                    System.out.print(messageSender2.list.get(i) + " ");
                }

                int total_amount = messageSender2.list.size();
                /*int amount_in_each_thread = (int) Math.floor(total_amount/threadCount);
                ServerThread.setAmount_in_each_thread(amount_in_each_thread);*/
                ServerThread.setAmount_in_each_thread(1);
                ServiceProtocol.setResultArraySize(total_amount);

                for (int i = 0; i < threadCount; i++) {
                    List<Integer> numList;
                    if (i != 4) {
                        numList = messageSender2.list.subList(i, i + 1);
                    } else {
                        numList = messageSender2.list.subList(4, messageSender2.list.size());
                    }
                    //construct a new thread to deal with client request
                    new ServerThread(numList, downLatch).start();
                }

                downLatch.await();
                MessageSender messageSender3 = new MessageSender("Server: Finished. Here's your result:", ServiceProtocol.getResultArray(), null);
                oos.writeObject(messageSender3);
            } catch (IOException | ClassNotFoundException | InterruptedException e) {
                System.out.println("Exception caught when trying to listen on port " + portNumber + " or listening for a connection, or class not found or interrupted exception.");
                System.out.println(e.getMessage());
            }
        }
    }
}
