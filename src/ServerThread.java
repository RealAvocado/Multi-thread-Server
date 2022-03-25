import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class ServerThread extends Thread {
    private List<Integer> numList = new ArrayList<>();//numbers allocated to this thread
    private CountDownLatch downLatch;
    private static int threadCount = 0;
    private static int amount_in_each_thread; //amount in each thread of first k-1 threads

    public ServerThread(List numList, CountDownLatch downLatch) {
        this.numList = numList;
        this.downLatch = downLatch;
    }

    public static int getAmount_in_each_thread() {
        return amount_in_each_thread;
    }

    public static void setAmount_in_each_thread(int amount_in_each_thread) {
        ServerThread.amount_in_each_thread = amount_in_each_thread;
    }

    public void run() {
        threadCount++;
        ServiceProtocol svp = new ServiceProtocol(threadCount);
        svp.processNumber(numList);
        this.downLatch.countDown();
    }
}
