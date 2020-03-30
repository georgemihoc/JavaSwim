package service;


import model.Inscriere;
import model.Organizator;
import model.Participant;


public interface IObserver {
     void participantInscris(Inscriere inscriere) throws Exception;
     void addParticipant(Inscriere inscriere) throws Exception;

     void loggedIn(Organizator user);

     void refresh(Inscriere inscriere);
}
