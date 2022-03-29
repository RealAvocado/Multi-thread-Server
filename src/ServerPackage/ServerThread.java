package ServerPackage;

import CommunicationPackage.ServiceProtocol;

import java.util.List;
import java.util.concurrent.CountDownLatch;

public class ServerThread implements Runnable {
    private List<Integer> numList;//numbers allocated to this thread
    private CountDownLatch downLatch;
    private int threadID = 0;
    private int client_choice = 0;
    private int result_insert_index = 0;
    private static int amount_in_each_thread; //amount in each thread of first k-1 threads

    public ServerThread(List<Integer> numList, CountDownLatch downLatch, int threadID, int client_choice, int result_insert_index) {
        this.numList = numList;
        this.downLatch = downLatch;
        this.threadID = threadID;
        this.client_choice = client_choice;
        this.result_insert_index = result_insert_index;
    }

    public static int getAmount_in_each_thread() {
        return amount_in_each_thread;
    }

    public static void setAmount_in_each_thread(int amount_in_each_thread) {
        ServerThread.amount_in_each_thread = amount_in_each_thread;
    }

    public void run() {
        System.out.println(Thread.currentThread().getName() + " is processing " + numList + "...");
        ServiceProtocol svp = new ServiceProtocol();
        switch (client_choice) {
            case 1:
                svp.processNumberSquare(numList, result_insert_index);
                break;
            case 2:
                svp.processNumberLog(numList, result_insert_index);
                break;
            case 3:
                svp.processNumberRoot(numList, result_insert_index);
                break;
            default:
                break;
        }
        this.downLatch.countDown();
    }
}
