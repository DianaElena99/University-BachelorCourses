import Controller.AppController;
import Controller.LoginController;
import Model.Concert;
import Model.ConcertDTO;
import Model.TipUser;
import Model.User;
import Repository.ArtistRepo;
import Repository.BiletRepo;
import Repository.ConcertRepo;
import Repository.UserRepo;
import Service.Service;
import Utils.GetPropertyValues;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        GetPropertyValues gp = new GetPropertyValues();
        Properties p = gp.getPropValues();
        String s2 = p.get("database").toString();
        System.out.println(s2);

        ConcertRepo cr = new ConcertRepo();
        BiletRepo br = new BiletRepo();
        UserRepo ur = new UserRepo();
        Service ser = new Service(ur, cr, br);

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/View/LoginMPP.fxml"));
        AnchorPane ap = loader.load();
        LoginController lc = loader.getController();
        lc.setService(ser);

        primaryStage.setScene(new Scene(ap));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
