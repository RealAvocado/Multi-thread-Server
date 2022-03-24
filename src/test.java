import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class test {
    public static void main(String[] args) {
        List<Integer> numberList = new ArrayList<>();
        try (BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));) {
            int userInput;
            for (int i = 0; i < 5; i++) {
                userInput = stdIn.read();
                numberList.add(userInput);
            }
            for (int number : numberList) {
                System.out.println(number);
            }
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection");
            e.printStackTrace();
            System.exit(1);
        }
    }
}
