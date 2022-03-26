import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class ServerThread implements Runnable {
    private List<Integer> numList = new ArrayList<>();//numbers allocated to this thread
    private CountDownLatch downLatch;
    private int threadID = 0;
    private static int amount_in_each_thread; //amount in each thread of first k-1 threads

    public ServerThread(List numList, CountDownLatch downLatch, int threadID) {
        this.numList = numList;
        this.downLatch = downLatch;
        this.threadID = threadID;
    }

    public static int getAmount_in_each_thread() {
        return amount_in_each_thread;
    }

    public static void setAmount_in_each_thread(int amount_in_each_thread) {
        ServerThread.amount_in_each_thread = amount_in_each_thread;
    }

    public void run() {
        ServiceProtocol svp = new ServiceProtocol(threadID);
        svp.processNumber(numList);
        this.downLatch.countDown();
    }
}
