import Controller.LoginController;
import Repository.hibernate_repos.BiletHibernateRepo;
import Repository.hibernate_repos.ConcertDTORepo;
import Repository.hibernate_repos.ConcertHibernateRepo;
import Repository.hibernate_repos.UserHibernateRepo;
import Service.Service;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        UserHibernateRepo ur = new UserHibernateRepo();
        ConcertHibernateRepo cr = new ConcertHibernateRepo();
        ConcertDTORepo cdto = new ConcertDTORepo();
        BiletHibernateRepo br = new BiletHibernateRepo();

        Service service = new Service(ur,cr,cdto,br);

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/LoginMPP.fxml"));
        AnchorPane ap = loader.load();
        LoginController ctrl = loader.getController();
        ctrl.setService(service);

        Stage st = new Stage();
        Scene sc = new Scene(ap);
        st.setScene(sc);
        st.show();
    }
}
