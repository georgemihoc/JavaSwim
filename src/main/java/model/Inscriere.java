package model;

public class Inscriere {
    int idInscriere;
    String idParticipant;
    String idProba;

    public Inscriere(int idInscriere, String idParticipant, String idProba) {
        this.idInscriere = idInscriere;
        this.idParticipant = idParticipant;
        this.idProba = idProba;
    }

    public int getIdInscriere() {
        return idInscriere;
    }

    public void setIdInscriere(int idInscriere) {
        this.idInscriere = idInscriere;
    }

    public String getIdParticipant() {
        return idParticipant;
    }

    public void setIdParticipant(String idParticipant) {
        this.idParticipant = idParticipant;
    }

    public String getIdProba() {
        return idProba;
    }

    public void setIdProba(String idProba) {
        this.idProba = idProba;
    }
}
