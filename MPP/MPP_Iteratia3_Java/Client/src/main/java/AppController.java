import Model.Bilet;
import Model.Concert;
import Model.ConcertDTO;
import Model.User;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AppController implements Initializable,IObserver {
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


    private IService server;
    private User user;
    ObservableList<ConcertDTO> concertModel = FXCollections.observableArrayList();

    public AppController(){};

    public AppController(IService serv){
        this.server = serv;
    }

    public void initGUI() {
        try{
            initModel();
            tabel.setItems(concertModel);
            setTableColorChangeListener();
        }catch (Exception e){
            Alert a = new Alert(Alert.AlertType.WARNING);
            a.setTitle("Error in " + user.getNume() + "'s form");
            a.setContentText(e.getMessage());
            a.show();
        }
    }

    private void initModel() {
       try{
           List<ConcertDTO> rez = null;
           rez = server.getAllConcerts();
           if (rez!=null){
               concertModel.setAll(rez);
               tabel.setItems(concertModel);
           }
           else{
               System.out.println("Null list :/");
           }
       }catch (Exception e){
           e.printStackTrace();
       }
    }

    public void onSell(ActionEvent actionEvent) throws Exception {
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
                    server.sellTicket(b);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }

    public void onLogout(ActionEvent actionEvent) {
        logout();
        Node n = (Node) actionEvent.getSource();
        Stage st = (Stage) n.getScene().getWindow();
        st.close();
    }

    public void logout() {
        try{
            server.logout(this.user, this);
        } catch (Exception e) {
            System.out.println("Logout error!!!!");
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        rezFin.setVisible(false);
        OcupCol.setCellValueFactory(new PropertyValueFactory<ConcertDTO, Integer>("locuriOcup"));
        FreeCol.setCellValueFactory(new PropertyValueFactory<ConcertDTO, Integer>("locuriFree"));
        dataCol.setCellValueFactory(new PropertyValueFactory<ConcertDTO, LocalDateTime>("data"));
        locatieCol.setCellValueFactory(new PropertyValueFactory<ConcertDTO, String>("locationName"));
        artistCol.setCellValueFactory(new PropertyValueFactory<ConcertDTO, String>("artistName"));

        rezFin.setVisible(true);
        freeRez.setCellValueFactory(new PropertyValueFactory<ConcertDTO, Integer>("locuriFree"));
        dataRez.setCellValueFactory(new PropertyValueFactory<ConcertDTO, LocalDateTime>("data"));
        locatieRez.setCellValueFactory(new PropertyValueFactory<ConcertDTO, String>("locationName"));
        artistRez.setCellValueFactory(new PropertyValueFactory<ConcertDTO, String>("artistName"));

        dataPicker.valueProperty().addListener((ov, oldval, nouval)->{
            LocalDate ld = dataPicker.getValue();
            rezFin.getItems().clear();
            List<ConcertDTO> rez = new ArrayList<>();
            try{
                rez = server.searchConcerts(ld);
            }catch (Exception e){e.printStackTrace();}
            rezFin.getItems().setAll(rez);
        });
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
                        if (item != null && dto!=null && dto.getLocuriFree()==0)
                            row.setStyle("-fx-background-color: #219870");
                    }
                }
            };
        });
    }

    @Override
    public void update(List<ConcertDTO> l) {
        System.out.println("Updating table in " + user.getNume() + "'s window");
        List<ConcertDTO> lista = new ArrayList<>();
        for (ConcertDTO c : l){
            lista.add(c);
        }
        concertModel.setAll(l);
        tabel.setItems(concertModel);
    }

    public void setUser(User u) {
        this.user = u;
    }









    private List<ConcertDTO> filterData(LocalDate data) {
        return tabel.getItems().filtered(x -> x.getData().getYear() == data.getYear() && x.getData().getMonth()==data.getMonth() && x.getData().getDayOfMonth() == data.getDayOfMonth());
    }


    public void setServer(IService server) {
        this.server = server;
    }

    public void onSearch(ActionEvent actionEvent) {
        LocalDate data = dataPicker.getValue();

        if (data == null){
            Alert a = new Alert(Alert.AlertType.WARNING);
            a.setHeaderText("In ce data sa fie concertele?");
            a.show();
        }
        else{
            List<ConcertDTO> lista = filterData(data);

            Platform.runLater(()->{
                rezFin.getItems().clear();
                rezFin.getItems().setAll(lista);
            });
        }
    }



}
