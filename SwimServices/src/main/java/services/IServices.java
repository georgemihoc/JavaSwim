package services;

import model.Organizator;

public interface IServices {
     void addInscriere(Service service,String nume, int varsta, int idProba) throws Exception;

     void login(Organizator user, IObserver client) throws Exception;

}
