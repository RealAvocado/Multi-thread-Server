import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ServiceProtocol {
    //private static List<Integer> numberList = new ArrayList<>();
    //private static List<Integer> resultList = new ArrayList<>();
    private static int[] resultArray;
    //private List<Integer> processList = new ArrayList<>(); //numbers to be processed in the corresponding thread
    private int threadCount;

    public ServiceProtocol(int threadCount) {
        this.threadCount = threadCount;
    }

    /*public static List<Integer> getNumberList() {
        return numberList;
    }

    public static void setNumberList(List<Integer> numberList) {
        ServiceProtocol.numberList = numberList;
    }*/

    /*public static List<Integer> getResultList() {
        return resultList;
    }

    public static void setResultList(List<Integer> resultList) {
        ServiceProtocol.resultList = resultList;
    }*/

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
        int headIndex = threadCount * ServerThread.getAmount_in_each_thread();
        for (int i = 0; i < input.size(); i++) {
            int num = (int) input.get(i);
            int result = num ^ 2;
            resultArray[i + headIndex] = result;
        }
    }
}
