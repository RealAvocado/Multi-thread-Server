import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MessageSender implements Serializable {
    private String message;
    private int[] arr; //from server
    private List<Integer> list; //from client
    private int client_choice; //from client
    private float execution_time; //from server

    public MessageSender(String message, int[] arr, List<Integer> list, int client_choice, float execution_time) {
        this.message = message;
        this.arr = arr;
        this.list = list;
        this.client_choice = client_choice;
        this.execution_time = execution_time;
    }

    //client message constructor
    public MessageSender(String message, List<Integer> list, int client_choice){
        this.message = message;
        this.list = list;
        this.client_choice = client_choice;
    }

    //server message constructor
    public MessageSender(String message, int[] arr, float execution_time){
        this.message = message;
        this.arr = arr;
        this.execution_time = execution_time;
    }

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

    public int getClient_choice() {
        return client_choice;
    }

    public void setClient_choice(int client_choice) {
        this.client_choice = client_choice;
    }

    public float getExecution_time() {
        return execution_time;
    }

    public void setExecution_time(float execution_time) {
        this.execution_time = execution_time;
    }
}
