package Warehouse;

import java.util.ArrayList;

public class MessageWarehouse {
    private ArrayList<Message> messagesList;

    public MessageWarehouse() {
        this.messagesList = new ArrayList<>();
    }

    public ArrayList<Message> getMessagesList() {
        return messagesList;
    }

    public Message addMessage(User user, String msg) {
        Message message = new Message(user, msg);
        messagesList.add(message);

        return message;
    }
}
