package services;


import model.Inscriere;
import model.Organizator;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IObserver extends Remote {
     void participantInscris(String nume, int varsta, int idProba) throws Exception, RemoteException;

     void loggedIn(Organizator user) throws RemoteException;

     void refresh(Inscriere inscriere) throws RemoteException;

     void inscriereEfectuata(Inscriere inscriere) throws Exception,RemoteException;
}
