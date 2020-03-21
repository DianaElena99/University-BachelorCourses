package Model;

public class User {
    private String nume;
    private String parola;
    private String email;
    private TipUser rol;
    private Integer codUser;

    // constructor pt angajat
    public User(int cod, String nume, String email, String parola) {
        this.nume = nume;
        this.parola = parola;
        this.rol = TipUser.ANGAJAT;
        this.codUser = cod;
        this.email = email;
    }

    public User(int cod, String nume){
        this.nume = nume;
        this.codUser = cod;
        this.email = "";
        this.parola = "";
        this.rol = TipUser.CLIENT;
    }

    public String getEmail() {
        return email;
    }

    public void setCodUser(Integer codUser) {
        this.codUser = codUser;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getParola() {
        return parola;
    }

    public void setParola(String parola) {
        this.parola = parola;
    }

    public TipUser getRol() {
        return rol;
    }

    public void setRol(TipUser rol) {
        this.rol = rol;
    }

    public Integer getCodUser() {
        return codUser;
    }

    public void setCodUser(int codUser) {
        this.codUser = codUser;
    }

    @Override
    public String toString() {
        return "User{" +
                "nume='" + nume + '\'' +
                ", parola='" + parola + '\'' +
                ", email='" + email + '\'' +
                ", rol=" + rol +
                ", codUser=" + codUser +
                '}';
    }
}
