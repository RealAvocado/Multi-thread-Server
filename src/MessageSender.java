import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MessageSender implements Serializable {
    private String message;
    private int[] arr; //from server
    private List<Integer> list; //from client

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int[] getArr() {
        return arr;
    }

    public void setArr(int[] arr) {
        this.arr = arr;
    }

    public List<Integer> getList() {
        return list;
    }

    public void setList(List<Integer> list) {
        this.list = list;
    }

    public MessageSender(String message, int[] arr, List<Integer> list) {
        this.message = message;
        this.arr = arr;
        this.list = list;
    }
}
