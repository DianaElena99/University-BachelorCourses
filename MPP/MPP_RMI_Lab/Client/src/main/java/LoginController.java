import Model.User;
import Services.IObserver;
import Services.IService;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class LoginController{
    @FXML
    public TextField user;
    public PasswordField passwd;
    public Button login;

    private MainAppController appCtrl;
    private Parent parent;

    public LoginController(){}

    public IService server;

    @FXML
    public void initialize(){

    }

    public void onLogin(ActionEvent actionEvent) {
        String nume = user.getText();
        String pass = passwd.getText();
        User u = new User(nume, pass);
        try{
            appCtrl.setServer(server);
            server.Login(u, appCtrl);

            Stage st = new Stage();
            st.setTitle("Welcome, " + u.getNume());
            st.setScene(new Scene(parent));

            st.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                    appCtrl.Logout();
                    System.exit(0);
                }
            });

            Stage stg = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
            stg.close();

            Platform.runLater(()->{
                appCtrl.setUser(u);
                appCtrl.initGUI();
                st.show();
            });
        }catch (Exception e){
            e.printStackTrace();
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("Login");
            a.setHeaderText("Autentificare nereusita");
            a.show();
        }
    }


    public void setAppCtrl(MainAppController appCtrl) {
        this.appCtrl = appCtrl;
    }

    public void setParent(Parent appRoot) {
        parent = appRoot;
    }

    public void setServer(IService server) {
        this.server = server;
    }

    public IService getServer() {
        return server;
    }
}