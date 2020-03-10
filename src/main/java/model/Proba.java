package model;

public class Proba {
    int idProba;
    int lungime;
    String stil;
    int nrParticipanti;

    public Proba(int idProba, int lungime, String stil, int nrParticipanti) {
        this.idProba = idProba;
        this.lungime = lungime;
        this.stil = stil;
        this.nrParticipanti = nrParticipanti;
    }

    public int getIdProba() {
        return idProba;
    }

    public void setIdProba(int idProba) {
        this.idProba = idProba;
    }

    public int getLungime() {
        return lungime;
    }

    public void setLungime(int lungime) {
        this.lungime = lungime;
    }

    public String getStil() {
        return stil;
    }

    public void setStil(String stil) {
        this.stil = stil;
    }

    public int getNrParticipanti() {
        return nrParticipanti;
    }

    public void setNrParticipanti(int nrParticipanti) {
        this.nrParticipanti = nrParticipanti;
    }
}
