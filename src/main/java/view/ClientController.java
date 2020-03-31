package view;


import model.Inscriere;
import model.Organizator;
import model.Proba;
import service.IObserver;
import service.IServices;
import service.Service;

import javax.swing.*;
import java.util.Map;


public class ClientController implements IObserver {

    private UserListModel userListModel;
//    private MessagesListModel messagesModel;

    private IServices server;
//    private ProbeListModel probeListModel;
    private Service service;
//    private User user;
    private MainViewFXML mainViewFXML;

    public ClientController(IServices server, Service service, MainViewFXML view) {
        userListModel=new UserListModel();
        this.server = server;
        this.service = service;
        this.mainViewFXML = view;
    }

    @Override
    public void participantInscris(Service service,String nume, int varsta, int idProba) throws Exception {
        System.out.println("PAS 1");
        server.addInscriere(service,nume,varsta,idProba);

    }

    @Override
    public void addParticipant(Inscriere inscriere) throws Exception {
    }

    @Override
    public void loggedIn(Organizator user) {
        userListModel.friendLoggedIn(user.getIdOrganizator());
    }

    @Override
    public void refresh(Inscriere inscriere) {
        mainViewFXML.refresh(inscriere);
    }

    public void login(String username, String password) throws Exception {
        System.out.println("USER");
        System.out.println(service.findOrganizator(username,password).getUsername());
        server.login(service.findOrganizator(username,password),this);

    }
}
