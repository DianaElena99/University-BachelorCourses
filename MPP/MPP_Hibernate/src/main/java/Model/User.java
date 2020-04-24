package Model;

import java.io.Serializable;

public class User implements Serializable {
    private int id;
    private String email;
    private String passwd;

    public User(int id, String email, String passwd) {
        this.id = id;
        this.email = email;
        this.passwd = passwd;
    }

    public User(String email, String passwd) {
        this.email = email;
        this.passwd = passwd;
    }

    public User() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", passwd='" + passwd + '\'' +
                '}';
    }
}
