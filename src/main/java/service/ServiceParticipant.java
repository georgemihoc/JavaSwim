package service;

import model.Participant;
import repository.IRepository;

public class ServiceParticipant {
    private IRepository<Integer, Participant> repo;
    public ServiceParticipant(IRepository<Integer, Participant> repo){
        this.repo=repo;
    }

    public Iterable<Participant> findAll() {
        return repo.findAll();
    }
}
