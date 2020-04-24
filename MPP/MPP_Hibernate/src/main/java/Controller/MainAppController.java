package Controller;

import Model.ConcertDTO;
import Service.Service;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.time.LocalDate;

public class MainAppController {
    public Service service;
    public String user;

    @FXML
    public Button LogOutBtn;
    public Button sellBtn;
    public TextField nrBileteTxt;
    public TextField numeTxt;
    public Button searchBtn;
    public DatePicker dataPicker;

    public TableColumn<ConcertDTO, Integer> freeRez;
    public TableColumn<ConcertDTO, String> dataRez;
    public TableColumn<ConcertDTO, String> locatieRez;
    public TableColumn<ConcertDTO, String> artistRez;
    public TableView<ConcertDTO> rezFin;

    public TableColumn<ConcertDTO, Integer> OcupCol;
    public TableColumn<ConcertDTO, Integer> FreeCol;
    public TableColumn<ConcertDTO, String> dataCol;
    public TableColumn<ConcertDTO, String> locatieCol;
    public TableColumn<ConcertDTO, String> artistCol;
    public TableView<ConcertDTO> tabel;
    private ObservableList<ConcertDTO> concerte;

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
        concerte = FXCollections.observableArrayList(service.GetConcerte());
        tabel.setItems(concerte);
    }

    @FXML
    public void initialize(){
        rezFin.setVisible(false);
        OcupCol.setCellValueFactory(new PropertyValueFactory<ConcertDTO, Integer>("locuriOcup"));
        FreeCol.setCellValueFactory(new PropertyValueFactory<ConcertDTO, Integer>("locuriFree"));
        dataCol.setCellValueFactory(new PropertyValueFactory<ConcertDTO, String>("data"));
        locatieCol.setCellValueFactory(new PropertyValueFactory<ConcertDTO, String>("location"));
        artistCol.setCellValueFactory(new PropertyValueFactory<ConcertDTO, String>("artist"));
    }

    public void onSearch(ActionEvent actionEvent) {
        LocalDate data = dataPicker.getValue();
        ObservableList<ConcertDTO> rez = FXCollections.observableArrayList(service.ConcertsByDate(data));

        rezFin.setVisible(true);
        freeRez.setCellValueFactory(new PropertyValueFactory<ConcertDTO, Integer>("locuriFree"));
        dataRez.setCellValueFactory(new PropertyValueFactory<ConcertDTO, String>("data"));
        locatieRez.setCellValueFactory(new PropertyValueFactory<ConcertDTO, String>("location"));
        artistRez.setCellValueFactory(new PropertyValueFactory<ConcertDTO, String>("artist"));
        rezFin.setItems(rez);
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
                int concert = c.getId();
                service.VindeBilete(nrBilete, customer, concert);
                concerte = FXCollections.observableArrayList(service.GetConcerte());
                tabel.setItems(concerte);
            }
        }
    }

    public void onLogout(ActionEvent actionEvent) {
        service.LogOut(this.user);
        Node n = (Node) actionEvent.getSource();
        Stage st = (Stage) n.getScene().getWindow();
        st.close();
    }

    public void setUser(String usr) {
        this.user = usr;
    }

}
