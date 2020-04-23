package Controllers;

import Model.User;
import Service.Observer;
import Service.Service;
import Service.TipEvent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class AdminController extends AbstractController implements Observer {
    @FXML
    public TextField emailTxt;
    public TextField passTxt;
    public TextField tipTxt;
    public TextField sectionTxt;
    public Button btnAdd;
    public Button btnSterge;
    public Button btnModif;
    public TableView<User> accountTable;
    public TableColumn<User,String> emailCol;
    public TableColumn<User, String> passCol;
    public TableColumn<User, String> userCol;

    ObservableList<User> usersModel;

    private Service service;

    private User user;

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public void update(TipEvent te) {

    }

    @FXML
    public void initialize(){
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        passCol.setCellValueFactory(new PropertyValueFactory<>("passwd"));
        userCol.setCellValueFactory(new PropertyValueFactory<>("type"));
    }

    public void setService(Service service) {
        this.service = service;
        usersModel = FXCollections.observableArrayList(service.userRepo.users);
        accountTable.getItems().setAll(usersModel);
    }

    public void onCreateAcc(ActionEvent actionEvent) {
        try{
            String email = emailTxt.getText();
            if (!email.contains("@") || email.equals("")){
                throw new Exception("Email invalid");
            }
            String pass = passTxt.getText();
            if (pass.length() < 3){
                throw new Exception("Parola prea slaba");
            }
            String tip = tipTxt.getText();
            if (!(tip.equalsIgnoreCase("ADMIN") ||
                    tip.equalsIgnoreCase("MEDIC") ||
                    tip.equalsIgnoreCase("FARMACIST"))){
                throw new Exception("Tip de user invalid");
            }
            String sectie = sectionTxt.getText();

            User user = new User(email,pass,tip,sectie);
            service.createUser(user);

            Alert a = new Alert(Alert.AlertType.CONFIRMATION);
            a.setHeaderText("Cont creat cu succes");
            a.show();
        }catch (Exception e){
            Alert a = new Alert(Alert.AlertType.WARNING);
            a.setHeaderText("Date invalide -- Crearea contului a esuat");
            a.setContentText(e.getMessage());
            a.show();
        }
    }
}
