package service;


import model.Inscriere;
import model.Organizator;
import model.Participant;


public interface IObserver {
     void participantInscris(Service service,String nume, int varsta, int idProba) throws Exception;

     void loggedIn(Organizator user);

     void refresh(Inscriere inscriere);
}
