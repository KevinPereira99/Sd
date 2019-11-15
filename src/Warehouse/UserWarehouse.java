package Warehouse;

import java.util.ArrayList;

public class UserWarehouse {
    private int idUser;
    private ArrayList<User> usersList;

    public UserWarehouse() {
        this.idUser = 0;
        this.usersList = new ArrayList<>();
    }

    public ArrayList<User> getUsersList() {
        return usersList;
    }

    public User getUser(int idUser) {
        for(User user: usersList) {
            if(user.getId() == idUser) {
                return user;
            }
        }

        return null;
    }

    public User addUser(String nickname) {
        User user = new User(idUser, nickname);
        usersList.add(user);
        idUser++;

        return user;
    }
}
