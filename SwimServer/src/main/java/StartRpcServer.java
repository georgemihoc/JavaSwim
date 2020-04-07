import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import repository.InscriereDbRepository;
import repository.OrganizatorDbRepository;
import repository.ParticipantDbRepository;
import repository.ProbaDbRepository;

import services.IServices;
import services.Service;


import java.io.IOException;
import java.rmi.ServerException;
import java.util.Properties;

public class StartRpcServer {
    private static int defaultPort=55555;
    public static void main(String[] args) throws ServerException {
//        // UserRepository userRepo=new UserRepositoryMock();
//        Properties serverProps=new Properties();
//        try {
//            serverProps.load(StartRpcServer.class.getResourceAsStream("/server.properties"));
//            System.out.println("Server properties set. ");
//            serverProps.list(System.out);
//        } catch (IOException e) {
//            System.err.println("Cannot find chatserver.properties "+e);
//            return;
//        }
////        UserRepository userRepo=new UserRepositoryJdbc(serverProps);
////        MessageRepository messRepo=new MessageRepositoryJdbc(serverProps);
////        IChatServices chatServerImpl=new ChatServicesImpl(userRepo, messRepo);
//        ApplicationContext context=new ClassPathXmlApplicationContext("SwimApp.xml");
//
//        ParticipantDbRepository repoParticipant = context.getBean(ParticipantDbRepository.class);
//        ProbaDbRepository repoProba = context.getBean(ProbaDbRepository.class);
//        InscriereDbRepository repoInscriere = context.getBean(InscriereDbRepository.class);
//        OrganizatorDbRepository repoOrganizator = context.getBean(OrganizatorDbRepository.class);
//        IServices serviceImpl = new ChatServicesImpl(repoParticipant,repoProba,repoInscriere,repoOrganizator);
//
////        services.Service service =  context.getBean(services.Service.class);
//        int chatServerPort=defaultPort;
//        try {
//            chatServerPort = Integer.parseInt(serverProps.getProperty("chat.server.port"));
//        }catch (NumberFormatException nef){
//            System.err.println("Wrong  Port Number"+nef.getMessage());
//            System.err.println("Using default port "+defaultPort);
//        }
//        System.out.println("Starting server on port: "+chatServerPort);
//        AbstractServer server = new ChatRpcConcurrentServer(chatServerPort, serviceImpl,context.getBean(Service.class));
//        try {
//            server.start();
//            System.out.println("BOSS");
//        } finally {
//            server.stop();
//        }
        ApplicationContext factory = new ClassPathXmlApplicationContext("classpath:spring-server.xml");
    }
}
