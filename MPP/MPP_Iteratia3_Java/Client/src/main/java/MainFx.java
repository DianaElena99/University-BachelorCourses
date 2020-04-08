import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class MainFx extends Application {

    private Stage stage;
    private static int defaultPort = 55556;
    private static String defaultServer = "localhost";

    @Override
    public void start(Stage primaryStage) throws Exception {
        System.out.println("Starting client");
        IService server = new ServerProxy(defaultServer, defaultPort);

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/loginMPP.fxml"));
        AnchorPane root = loader.load();
        LoginController ctrl = loader.getController();
        ctrl.setServer(server);

        FXMLLoader appLoader = new FXMLLoader();
        appLoader.setLocation(getClass().getResource("/view/mainApp.fxml"));
        AnchorPane appRoot = appLoader.load();
        AppController appCtrl = appLoader.getController();

        appCtrl.setServer(server);

        ctrl.setAppCtrl(appCtrl);
        ctrl.setParent(appRoot);

        stage = new Stage();
        stage.setTitle("SpringWell Festival - Login to see the latest concerts");
        stage.setScene(new Scene(root));
        stage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
