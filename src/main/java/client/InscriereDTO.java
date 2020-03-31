package client;

import java.io.Serializable;


public class InscriereDTO implements Serializable{
    int idInscriere;
    int idParticipant;
    int idProba;
    String nume;
    int varsta;

    public InscriereDTO(int idInscriere, int idParticipant, int idProba , String nume, int varsta) {
        this.idInscriere = idInscriere;
        this.idParticipant = idParticipant;
        this.idProba = idProba;
        this.nume = nume;
        this.varsta = varsta;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public int getVarsta() {
        return varsta;
    }

    public void setVarsta(int varsta) {
        this.varsta = varsta;
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
        return idInscriere + " " + idParticipant + " " + idProba + " " + nume + " " + varsta;
    }
}
