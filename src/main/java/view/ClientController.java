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
//        probeListModel = new ProbeListModel(service);

//        friendsModel=new FriendsListModel();
//        messagesModel=new MessagesListModel();
    }


//    public void messageReceived(Message message) throws ChatException {
//        messagesModel.newMessage(message.getSender().getId(), message.getText());
//    }
//
//    public void friendLoggedIn(User friend) throws ChatException {
//        friendsModel.friendLoggedIn(friend.getId());
//    }
//
//    public void friendLoggedOut(User friend) throws ChatException {
//        friendsModel.friendLoggedOut(friend.getId());
//    }
//
//    public void logout() {
//        try {
//            server.logout(user, this);
//        } catch (ChatException e) {
//            System.out.println("Logout error "+e);
//        }
//    }
//
//    public void login(String id, String pass) throws ChatException {
//        User userL= new User(id,pass,"");
//        server.login(userL,this);
//        user=userL;
//        User[] loggedInFriends=server.getLoggedFriends(user);
//        System.out.println("Logged friends "+loggedInFriends.length);
//        for(User us : loggedInFriends){
//            friendsModel.friendLoggedIn(us.getId());
//        }
//
//    }

    public void sendMessage(Inscriere inscriere) throws Exception{
//        messagesModel.newMessage(user.getId(), txtMsg);
//        User sender=new User(user.getId());
//        User receiver=new User((String)friendsModel.getElementAt(indexFriend));
//        Message message=new Message(sender,txtMsg,receiver);
//        server.addParticipant(inscriere);
    }



    @Override
    public void participantInscris(Inscriere inscriere) throws Exception {
        System.out.println("PAS 1");
        server.addInscriere(inscriere);

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
