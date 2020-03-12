package model;

public class Organizator {
    int idOrganizator;
    String username;
    String password;

    public Organizator(int idOrganizator, String username, String password) {
        this.idOrganizator = idOrganizator;
        this.username = username;
        this.password = password;
    }

    public int getIdOrganizator() {
        return idOrganizator;
    }

    public void setIdOrganizator(int idOrganizator) {
        this.idOrganizator = idOrganizator;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
