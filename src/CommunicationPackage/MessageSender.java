package CommunicationPackage;

import java.io.Serializable;
import java.util.List;

public class MessageSender implements Serializable {
    private String message;
    private double[] arr; //result from server
    private List<Integer> list; //from client
    private int client_choice; //from client
    private float execution_time; //from server

    //client message constructor
    public MessageSender(String message, List<Integer> list, int client_choice){
        this.message = message;
        this.list = list;
        this.client_choice = client_choice;
    }

    //server message constructor
    public MessageSender(String message, double[] arr, float execution_time){
        this.message = message;
        this.arr = arr;
        this.execution_time = execution_time;
    }

    public MessageSender(String message, double[] arr, List<Integer> list, int client_choice, float execution_time) {
        this.message = message;
        this.arr = arr;
        this.list = list;
        this.client_choice = client_choice;
        this.execution_time = execution_time;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public double[] getArr() {
        return arr;
    }

    public void setArr(double[] arr) {
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
