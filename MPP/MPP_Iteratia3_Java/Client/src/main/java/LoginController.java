import Model.User;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class LoginController {
    @FXML
    public TextField user;

    @FXML
    public PasswordField passwd;

    @FXML
    public Button login;

    @FXML
    public void initialize(){}

    private IService service;
    private AppController appCtrl;
    private Parent parent;

    public void setAppCtrl(AppController appCtrl) {
        this.appCtrl = appCtrl;
    }

    public void setServer(IService server) {
        service = server;
    }

    public void setParent(Parent root) {
        parent = root;
    }

    public void onLogin(ActionEvent actionEvent) {
        String nume = user.getText();
        String pass = passwd.getText();
        User u = new User(nume, pass);
        try{
            service.login(u, appCtrl);
            Stage st = new Stage();
            st.setTitle("Welcome, " + u.getNume());
            st.setScene(new Scene(parent));

            st.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                    appCtrl.logout();
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
}
