package Responses;

import Warehouse.Message;
import Warehouse.User;

import java.util.ArrayList;

public class MsgResponse {
    private ArrayList<User> usersList;
    private ArrayList<Message> messagesList;

    public MsgResponse(ArrayList<User> usersList, ArrayList<Message> messagesList) {
        this.usersList = usersList;
        this.messagesList = messagesList;
    }

    public ArrayList<User> getUsersList() {
        return usersList;
    }

    public void setUsersList(ArrayList<User> usersList) {
        this.usersList = usersList;
    }

    public ArrayList<Message> getMessagesList() {
        return messagesList;
    }

    public void setMessagesList(ArrayList<Message> messagesList) {
        this.messagesList = messagesList;
    }
}
