import java.io.*;
import java.net.*;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * "k-1 threads for first k-1 numbers" version
 */
public class Server {
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

        while(true) {
            try (ServerSocket myServerSocket = new ServerSocket(portNumber);
                 Socket clientSocket = myServerSocket.accept();
                 ObjectOutputStream oos = new ObjectOutputStream(clientSocket.getOutputStream());
                 ObjectInputStream ois = new ObjectInputStream(clientSocket.getInputStream())
            ) {
                System.out.println("\n\nConnection established with a new client with IP address: " + clientSocket.getInetAddress());

                //-----------send the first message to client-------------
                String output = "Server says: Hello Client \" + \". This is server \"" + myServerSocket.getInetAddress() + "\" providing the square operation service. Now I'm ready to receive your numbers.";
                MessageSender messageSender1 = new MessageSender(output, null, null);
                oos.writeObject(messageSender1);

                //-----------receive client's message and numbers for service-------------
                MessageSender messageSender2 = (MessageSender) ois.readObject();//---receive

                System.out.println(messageSender2.getMessage());
                for (int i = 0; i < messageSender2.getList().size(); i++) {
                    System.out.print(messageSender2.getList().get(i) + " ");
                }

                int total_amount = messageSender2.getList().size();
                /*int amount_in_each_thread = (int) Math.floor(total_amount/threadCount);
                ServerThread.setAmount_in_each_thread(amount_in_each_thread);*/
                ServerThread.setAmount_in_each_thread(1);
                ServiceProtocol.setResultArraySize(total_amount);

                //lock used to arrange main thread schedule, main thread would only continue after all sub-threads finished their calculation
                CountDownLatch downLatch;
                if (total_amount>=5) {
                    downLatch = new CountDownLatch(5);
                }else {
                    downLatch = new CountDownLatch(total_amount);
                }

                //start concurrent calculation in multi-threads
                if (total_amount>=5) { //number amount larger than thread amount
                    for (int i = 0; i < threadCount; i++) {
                        List<Integer> numList;
                        if (i != 4) {
                            numList = messageSender2.getList().subList(i, i + 1);
                        } else {
                            numList = messageSender2.getList().subList(4, messageSender2.getList().size());
                        }
                        //construct a new thread to deal with client request
                        int thread_id = i + 1; String id = Integer.toString(thread_id); String thread_name = "thread " + id;
                        ServerThread serverThread = new ServerThread(numList, downLatch, thread_id);
                        new Thread(serverThread, thread_name).start();
                    }
                }else{ //number amount less than thread amount
                    for (int i = 0; i < total_amount; i++) {
                        List<Integer> numList;
                        numList = messageSender2.getList().subList(i, i + 1);
                        //construct a new thread to deal with client request
                        int thread_id = i + 1; String id = Integer.toString(thread_id); String thread_name = "thread " + id;
                        ServerThread serverThread = new ServerThread(numList, downLatch, thread_id);
                        new Thread(serverThread, thread_name).start();
                    }
                }

                //wait until all threads finished
                downLatch.await();

                //-----------send results back to the client-------------
                MessageSender messageSender3 = new MessageSender("Server: Finished. Here's your result:", ServiceProtocol.getResultArray(), null);
                oos.writeObject(messageSender3);

            } catch (IOException e) {
                System.out.println("Exception caught when trying to listen on port " + portNumber + " or listening for a connection");
                System.out.println(e.getMessage());
            } catch (ClassNotFoundException e) {
                System.out.println("Exception caught when class not found.");
                System.out.println(e.getMessage());
            } catch (InterruptedException e) {
                System.out.println("Exception caught when interrupted exception.");
                System.out.println(e.getMessage());
            }
        }
    }
}
