package sample;

import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class MessageAlert {
    static void showMessage(Stage owner, Alert.AlertType type, String header, String text){
        Alert messg = new Alert(type);
        messg.setHeaderText(header);
        messg.setContentText(text);
        messg.initOwner(owner);
        messg.showAndWait();
    }

    static void showErrorMessage(Stage owner, String text){
        Alert messg = new Alert(Alert.AlertType.ERROR);
        messg.initOwner(owner);
        messg.setTitle("EROARE :( ");
        messg.setContentText(text);
        messg.showAndWait();
    }
}
