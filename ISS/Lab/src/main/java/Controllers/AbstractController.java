package Controllers;

import Model.User;
import Service.Service;
import javafx.fxml.FXML;

public abstract class AbstractController {
    private Service service;
    public User user;
    public abstract void setService(Service ser);
    @FXML
    public void initialize(){

    }

    public void setUser(User u) {
        user = u;
    }

    public User getUser() {
        return user;
    }
}
