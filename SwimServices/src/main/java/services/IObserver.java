package services;


import model.Inscriere;
import model.Organizator;

public interface IObserver {
     void participantInscris(Service service,String nume, int varsta, int idProba) throws Exception;

     void loggedIn(Organizator user);

     void refresh(Inscriere inscriere);
}
