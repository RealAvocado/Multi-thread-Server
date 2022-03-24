import java.util.ArrayList;
import java.util.List;

public class ServiceProtocol {
    private static List<Integer> numberList = new ArrayList<>();
    private static List<Integer> resultList = new ArrayList<>();
    private int threadCount;

    public ServiceProtocol(int threadCount) {
        this.threadCount = threadCount;
    }

    public static List<Integer> getNumberList() {
        return numberList;
    }

    public static void setNumberList(List<Integer> numberList) {
        ServiceProtocol.numberList = numberList;
    }

    public List<Integer> getResultList() {
        return resultList;
    }

    public static void setResultList(List<Integer> resultList) {
        ServiceProtocol.resultList = resultList;
    }

    public void processNumber(List<Integer> input) {
        for (int num : input) {
            int result = num^2;
        }
    }
}
