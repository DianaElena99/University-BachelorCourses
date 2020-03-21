package Model.Validators;

import Model.User;
import javafx.scene.layout.VBox;

public class UserValidator implements Validator<User> {
    @Override
    public void validate(User user) throws Exception {
        String err = "";
        if (user.getNume().equals(""))
            err+= "Numele nu poate fi vid";
        if (!(user.getCodUser() instanceof Integer))
            err+="Codul trebuie sa fie nr";
        if (user.getCodUser() < 0)
            err += "Codul nu poate sa fie nr negativ";
        if (!err.equals(""))
            throw new Exception(err);
    }
}
