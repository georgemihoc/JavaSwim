import model.Participant;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import repository.InscriereDbRepository;
import repository.ParticipantDbRepository;
import repository.ProbaDbRepository;
import service.ServiceParticipant;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class Main {

    public static void main(String[] args) {
        ApplicationContext context=new ClassPathXmlApplicationContext("SwimApp.xml");
        System.out.println("boss");
        Participant p = new Participant(1,"abc",23);
        System.out.println(p.getNume());


        Properties serverProps=new Properties();
        try {
            serverProps.load(new FileReader("bd.config"));
            //System.setProperties(serverProps);

            System.out.println("Properties set. ");
            //System.getProperties().list(System.out);
            serverProps.list(System.out);
        } catch (IOException e) {
            System.out.println("Cannot find bd.config "+e);
        }
//        ParticipantDbRepository repoParticipant=new ParticipantDbRepository(serverProps);
//        ProbaDbRepository repoProba = new ProbaDbRepository(serverProps);
//        InscriereDbRepository repoInscriere = new InscriereDbRepository(serverProps);

        ParticipantDbRepository repoParticipant = context.getBean(ParticipantDbRepository.class);
        ProbaDbRepository repoProba = context.getBean(ProbaDbRepository.class);
        InscriereDbRepository repoInscriere = context.getBean(InscriereDbRepository.class);

        ServiceParticipant serviceParticipant = context.getBean(ServiceParticipant.class);
        for (Participant participant:serviceParticipant.findAll()
             ) {
            System.out.println(participant);
        }


//        repoParticipant.delete(1);
//        repoProba.delete(1);
//        repoInscriere.delete(1);
//
//        repoParticipant.save(p);
//        repoProba.save(new Proba(1, 100, "liber", 0));
//        repoInscriere.save(new Inscriere(1,"1","sal"));
    }
}