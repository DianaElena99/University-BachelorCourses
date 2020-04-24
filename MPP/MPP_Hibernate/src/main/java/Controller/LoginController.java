package Controller;

import Model.User;
import Service.Service;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {
    public Service service;

    @FXML
    public TextField user;
    public PasswordField passwd;
    public Button login;

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    @FXML
    public void initialize(){

    }

    public void onLogin(ActionEvent actionEvent) throws IOException {
        String usr = user.getText();
        String pass = passwd.getText();
        int id = service.LogIn(new User(usr,pass));
        if (id > 0){
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/View/mainApp.fxml"));
            AnchorPane ap = loader.load();
            MainAppController ctrl = loader.getController();
            ctrl.setService(this.service);
            ctrl.setUser(usr);
            Scene sc = new Scene(ap);
            Stage st = new Stage();
            st.setScene(sc);
            Node nod = (Node) actionEvent.getSource();
            Stage stage = (Stage) nod.getScene().getWindow();
            stage.close();
            st.show();
        }
    }
}
