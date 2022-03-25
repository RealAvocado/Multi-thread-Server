import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ServiceProtocol {
    private static int[] resultArray;
    private int threadCount;

    public ServiceProtocol(int threadCount) {
        this.threadCount = threadCount;
    }

    public static int[] getResultArray() {
        return resultArray;
    }

    public static void setResultArray(int[] resultArray) {
        ServiceProtocol.resultArray = resultArray;
    }

    public static void setResultArraySize(int size) {
        int[] arr = new int[size];
        ServiceProtocol.resultArray = arr;
    }

    public void processNumber(List<Integer> input) {
        int headIndex = (threadCount-1) * ServerThread.getAmount_in_each_thread();
        for (int i = 0; i < input.size(); i++) {
            int num = (int) input.get(i);
            int result = num ^ 2;
            resultArray[i + headIndex] = result;
        }
    }
}
