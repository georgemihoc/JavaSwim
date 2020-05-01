package model;

//import javax.persistence.Entity;
//import javax.persistence.Table;
import java.io.Serializable;

//@Entity
//@Table( name = "Participant" )
public class Participant implements Serializable {
    int idParticipant;
    String nume;
    int varsta;

    public Participant() {
    }

    public Participant(int idParticipant, String nume, int varsta) {
        this.idParticipant = idParticipant;
        this.nume = nume;
        this.varsta = varsta;
    }

    public int getIdParticipant() {
        return idParticipant;
    }

    public void setIdParticipant(int idParticipant) {
        this.idParticipant = idParticipant;
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

    @Override
    public String toString() {
        return idParticipant+ " " + nume + " " + varsta;
    }
}
