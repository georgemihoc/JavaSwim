import model.Inscriere;
import model.Organizator;
import model.Participant;
import model.Proba;

import services.IObserver;
import services.IServices;
import repository.*;
import services.SwimException;

import java.rmi.RemoteException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class ChatServicesImpl implements IServices {

    private IRepository<Integer, Participant> participantRepository;
    private IRepository<Integer, Proba> probaRepository;
    private IRepository<Integer, Inscriere> inscriereRepository;
    private IRepository<Integer, Organizator> organizatorRepository;
    private Map<Integer, IObserver> loggedClients;

    public ChatServicesImpl(IRepository<Integer, Participant> repoParticipant, IRepository<Integer, Proba> repoProba, IRepository<Integer, Inscriere> repoInscriere, IRepository<Integer, Organizator> repoOrganizator) {

        participantRepository= repoParticipant;
        probaRepository= repoProba;
        inscriereRepository= repoInscriere;
        organizatorRepository= repoOrganizator;
        loggedClients= new ConcurrentHashMap<>();;

    }


    private final int defaultThreadsNo=10;
    @Override
    public void addInscriere (String nume, int varsta, int idProba) throws Exception {
        System.out.println("PAS 4");
        System.out.println("NR CLIENTI LOGATI "+loggedClients.entrySet().size());
//        service.addInscriere(participantRepository.findOne(inscriere.getIdParticipant()).getNume(),participantRepository.findOne(inscriere.getIdParticipant()).getVarsta(),inscriere.getIdProba());
        System.out.println(nume+varsta+idProba);
        if(findParticipant(nume, varsta) == null){
            try {
                Participant p = new Participant(participantRepository.size()+1, nume, varsta);
                participantRepository.save(p);
            }
            catch (Exception except){
            }
        }
        int idParticipant = findParticipant(nume,varsta).getIdParticipant();
        Inscriere inscriere = new Inscriere(inscriereRepository.size()+1, idParticipant,idProba);
        if(findInscriere(idParticipant,idProba)==null) {
            inscriereRepository.save(inscriere);
            for (Proba proba :
                    probaRepository.findAll()) {
                if (proba.getIdProba() == idProba) {
                    probaRepository.update(idProba, new Proba(idProba, proba.getLungime(), proba.getStil(), proba.getNrParticipanti() + 1));
                }
            }
            ExecutorService executor = Executors.newFixedThreadPool(defaultThreadsNo);
            for (Map.Entry<Integer, IObserver> entry : loggedClients.entrySet()) {
                System.out.println(entry.getKey() + "/" + entry.getValue());
                IObserver chatClient = loggedClients.get(entry.getKey());
                if (chatClient != null)
                    executor.execute(() -> {
                        try {
                            //chatClient.participantInscris(nume, varsta, idProba);
                            chatClient.inscriereEfectuata(inscriere);
                        } catch (RemoteException e) {
                            System.err.println("Error notifying friend " + e);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
            }
        }
    }

    private Participant findParticipant(String nume, int varsta) {
        for (Participant participant:
             participantRepository.findAll()) {
            if(participant.getNume().equals(nume) && participant.getVarsta()==varsta)
                return participant;
        }
        return null;
    }
    private Inscriere findInscriere(int idParticipant, int idProba){
        for (Inscriere inscriere: inscriereRepository.findAll()) {
            if(inscriere.getIdParticipant() == idParticipant && inscriere.getIdProba() == idProba)
                return inscriere;
        }
        return null;
    }

    @Override
    public void login(Organizator user,IObserver client) throws SwimException {
        int id=-1;

        for (Organizator o:
             organizatorRepository.findAll()) {
            if(o.getPassword().equals(user.getPassword()) && o.getUsername().equals(user.getUsername()))
                id= o.getIdOrganizator();
        }
        if(id == -1){
            throw new SwimException("Invalid Credentials");
        }
        else {
            Organizator userOk = organizatorRepository.findOne(id);
            if(userOk != null){
                if(loggedClients.containsKey(id))
                    throw  new SwimException("User already logged in");
                loggedClients.put(id, client);
                System.out.println("S-a LOGAT" + client);
            }

        }
    }
    public synchronized void logout(Organizator user,IObserver client) throws Exception {
        int id = -1;
        for (Organizator o:
                organizatorRepository.findAll()) {
            if(o.getPassword().equals(user.getPassword()) && o.getUsername().equals(user.getUsername()))
                id= o.getIdOrganizator();
        }
        System.out.println("User " + id + "logged out");
        IObserver localClient=loggedClients.remove(id);
        if (localClient==null)
            throw new Exception("User "+id+" is not logged in.");
    }
    public synchronized Proba[] getProbe() throws Exception {
        List<Proba> list = new ArrayList<>();
        for (Proba proba :
                probaRepository.findAll()) {
            list.add(proba);
        }
        System.out.println("Size "+list.size());
        return list.toArray(new Proba[list.size()]);
    }

    @Override
    public Participant[] getParticipanti() throws Exception {
        List<Participant> list = new ArrayList<>();
        for (Participant participant :
                participantRepository.findAll()) {
            list.add(participant);
        }
        System.out.println("Size "+list.size());
        return list.toArray(new Participant[list.size()]);
    }

    @Override
    public Inscriere[] getInscrieri() throws Exception {
        List<Inscriere> list = new ArrayList<>();
        for (Inscriere inscriere :
                inscriereRepository.findAll()) {
            list.add(inscriere);
        }
        System.out.println("Size "+list.size());
        return list.toArray(new Inscriere[list.size()]);
    }
}