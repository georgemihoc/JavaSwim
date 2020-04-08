import model.Inscriere;
import model.Organizator;
import model.Participant;
import model.Proba;
import services.*;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class ClientController  extends UnicastRemoteObject implements IObserver, Serializable {

    private UserListModel userListModel;

    private IServices server;
    private MainViewFXML mainViewFXML;
    private Organizator user;

    public ClientController(IServices server,  MainViewFXML view) throws RemoteException {
        userListModel=new UserListModel();
        this.server = server;
        this.mainViewFXML = view;
    }

    @Override
    public void participantInscris(String nume, int varsta, int idProba) throws Exception {
        System.out.println("PAS 1");
        server.addInscriere(nume,varsta,idProba);

    }

    @Override
    public void loggedIn(Organizator user) {
        userListModel.friendLoggedIn(user.getIdOrganizator());
    }

    @Override
    public void refresh(Inscriere inscriere) {
        mainViewFXML.refresh(inscriere);
    }

    @Override
    public void inscriereEfectuata(Inscriere inscriere) throws Exception {
        mainViewFXML.refresh(inscriere);

    }

    public void login(String username, String password) throws Exception {
        System.out.println("USER");
        //System.out.println(service.findOrganizator(username,password).getUsername());
//        server.login(service.findOrganizator(username,password),this);
        user = new Organizator(0,username,password);
        server.login(new Organizator(0,username,password),this);
    }
    public void logout() throws Exception {
        server.logout(user,this);
    }
    public List<Proba> getProbe() throws Exception {
        return new ArrayList<>(Arrays.asList(server.getProbe()));
    }
    public List<Participant> getParticipanti() throws Exception {
        return new ArrayList<>(Arrays.asList(server.getParticipanti()));
    }
    public List<Inscriere> getInscrieri() throws Exception {
        return new ArrayList<>(Arrays.asList(server.getInscrieri()));
    }
}
