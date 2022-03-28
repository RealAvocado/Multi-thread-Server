import java.math.BigDecimal;
import java.util.List;

public class ServiceProtocol {
    private static double[] resultArray;
    private int threadCount;

    public static double[] getResultArray() {
        return resultArray;
    }

    public static void setResultArray(double[] resultArray) {
        ServiceProtocol.resultArray = resultArray;
    }

    public static void setResultArraySize(int size) {
        ServiceProtocol.resultArray = new double[size];
    }

    public void processNumberSquare(List<Integer> input, int result_insert_index) {
        for (int i = 0; i < input.size(); i++) {
            int num = input.get(i);
            int result = num * num;
            resultArray[i + result_insert_index] = result;
        }
    }

    public void processNumberLog(List<Integer> input, int result_insert_index) {
        for (int i = 0; i < input.size(); i++) {
            int num = input.get(i);
            double result = Math.log10(num);
            BigDecimal b = new BigDecimal(result);
            double result_two_bits_decimal = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            resultArray[i + result_insert_index] = result_two_bits_decimal;
        }
    }

    public void processNumberRoot(List<Integer> input, int result_insert_index) {
        for (int i = 0; i < input.size(); i++) {
            int num = input.get(i);
            double result = Math.sqrt(num);
            BigDecimal b = new BigDecimal(result);
            double result_two_bits_decimal = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            resultArray[i + result_insert_index] = result_two_bits_decimal;
        }
    }

}
