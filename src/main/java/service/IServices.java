package service;


import model.Inscriere;
import model.Organizator;

import java.util.Map;

public interface IServices {
     void addInscriere(Service service,String nume, int varsta, int idProba) throws Exception;

     void login(Organizator user, IObserver client) throws Exception;



     Map<Integer, IObserver> getLoggedUsers() throws Exception;

}
