import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ServerThread extends Thread {
    private Socket clientSocket;
    private ServerSocket serverSocket;
    private List<Integer> numList = new ArrayList<>(); //numbers allocated to this thread
    private static int threadCount;

    public ServerThread(Socket clientSocket, ServerSocket serverSocket, List numList) {
        this.clientSocket = clientSocket;
        this.serverSocket = serverSocket;
        this.numList = numList;
    }

    public void run(){
        threadCount++;
        try (
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        ) {
            int inputLine;
            ServiceProtocol svp = new ServiceProtocol(threadCount);
           /* while ((inputLine = in.readLine()) != null) {
                outputLine = kkp.processInput(inputLine);
                out.println(outputLine);
                if (outputLine.equals("Bye"))
                     break;
            }*/
            //clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
