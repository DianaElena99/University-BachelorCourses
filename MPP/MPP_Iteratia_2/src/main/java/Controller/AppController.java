package Controller;

import Model.Concert;
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
import java.time.LocalDateTime;

public class AppController {
    @FXML
    public Button LogOutBtn;
    public Button sellBtn;
    public TextField nrBileteTxt;
    public TextField numeTxt;
    public Button searchBtn;
    public DatePicker dataPicker;

    public TableColumn<ConcertDTO, Integer> freeRez;
    public TableColumn<ConcertDTO, LocalDateTime> dataRez;
    public TableColumn<ConcertDTO, String> locatieRez;
    public TableColumn<ConcertDTO, String> artistRez;
    public TableView rezFin;

    public TableColumn<ConcertDTO, Integer> OcupCol;
    public TableColumn<ConcertDTO, Integer> FreeCol;
    public TableColumn<ConcertDTO, LocalDateTime> dataCol;
    public TableColumn<ConcertDTO, String> locatieCol;
    public TableColumn<ConcertDTO, String> artistCol;
    public TableView<ConcertDTO> tabel;

    Service service;
    int UserID;

    private ObservableList<ConcertDTO> concerte;

    @FXML
    public void initialize(){
        rezFin.setVisible(false);
        OcupCol.setCellValueFactory(new PropertyValueFactory<ConcertDTO, Integer>("locuriOcup"));
        FreeCol.setCellValueFactory(new PropertyValueFactory<ConcertDTO, Integer>("locuriFree"));
        dataCol.setCellValueFactory(new PropertyValueFactory<ConcertDTO, LocalDateTime>("data"));
        locatieCol.setCellValueFactory(new PropertyValueFactory<ConcertDTO, String>("locationName"));
        artistCol.setCellValueFactory(new PropertyValueFactory<ConcertDTO, String>("artistName"));
    }



    public void setService(Service serv, int userID){
        this.UserID = userID;
        this.service = serv;
        concerte = FXCollections.observableArrayList(service.GetConcerte());
        tabel.setItems(concerte);
        setTableColorChangeListener();
    }

    private void setTableColorChangeListener(){
        FreeCol.setCellFactory(col -> {
            return new TableCell<ConcertDTO, Integer>(){
                @Override
                protected void updateItem(Integer item, boolean empty){
                    super.updateItem(item, empty);

                    setText(empty ? "" : getItem().toString());
                    setGraphic(null);

                    TableRow<ConcertDTO> row = getTableRow();
                    ConcertDTO dto = row.getItem();
                    if (!isEmpty()){
                        if (item != null && dto.getLocuriFree() == 0)
                            row.setStyle("-fx-background-color: #ff5b5a");
                    }
                }
            };
        });
    }

    public void onSearch(javafx.event.ActionEvent actionEvent) {
        LocalDate data = dataPicker.getValue();
        ObservableList<ConcertDTO> rez = FXCollections.observableArrayList(service.ConcertsByDate(data));

        rezFin.setVisible(true);
        freeRez.setCellValueFactory(new PropertyValueFactory<ConcertDTO, Integer>("locuriFree"));
        dataRez.setCellValueFactory(new PropertyValueFactory<ConcertDTO, LocalDateTime>("data"));
        locatieRez.setCellValueFactory(new PropertyValueFactory<ConcertDTO, String>("locationName"));
        artistRez.setCellValueFactory(new PropertyValueFactory<ConcertDTO, String>("artistName"));
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
                int concert = c.getConcertID();
                service.VindeBilete(nrBilete, customer, concert);
                concerte = FXCollections.observableArrayList(service.GetConcerte());
                tabel.setItems(concerte);
            }
        }

    }

    public void onLogout(ActionEvent actionEvent) {
        service.LogOut(this.UserID);
        Node n = (Node) actionEvent.getSource();
        Stage st = (Stage) n.getScene().getWindow();
        st.close();
    }
}
