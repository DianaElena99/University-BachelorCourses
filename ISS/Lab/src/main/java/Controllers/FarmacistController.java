package Controllers;

import Model.Comanda;
import Model.User;
import Service.Observer;
import Service.Service;
import Service.TipEvent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class FarmacistController extends AbstractController implements Observer {
    @FXML
    public TableView<Comanda> tblComenzi;
    public TableColumn<Comanda, Integer> codCol;
    public TableColumn<Comanda, Float> pretCol;
    public TableColumn<Comanda, String> sectieCol;

    private Service service;
    private User user;

    ObservableList<Comanda> comenziModel;

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public void update(TipEvent te) {
        if (te == TipEvent.ADD_COMANDA){
            comenziModel = FXCollections.observableArrayList(service.comandaRepo.getAll());
            tblComenzi.getItems().setAll(comenziModel);
        }
        if (te == TipEvent.REMOVE_COMANDA){
            comenziModel = FXCollections.observableArrayList(service.comandaRepo.getAll());
            tblComenzi.getItems().setAll(comenziModel);
        }
        if(te == TipEvent.ANULARE_COMANDA){
            comenziModel = FXCollections.observableArrayList(service.comandaRepo.getAll());
            tblComenzi.getItems().setAll(comenziModel);
        }
    }

    @FXML
    public void initialize(){
        codCol.setCellValueFactory(new PropertyValueFactory<>("cod"));
        pretCol.setCellValueFactory(new PropertyValueFactory<>("subtotal"));
        sectieCol.setCellValueFactory(new PropertyValueFactory<>("sectie"));

    }

    public void setService(Service service) {
        this.service = service;
        service.addObserver(this);
        comenziModel = FXCollections.observableArrayList(service.comandaRepo.getAll());
        tblComenzi.getItems().setAll(comenziModel);
    }

    /*Handler pt onorat comenzi*/
    public void onOnorareCmd(ActionEvent actionEvent) {
        Comanda cmd = tblComenzi.getSelectionModel().getSelectedItem();
        service.onoreazaComanda(cmd);
    }
}
