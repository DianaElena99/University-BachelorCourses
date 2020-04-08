package requests;

import Model.User;

public class LoginRequest implements Request {
    public User user;

    public LoginRequest(User user) {
        this.user = user;
    }
}
