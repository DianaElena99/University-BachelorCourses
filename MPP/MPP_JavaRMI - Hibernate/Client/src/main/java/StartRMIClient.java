import Services.IService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Properties;

public class StartRMIClient extends Application {
    public static void main(String[] args) {
       launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        try{
            ApplicationContext appContext = new ClassPathXmlApplicationContext("classpath:spring-rmi-client.xml");
            IService server = (IService) appContext.getBean("service");
            System.out.println("Obtained a ref to remote festival service");

            FXMLLoader loader = new FXMLLoader();
            FXMLLoader appLoader = new FXMLLoader();

            loader.setLocation(getClass().getResource("/view/LoginMPP.fxml"));
            appLoader.setLocation(getClass().getResource("view/mainApp.fxml"));

            Parent root = loader.load();
            Parent appRoot = appLoader.load();

            LoginController Loginctrl = loader.getController();
            MainAppController appCtrl = appLoader.getController();

            Loginctrl.setServer(server);
            Loginctrl.setAppCtrl(appCtrl);
            Loginctrl.setParent(appRoot);

            Scene sc = new Scene(root);
            Stage st = new Stage();
            st.setScene(sc);
            st.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
