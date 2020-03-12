package service;

import model.Inscriere;
import model.Organizator;
import model.Participant;
import model.Proba;
import repository.IRepository;

import java.util.ArrayList;
import java.util.List;

public class Service {
    private IRepository<Integer, Participant> repoParticipant;
    private IRepository<Integer, Proba> repoProba;
    private IRepository<Integer, Inscriere> repoInscriere;
    private IRepository<Integer, Organizator> repoOrganizator;
//    public ServiceParticipant(IRepository<Integer, Participant> repoParticipant){
//        this.repoParticipant=repoParticipant;
//    }


    public Service(IRepository<Integer, Participant> repoParticipant, IRepository<Integer, Proba> repoProba, IRepository<Integer, Inscriere> repoInscriere,IRepository<Integer, Organizator> repoOrganizator) {
        this.repoParticipant = repoParticipant;
        this.repoProba = repoProba;
        this.repoInscriere = repoInscriere;
        this.repoOrganizator = repoOrganizator;
    }

    public int getSize(IRepository repo){
        return (int) repo.findAll().spliterator().getExactSizeIfKnown();
    }
    public Participant addParticipant(String nume, int varsta){
        if(findParticipant(nume, varsta) != null)
            return null;
        try {
            System.out.println((getSize(repoParticipant)));
            Participant p = new Participant(getSize(repoParticipant)+1, nume, varsta);
            repoParticipant.save(p);
            return p;
        }
        catch (Exception except){
            return null;
        }
    }
    public Participant findParticipant(String nume, int varsta){
        for (Participant p:
                repoParticipant.findAll()) {
            if(p.getNume().equals(nume) && p.getVarsta() == varsta)
                return p;
        }
        return null;
    }
    public Inscriere addInscriere(String nume,int varsta, int idProba){
        Participant p = findParticipant(nume,varsta);
        Inscriere inscriere = new Inscriere(getSize(repoInscriere)+1,p.getIdParticipant(),idProba);
        if(findInscriere(p.getIdParticipant(),idProba)!= null){
            return null;
        }
        repoInscriere.save(inscriere);
        for (Proba proba:
             repoProba.findAll()) {
            if(proba.getIdProba()==idProba){
                repoProba.update(idProba,new Proba(idProba,proba.getLungime(),proba.getStil(),proba.getNrParticipanti()+1));
            }
        }
        return inscriere;
    }

    private Inscriere findInscriere(int idParticipant, int idProba) {
        for (Inscriere inscriere :
                repoInscriere.findAll()) {
            if(inscriere.getIdParticipant() == idParticipant && inscriere.getIdProba() == idProba)
                return inscriere;
        }
        return null;
    }

    public Iterable<Participant> findAllInscrisi(int idProba){
        List<Participant> list = new ArrayList<>();
        for (Inscriere inscriere:
             repoInscriere.findAll()) {
            if( inscriere.getIdProba() == idProba) {
                list.add(repoParticipant.findOne(inscriere.getIdParticipant()));
            }
        }
        return list;
    }
    public Iterable<Proba> findAllProbeInscris(int idParticipant){
        List<Proba> list = new ArrayList<>();
        for (Inscriere inscriere:
             repoInscriere.findAll()) {
            if(inscriere.getIdParticipant() == idParticipant)
                list.add(repoProba.findOne(inscriere.getIdProba()));
        }
        return list;
    }

    public Iterable<Participant> findAllParticipant() {
        return repoParticipant.findAll();
    }
    public  Iterable<Proba> findAllProba(){
        return repoProba.findAll();
    }

    public boolean validateLogin(String username, String password) {
        for (Organizator organizator :
                repoOrganizator.findAll()) {
            if (organizator.getUsername().equals(username) && organizator.getPassword().equals(password))
                return true;
        }
        return false;
    }
}
