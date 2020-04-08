package requests;

import Model.User;

public class LogoutRequest implements Request {
    public User u;

    public LogoutRequest(User u) {
        this.u = u;
    }
}
