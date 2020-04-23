package Controllers;

import Model.*;
import Service.Observer;
import Service.Service;
import Service.TipEvent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MedicController extends AbstractController implements Observer {
    //tabel medicamente in stoc
    @FXML
    public TableView<Medicament> meds;
    public TableColumn<Medicament,Integer> codMed;
    public TableColumn<Medicament,String> numeMed;
    public TableColumn<Medicament,Integer> cantMed;
    public TableColumn<Medicament,Float> pretMed;
    public TextField txtTot;

    private Float subtotalComandaCurenta;

    //cos de cumparaturi
    public TableView<Medicament> cos;
    public TableColumn<Medicament, Integer> codCos;
    public TableColumn<Medicament, Integer> numeCos;
    public TableColumn<Medicament, Integer> cantitCos;
    
    public TextField txtCod;
    public TextField txtNume;
    public TextField txtCantit;

    //comenzi neonorate pe sectia respectiva
    public TableView<Comanda> cmdList;
    public TableColumn<Comanda, Integer> cmdCod;
    public TableColumn<Comanda, Float> cmdPret;

    public Button addInCos;
    public Button deleteCmd;
    public Button plaseazaCmd;
    public Button btnStergeDinCos;

    public Service ser;

    ObservableList<Medicament> medicamentModel;
    ObservableList<Medicament> cosModel;
    ObservableList<Comanda> comenzi;


    @Override
    public void setUser(User u) {
        super.setUser(u);
        System.out.println(super.user);
    }

    @Override
    public void update(TipEvent te) {
        if(te == TipEvent.MODIF_CANTITATE){
            this.medicamentModel = FXCollections.observableArrayList(ser.medicamentRepo.getAll());
            meds.getItems().setAll(medicamentModel);
        }
        if (te == TipEvent.REMOVE_COMANDA){
            this.comenzi = FXCollections.observableArrayList(ser.comandaRepo.getAll().stream().filter(x->x.getSectie().equalsIgnoreCase(this.user.getSection())).collect(Collectors.toList()));
            cmdList.getItems().setAll(comenzi);
        }
    }

    public void setService(Service service) {
        ser = service;
        ser.addObserver(this);
        medicamentModel = FXCollections.observableArrayList(ser.medicamentRepo.getAll());
        cosModel = FXCollections.observableArrayList();
        cos.getItems().setAll(cosModel);
        comenzi = FXCollections.observableArrayList(ser.comandaRepo.comenzi
                .stream()
                .filter(x -> x.getSectie().equals(super.user.getSection()))
                .collect(Collectors.toList()));
        System.out.println(comenzi);
        meds.getItems().setAll(medicamentModel);
        cmdList.getItems().setAll(comenzi);

    }

    public void golesteCos(){
        for(Medicament med : cosModel){
            subtotalComandaCurenta -= med.getPret() * med.getCantitateDisp();

            Medicament stoc = (Medicament) medicamentModel.stream().filter(x -> x.getNume().equalsIgnoreCase(med.getNume())).toArray()[0];
            Integer cant = stoc.getCantitateDisp() + med.getCantitateDisp();

            stoc.setCantitateDisp(cant);
            ser.modificaCantitate(stoc, user.getSection());

            cosModel.remove(med);
            cos.getItems().setAll(cosModel);
        }
    }

    @FXML
    public void initialize(){
        codMed.setCellValueFactory(new PropertyValueFactory<>("id"));
        numeMed.setCellValueFactory(new PropertyValueFactory<>("nume"));
        cantMed.setCellValueFactory(new PropertyValueFactory<>("cantitateDisp"));
        pretMed.setCellValueFactory(new PropertyValueFactory<>("pret"));

        codCos.setCellValueFactory(new PropertyValueFactory<>("id"));
        numeCos.setCellValueFactory(new PropertyValueFactory<>("nume"));
        cantitCos.setCellValueFactory(new PropertyValueFactory<>("cantitateDisp"));

        cmdCod.setCellValueFactory(new PropertyValueFactory<>("cod"));
        cmdPret.setCellValueFactory(new PropertyValueFactory<>("subtotal"));

        meds.setOnMouseClicked((ev)->{
            Integer idMed = meds.getSelectionModel().getSelectedItem().getId();
            Integer cantMed = meds.getSelectionModel().getSelectedItem().getCantitateDisp();
            String numeMed = meds.getSelectionModel().getSelectedItem().getNume();
            txtCod.setText(idMed.toString());
            txtCantit.setText(cantMed.toString());
            txtNume.setText(numeMed);
        });

        subtotalComandaCurenta = 0f;
    }

    /* Handler care adauga medicament in cosul de cumparaturi */
    public void onAdaugaCos(ActionEvent actionEvent) {
        String nume = txtNume.getText();
        int cod = Integer.parseInt(txtCod.getText());
        int cant = Integer.parseInt(txtCantit.getText());
        int disp = meds.getSelectionModel().getSelectedItem().getCantitateDisp();
        if (cant > disp){
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setHeaderText("Cantitate insuficienta. Introduceti o cantitate mai mica");
            a.show();
            return;
        }

        Float pret = meds.getSelectionModel().getSelectedItem().getPret();
        subtotalComandaCurenta += pret*cant;
        txtTot.setText(subtotalComandaCurenta.toString());

        Medicament m = new Medicament(cod, nume, cant, pret);
        Medicament aux = new Medicament(cod, nume, disp-cant, pret);

        cosModel.add(m);
        cos.getItems().setAll(cosModel);
        aux.setCantitateDisp(disp-cant);

        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
        a.setHeaderText("Medicament adaugat cu succes in cos");
        a.show();
    }

    /* Handler sterge medicament din cos */
    public void onStergeCos(ActionEvent actionEvent) {
        Medicament med = cos.getSelectionModel().getSelectedItem();
        subtotalComandaCurenta -= med.getPret() * med.getCantitateDisp();
        Medicament stoc = (Medicament) medicamentModel.stream().filter(x -> x.getNume().equalsIgnoreCase(med.getNume())).toArray()[0];
        Integer cant = stoc.getCantitateDisp() + med.getCantitateDisp();
        stoc.setCantitateDisp(cant);
        ser.modificaCantitate(stoc, user.getSection());
        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
        a.setContentText("Meidcamentul a fost sters din cos");
        a.show();
        cosModel.remove(med);
        cos.getItems().setAll(cosModel);
    }

    /*Handler plasare comanda*/
    public void onPlaseazaCmd(ActionEvent actionEvent) {
        Comanda cmd = new Comanda(user.getEmail(), user.getSection(), subtotalComandaCurenta);
        cmd.setMedicamentList(cosModel);

        for(Medicament m : cosModel){
            Medicament inStoc = (Medicament)medicamentModel.stream().filter(x->x.getNume().equals(m.getNume())).toArray()[0];
            Medicament aux = new Medicament(m.getId(),m.getNume(), inStoc.getCantitateDisp() - m.getCantitateDisp(), m.getPret());
            ser.modificaCantitate(aux, user.getSection());
        }
        cosModel = FXCollections.observableArrayList(new ArrayList<>());
        cos.getItems().setAll(cosModel);
        comenzi.add(cmd);
        cmdList.getItems().setAll(comenzi);

        subtotalComandaCurenta = 0f;
        ser.addComanda(cmd);
    }

    /* Handler anulare comanda */
    public void onAnulareCmd(ActionEvent actionEvent) {
        Comanda cd = cmdList.getSelectionModel().getSelectedItem();
        if (cd == null){
            Alert a = new Alert(Alert.AlertType.WARNING);
            a.setHeaderText("Nu ati selectat comanda");
            a.show();
        }
        else{
            for(Medicament m : cd.getMedicamentList()){
                Medicament inStoc = (Medicament)medicamentModel.stream().filter(x->x.getNume().equals(m.getNume())).toArray()[0];
                Medicament aux = new Medicament(m.getId(),m.getNume(), inStoc.getCantitateDisp() + m.getCantitateDisp(), m.getPret());
                ser.modificaCantitate(aux, user.getSection());
            }
            cosModel = FXCollections.observableArrayList(new ArrayList<>());
            cos.getItems().setAll(cosModel);
            comenzi.remove(cd);
            cmdList.getItems().setAll(comenzi);

            ser.anuleazaComanda(cd);
        }
    }

    public void onClose(){

    }
}
