package Model;

import java.io.Serializable;

public class User implements Serializable {
    private String email;
    private String passwd;

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    private String type;
    private String section;

    public User(){}

    public User(String email, String password, String type, String section) {
        this.email = email;
        this.passwd = password;
        this.type = type;
        this.section = section;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return passwd;
    }

    public void setPassword(String password) {
        this.passwd = password;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", password='" + passwd + '\'' +
                ", type='" + type + '\'' +
                ", section='" + section + '\'' +
                '}';
    }
}
