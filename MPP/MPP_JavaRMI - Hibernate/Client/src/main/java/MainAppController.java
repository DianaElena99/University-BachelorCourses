import Model.Bilet;
import Model.ConcertDTO;
import Model.User;
import Services.IObserver;
import Services.IService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MainAppController extends UnicastRemoteObject implements IObserver, Serializable {
    @FXML
    public Button LogOutBtn;
    public Button sellBtn;
    public TextField nrBileteTxt;
    public TextField numeTxt;
    public Button searchBtn;
    public DatePicker dataPicker;

    public TableView<ConcertDTO> tabel;
    public TableView<ConcertDTO> rezFin;

    public TableColumn<ConcertDTO, String> artistCol;
    public TableColumn<ConcertDTO, String> locatieCol;
    public TableColumn<ConcertDTO, LocalDateTime> dataCol;
    public TableColumn<ConcertDTO, Integer> OcupCol;
    public TableColumn<ConcertDTO, Integer> FreeCol;

    public TableColumn<ConcertDTO, String> artistRez;
    public TableColumn<ConcertDTO, String> locatieRez;
    public TableColumn<ConcertDTO, LocalDateTime> dataRez;
    public TableColumn<ConcertDTO, Integer> freeRez;
    
    //public ClientController ctrl;

    public IService server;

    public User user;

    public MainAppController() throws RemoteException {
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setServer(IService server) {
        this.server = server;
    }

    ObservableList<ConcertDTO> modelConcerte = FXCollections.observableArrayList();

    @FXML
    public void initialize(){
        rezFin.setVisible(false);
        OcupCol.setCellValueFactory(new PropertyValueFactory<ConcertDTO, Integer>("locuriOcup"));
        FreeCol.setCellValueFactory(new PropertyValueFactory<ConcertDTO, Integer>("locuriFree"));
        dataCol.setCellValueFactory(new PropertyValueFactory<ConcertDTO, LocalDateTime>("data"));
        locatieCol.setCellValueFactory(new PropertyValueFactory<ConcertDTO, String>("locationName"));
        artistCol.setCellValueFactory(new PropertyValueFactory<ConcertDTO, String>("artistName"));

        freeRez.setCellValueFactory(new PropertyValueFactory<ConcertDTO, Integer>("locuriFree"));
        dataRez.setCellValueFactory(new PropertyValueFactory<ConcertDTO, LocalDateTime>("data"));
        locatieRez.setCellValueFactory(new PropertyValueFactory<ConcertDTO, String>("locationName"));
        artistRez.setCellValueFactory(new PropertyValueFactory<ConcertDTO, String>("artistName"));

        dataPicker.valueProperty().addListener((ov, oldval, nouval)->{
            rezFin.setVisible(true);
            LocalDate ld = dataPicker.getValue();
            rezFin.getItems().clear();
            List<ConcertDTO> rez = new ArrayList<>();
            try{
                rez = server.GetConcertsByDate(ld);
            }catch (Exception e){e.printStackTrace();}
            rezFin.getItems().setAll(rez);
        });
    }

    public void onLogout(ActionEvent actionEvent) {
        Logout();
    }

    public void onSell(ActionEvent actionEvent) {
        ConcertDTO c = tabel.getSelectionModel().getSelectedItem();
        int nrTot = c.getLocuriFree();
        int nrBilete = 0;
        if (c == null){
            Alert a = new Alert(Alert.AlertType.WARNING);
            a.setHeaderText("No concert selected");
            a.show();
        }else{
            try{
                nrBilete = Integer.parseInt(nrBileteTxt.getText());
            }
            catch (Exception e){
                Alert a = new Alert(Alert.AlertType.WARNING);
                a.setHeaderText("Nu ati introdus numarul de locuri");
                a.show();
            }
            if (nrBilete > nrTot){
                Alert a = new Alert(Alert.AlertType.WARNING);
                a.setHeaderText("Locuri insuficiente");
                a.show();
            }
            else{
                String customer = numeTxt.getText();
                int concert = c.getConcertID();
                Bilet b = new Bilet(customer, concert, nrBilete);
                try{
                    server.SellTicket(b);
                    //modelConcerte.setAll(ctrl.concerts);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }


    public void Logout() {
        try{
            server.Logout(user, this);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void initGUI() {
        //init model
        try {
            List<ConcertDTO> concerte = server.GetAllConcerts();
            modelConcerte.setAll(concerte);
            tabel.getItems().setAll(modelConcerte);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void update(List<ConcertDTO> actual) throws RemoteException {
        modelConcerte.setAll(actual);
        tabel.getItems().setAll(modelConcerte);
    }
}
