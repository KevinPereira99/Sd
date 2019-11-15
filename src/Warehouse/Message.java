package Warehouse;

public class Message {
    private User user;
    private String msg;

    public Message(User user, String msg) {
        this.user = user;
        this.msg = msg;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
