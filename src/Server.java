import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

/**
 * "k-1 threads for first k-1 numbers" version
 */
public class Server {
    public static void main(String[] args) throws IOException {
        int portNumber;
        //boolean accepting = true;
        int threadCount = 5;

        if (args.length < 1) {
            System.out.println("Warning: You have provided no arguments\nTrying to connect to the default port 8000...");
            portNumber = 8000;
        } else if (args.length == 1) {
            portNumber = Integer.parseInt(args[0]);
        } else {
            System.out.println("Warning: You have provided > 1 arguments\nTrying with the first argument to connect to a port...");
            portNumber = Integer.parseInt(args[0]);
        }

        while (true) { //in order to serve multiple clients but sequentially, one after the other
            try (ServerSocket myServerSocket = new ServerSocket(portNumber);
                 Socket clientSocket = myServerSocket.accept();
                 PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                 BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                 ObjectInputStream ois = new ObjectInputStream(clientSocket.getInputStream());
                 ) {
                System.out.println("Connection established with a new client with IP address: " + clientSocket.getInetAddress() + "\n");
                out.println("Server says: Hello Client " + ". This is server " + myServerSocket.getInetAddress() + " providing the operation service.\nNow I'm ready to receive your numbers.");

                Client client = (Client) ois.readObject();
                ServiceProtocol.setNumberList(client.numberList);

                int amount = client.numberList.size();

                for (int i = 0; i < threadCount; i++) {
                    List<Integer> numList;
                    if (i!=4) {
                        numList = client.numberList.subList(i, i + 1);
                    }else {
                        numList = client.numberList.subList(4,client.numberList.size());
                    }
                    //construct a new thread to deal with client request
                    new ServerThread(myServerSocket.accept(), myServerSocket,numList).start();
                }
            } catch(IOException | ClassNotFoundException e){
                System.out.println("Exception caught when trying to listen on port " + portNumber + " or listening for a connection, or class not found");
                System.out.println(e.getMessage());
            }
        }
    }
}
