package ServerPackage;

import CommunicationPackage.MessageSender;
import CommunicationPackage.ServiceProtocol;

import java.io.*;
import java.net.*;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * "uniformly dividing sequence" version
 */
public class Server_UniformDivision {
    public final static int threadCount = 10;

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

        System.out.println("\nListening for any client...");

        while(true) {
            try (ServerSocket myServerSocket = new ServerSocket(portNumber);
                 Socket clientSocket = myServerSocket.accept();
                 ObjectOutputStream oos = new ObjectOutputStream(clientSocket.getOutputStream());
                 ObjectInputStream ois = new ObjectInputStream(clientSocket.getInputStream())
            ) {
                //confirm connection
                System.out.println("\n-----------a new client communication-----------");
                System.out.println("\nConnection established with a new client with IP address: " + clientSocket.getInetAddress() + "\n");
                //-----------send the first message to client-------------
                String output = "Server: Hello Client. This is server \"" + myServerSocket.getInetAddress() + "\" providing the number operation service. \n\nThe available operations are: \n1.Square operation\n2.Logarithm operation (10 being base number)\n3.Root operation\n\nServer: Now I'm ready to receive your numbers.";
                MessageSender messageSender1 = new MessageSender(output, (double[]) null,0);
                oos.writeObject(messageSender1);

                //-----------receive client's message and numbers for service-------------
                MessageSender messageSender2 = (MessageSender) ois.readObject();//---receive
                long start_time = System.currentTimeMillis(); //server's processing start time, start here
                int client_choice = messageSender2.getClient_choice(); //receive operation service chosen by client
                System.out.println(messageSender2.getMessage()); //clients received from the client
                System.out.println(messageSender2.getList()+"\n"); //numbers received from the client

                //------------allocate amount in each thread-------------
                int total_amount = messageSender2.getList().size();
                ServiceProtocol.setResultArraySize(total_amount);
                int amount_in_each_thread = (int) Math.floor(total_amount/threadCount); //uniformly divide, average amount in each thread (floor)
                ServerThread.setAmount_in_each_thread(amount_in_each_thread);
                int overflow_thread_amount = total_amount - threadCount * amount_in_each_thread; //impossible to divide perfectly. so some threads have to take one more number

                //lock used to arrange main thread schedule, main thread would only continue after all sub-threads finished their calculation
                CountDownLatch downLatch;
                if (total_amount >= threadCount) {
                    downLatch = new CountDownLatch(threadCount);
                }else {
                    downLatch = new CountDownLatch(total_amount);
                }

                //start concurrent calculation in multi-threads
                if (total_amount >= threadCount) { //number amount larger than thread amount
                    int result_insert_index = 0; //index of inserting the result in the result array for each thread
                    for (int i = 0; i < threadCount; i++) {
                        List<Integer> numList; //numbers allocated to each thread
                        if (i < overflow_thread_amount) {
                            numList = messageSender2.getList().subList(result_insert_index, result_insert_index + ServerThread.getAmount_in_each_thread()+1);
                        } else {
                            numList = messageSender2.getList().subList(result_insert_index, result_insert_index + ServerThread.getAmount_in_each_thread());
                        }
                        //construct a new thread to deal with client request
                        int thread_id = i + 1; String id = Integer.toString(thread_id); String thread_name = "sub-thread " + id;
                        ServerThread serverThread = new ServerThread(numList, downLatch, thread_id, client_choice, result_insert_index);
                        new Thread(serverThread, thread_name).start();
                        result_insert_index = result_insert_index + numList.size();//update the insert index for the next thread
                    }
                }else{ //number amount less than thread amount
                    for (int i = 0; i < total_amount; i++) {
                        List<Integer> numList;
                        numList = messageSender2.getList().subList(i, i + 1);
                        //construct a new thread to deal with client request
                        int thread_id = i + 1; String id = Integer.toString(thread_id); String thread_name = "sub-thread " + id;
                        ServerThread serverThread = new ServerThread(numList, downLatch, thread_id, client_choice, thread_id-1);
                        new Thread(serverThread, thread_name).start();
                    }
                }

                //wait until all threads finish
                downLatch.await();
                //calculate server execution time
                long end_time = System.currentTimeMillis();
                float execution_time = (float)(end_time-start_time)/1000;
                //-----------send results back to the client-------------
                MessageSender messageSender3 = new MessageSender("Server: Finished. Here's your result:", ServiceProtocol.getResultArray(), execution_time);
                oos.writeObject(messageSender3);

                System.out.println("\nCommunication with client " + clientSocket.getInetAddress() + " has ended");
                System.out.println("\nExecution time: " + execution_time + " seconds");
                System.out.println("\n-----------End of communication-----------");
                System.out.println("\nListening for any client...\n");
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
