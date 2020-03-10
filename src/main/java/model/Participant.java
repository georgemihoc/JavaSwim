package model;

public class Participant {
    int idParticipant;
    String nume;
    int varsta;

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
