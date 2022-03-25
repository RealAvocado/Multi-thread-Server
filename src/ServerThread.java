import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ServerThread extends Thread {
    private Socket clientSocket;
    private ServerSocket serverSocket;
    private List<Integer> numList = new ArrayList<>(); //numbers allocated to this thread
    private static int threadCount = 0;
    private static int amount_in_each_thread; //amount in each thread of first k-1 threads

    public ServerThread(Socket clientSocket, ServerSocket serverSocket, List numList) {
        this.clientSocket = clientSocket;
        this.serverSocket = serverSocket;
        this.numList = numList;
    }

    public static int getAmount_in_each_thread() {
        return amount_in_each_thread;
    }

    public static void setAmount_in_each_thread(int amount_in_each_thread) {
        ServerThread.amount_in_each_thread = amount_in_each_thread;
    }

    public void run(){
        threadCount++;
        try (
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                ObjectOutputStream oos = new ObjectOutputStream(clientSocket.getOutputStream());
                ObjectInputStream ois = new ObjectInputStream(clientSocket.getInputStream())
        ) {
            ServiceProtocol svp = new ServiceProtocol(threadCount);
            svp.processNumber(numList);
            //finish processing. transmitting data back to client
            if (threadCount == Server.threadCount){
                out.println("Your numbers have been processed. Sending back to you now...");//---output
                Server server = new Server();
                server.resultArray = ServiceProtocol.getResultArray();
                oos.writeObject(server);//---output
                oos.flush();
                oos.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
