package client;

import java.io.Serializable;


public class InscriereDTO implements Serializable{
    int idInscriere;
    int idParticipant;
    int idProba;

    public InscriereDTO(int idInscriere, int idParticipant, int idProba) {
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

    public int getIdParticipant() {
        return idParticipant;
    }

    public void setIdParticipant(int idParticipant) {
        this.idParticipant = idParticipant;
    }

    public int getIdProba() {
        return idProba;
    }

    public void setIdProba(int idProba) {
        this.idProba = idProba;
    }

    @Override
    public String toString() {
        return idInscriere + " " + idParticipant + " " + idProba;
    }
}
