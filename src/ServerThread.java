import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class ServerThread implements Runnable {
    private List<Integer> numList = new ArrayList<>();//numbers allocated to this thread
    private CountDownLatch downLatch;
    private int threadID = 0;
    private int client_choice = 0;
    private static int amount_in_each_thread; //amount in each thread of first k-1 threads

    public ServerThread(List numList, CountDownLatch downLatch, int threadID, int client_choice) {
        this.numList = numList;
        this.downLatch = downLatch;
        this.threadID = threadID;
        this.client_choice = client_choice;
    }

    public static int getAmount_in_each_thread() {
        return amount_in_each_thread;
    }

    public static void setAmount_in_each_thread(int amount_in_each_thread) {
        ServerThread.amount_in_each_thread = amount_in_each_thread;
    }

    public void getCurrentIndex(){

    }

    public void run() {
        ServiceProtocol svp = new ServiceProtocol(threadID);
        switch (client_choice){
            case 1:
                svp.processNumberSquare(numList);
                break;
            case 2:
                svp.processNumberLog(numList);
                break;
            case 3:
                svp.processNumberRoot(numList);
                break;
            default:
                break;
        }
        this.downLatch.countDown();
    }
}
