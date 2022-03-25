import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MessageSender implements Serializable {
    public String message;
    public int[] arr = null; //from server
    List <Integer> list = new ArrayList<>(); //from client

    public MessageSender(String message, int[] arr, List<Integer> list){
        this.message = message;
        this.arr = arr;
        this.list=list;
    }
}
