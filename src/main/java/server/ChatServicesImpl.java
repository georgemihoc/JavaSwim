package server;



import model.Inscriere;
import model.Organizator;
import model.Participant;
import model.Proba;
import repository.IRepository;
import service.IObserver;
import service.IServices;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class ChatServicesImpl implements IServices {

    private IRepository<Integer, Participant> participantRepository;
    private IRepository<Integer, Proba> probaRepository;
    private IRepository<Integer, Inscriere> inscriereRepository;
    private IRepository<Integer, Organizator> organizatorRepository;
    private Map<Integer, IObserver> loggedClients;

    public ChatServicesImpl(IRepository<Integer, Participant> repoParticipant, IRepository<Integer, Proba> repoProba, IRepository<Integer, Inscriere> repoInscriere,IRepository<Integer, Organizator> repoOrganizator) {

        this.participantRepository= repoParticipant;
        probaRepository= repoProba;
        inscriereRepository= repoInscriere;
        organizatorRepository= repoOrganizator;
        loggedClients= new ConcurrentHashMap<>();;

    }


//    public synchronized void login(Organizator user, IObserver client) throws Exception {
//        Organizator userR=organizatorRepository.findOne(user.getIdOrganizator());
//        loggedClients.put(user.getIdOrganizator(),client);
////        if (userR!=null){
////            if(loggedClients.get(user.getId())!=null)
////                throw new ChatException("User already logged in.");
////            loggedClients.put(user.getId(), client);
////            notifyFriendsLoggedIn(user);
////        }else
////            throw new ChatException("Authentication failed.");
//    }




//    public synchronized void sendMessage(Inscriere inscriere) throws Exception {
//        IObserver receiverClient=loggedClients.get(id_receiver);
//        if (receiverClient!=null) {      //the receiver is logged in
//            messageRepository.save(message);
//            receiverClient.messageReceived(message);
//        }
//        else
//            throw new ChatException("User "+id_receiver+" not logged in.");
//    }
//
//    public synchronized void logout(User user, IChatObserver client) throws ChatException {
//        IChatObserver localClient=loggedClients.remove(user.getId());
//        if (localClient==null)
//            throw new ChatException("User "+user.getId()+" is not logged in.");
//        notifyFriendsLoggedOut(user);
//    }

    private final int defaultThreadsNo=10;
    @Override
    public void addInscriere(Inscriere inscriere) throws Exception {
        System.out.println("PAS 4");
        System.out.println("NR CLIENTI LOGATI "+loggedClients.entrySet().size());
        ExecutorService executor= Executors.newFixedThreadPool(defaultThreadsNo);
        for (Map.Entry<Integer, IObserver> entry : loggedClients.entrySet()) {
            System.out.println(entry.getKey() + "/" + entry.getValue());
            IObserver chatClient=loggedClients.get(entry.getKey());
            if (chatClient!=null)
                executor.execute(() -> {
                    try {
                        chatClient.participantInscris(inscriere);
//                        chatClient.refresh();
                    } catch (Exception e) {
                        System.err.println("Error notifying friend " + e);
                    }
                });
        }
    }

    @Override
    public void login(Organizator user,IObserver client) {
//        Organizator userR=organizatorRepository.findOne(user.getIdOrganizator());
//        loggedClients.put(user.getIdOrganizator(),client);
//        if (userR!=null){
//            if(loggedClients.get(user.getId())!=null)
//                throw new ChatException("User already logged in.");
//            notifyFriendsLoggedIn(user);
//        }else
//            throw new ChatException("Authentication failed.");
        int id=0;
        for (Organizator o:
             organizatorRepository.findAll()) {
            if(o.getPassword().equals(user.getPassword()) && o.getUsername().equals(user.getUsername()))
                id= o.getIdOrganizator();
        }
        loggedClients.put(id, client);
        System.out.println("S-a LOGAT" + client);

    }

    public synchronized Map<Integer, IObserver> getLoggedUsers() {
        return loggedClients;
    }
}