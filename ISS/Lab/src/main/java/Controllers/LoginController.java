package Controllers;

import Model.Medicament;
import Model.User;
import Service.Service;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class LoginController {
    @FXML
    public TextField emailTxt;
    public PasswordField passTxt;
    public Button LoginBtn;

    @FXML
    public void initialize(){

    }

    public Service service;
    public User user;

    public void setUser(User u){
        user = u;
    }

    public void setService(Service LS){
        service = LS;
    }

    private Stage getNewStage(AnchorPane ap){
        Scene sc = new Scene(ap);
        Stage st = new Stage();
        st.setScene(sc);
        return st;
    }

    public void Login(){
        String e = emailTxt.getText();
        String p = passTxt.getText();
        try{
            User usr = service.Login(e,p);
            if (!usr.getType().equals(this.user.getType())){
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setHeaderText("Accesati terminalul sectiei arondate");
                return;
            }
            Scene sc = new Scene(app);
            Stage st = new Stage();
            abstractController.setUser(user);
            st.setScene(sc);
            if (!usr.getSection().equals("")){
                st.setTitle("Terminalul sectiei de " + usr.getSection());
            }
            else{
                st.setTitle(usr.getType());
            }
            LoginBtn.getScene().getWindow().hide();
            st.show();

        }catch (Exception ex){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Autentificare nereusita");
            alert.show();
        }
    }

    private AnchorPane app;

    public void setApp(AnchorPane app) {
        this.app = app;
    }

    public AnchorPane getApp() {
        return app;
    }

    public AbstractController abstractController;

    public void setControllerApp(AbstractController appCtrl) {
        abstractController = appCtrl;
    }
}
