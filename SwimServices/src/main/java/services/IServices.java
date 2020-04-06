package services;

import model.Inscriere;
import model.Organizator;
import model.Participant;
import model.Proba;

public interface IServices {
     void addInscriere(String nume, int varsta, int idProba) throws Exception;

     void login(Organizator user, IObserver client) throws Exception;

     Proba[] getProbe() throws Exception;

     Participant[] getParticipanti() throws Exception;

     Inscriere[] getInscrieri() throws Exception;
}
