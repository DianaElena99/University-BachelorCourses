import Controllers.AbstractController;
import Controllers.LoginController;
import Controllers.MedicController;
import Model.Sectie;
import Model.TipUser;
import Model.User;
import Repository.ComandaRepo;
import Repository.MedicamentRepo;
import Repository.UserRepo;
import Service.Service;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Main extends Application {
    public UserRepo ur;
    public MedicamentRepo mr;
    public ComandaRepo cr;
    public Service service;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        System.out.println("Entry point --- Creating the repositories ");
        ur = new UserRepo();
        mr = new MedicamentRepo();
        cr = new ComandaRepo();
        service = new Service(ur, mr, cr);
        testDataObtained();

        for (TipUser tu : TipUser.values()) {

            if (tu == TipUser.ADMIN) {
                FXMLLoader loader = new FXMLLoader();
                FXMLLoader loaderApp = new FXMLLoader();
                loader.setLocation(getClass().getResource("/view/gui_login.fxml"));
                loaderApp.setLocation(getClass().getResource("/view/gui_admin.fxml"));
                AnchorPane app = loaderApp.load();
                AnchorPane ap = loader.load();

                LoginController ctrl = loader.getController();
                AbstractController appCtrl = loaderApp.getController();
                appCtrl.setService(service);

                ctrl.setService(service);
                ctrl.setUser(new User("", "", "ADMIN", ""));
                ctrl.setApp(app);
                ctrl.setControllerApp(appCtrl);
                Scene sc = new Scene(ap);
                Stage st = new Stage();
                st.setTitle("admin");
                st.setScene(sc);
                st.show();
            }

            if (tu == TipUser.FARMACIST) {
                FXMLLoader loader = new FXMLLoader();
                FXMLLoader loaderApp = new FXMLLoader();
                loader.setLocation(getClass().getResource("/view/gui_login.fxml"));
                loaderApp.setLocation(getClass().getResource("/view/farmacie_server.fxml"));
                AnchorPane app = loaderApp.load();
                AnchorPane ap = loader.load();

                LoginController ctrl = loader.getController();
                AbstractController appCtrl = loaderApp.getController();
                appCtrl.setService(service);

                ctrl.setService(service);
                ctrl.setUser(new User("", "", "FARMACIST", ""));
                ctrl.setApp(app);
                ctrl.setControllerApp(appCtrl);
                Scene sc = new Scene(ap);
                Stage st = new Stage();
                st.setTitle("Farmacist");
                st.setScene(sc);
                st.show();
            }

            if (tu == TipUser.MEDIC) {
                for (Sectie se : Sectie.values()) {
                    FXMLLoader loader = new FXMLLoader();
                    FXMLLoader loaderApp = new FXMLLoader();
                    loader.setLocation(getClass().getResource("/view/gui_login.fxml"));
                    loaderApp.setLocation(getClass().getResource("/view/farmacie_client.fxml"));
                    AnchorPane app = loaderApp.load();
                    AnchorPane ap = loader.load();

                    LoginController ctrl = loader.getController();
                    MedicController appCtrl = loaderApp.getController();
                    appCtrl.setUser(new User("", "", "MEDIC", String.valueOf(se)));
                    appCtrl.setService(service);

                    ctrl.setUser(new User("", "", "MEDIC", String.valueOf(se)));
                    ctrl.setService(service);

                    ctrl.setApp(app);
                    ctrl.setControllerApp(appCtrl);
                    Scene sc = new Scene(ap);
                    Stage st = new Stage();
                    st.setOnCloseRequest((ev)->{
                        appCtrl.golesteCos();
                    });
                    st.setTitle("Sectia de " + String.valueOf(se));
                    st.setScene(sc);
                    st.show();
                }
            }
        }


    }

    private void testDataObtained() {
        System.out.println("Retrived all the users from the DB" );
        ur.users.forEach(System.out::println);
        System.out.println("\nRetrived all the medicines from the DB" );
        mr.meds.forEach(System.out::println);
    }
}
