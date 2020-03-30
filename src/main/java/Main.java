import client.StartRpcClient;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import service.Service;
import view.ClientController;
import view.MainViewFXML;

import java.util.Iterator;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Concurs de inot");
        FXMLLoader loader=new FXMLLoader(getClass().getResource("mainView.fxml"));
        Pane myPane = (AnchorPane) loader.load();
        MainViewFXML ctrl=loader.getController();

        ClientController clientController = StartRpcClient.main(getParticipantService(),ctrl);
        System.out.println("BOss");

//        Iterator list = MainViewFXML.getAllInstances().iterator();
//        while (list.hasNext()){
//            System.out.println(list.toString());
//        }


        ctrl.setTasksService(getParticipantService(),clientController);
//        ctrl.setTasksService(getParticipantService());
        Scene myScene = new Scene(myPane);
        primaryStage.setScene(myScene);


        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
//                ctrl.close();
            }
        });
        primaryStage.show();
    }
    public static void main(String[] args) throws Exception {
//        StartRpcClient.main();
        System.out.println("boss");
        launch(args);
    }

    static Service getParticipantService(){
        ApplicationContext context=new ClassPathXmlApplicationContext("SwimApp.xml");

//        ParticipantDbRepository repoParticipant = context.getBean(ParticipantDbRepository.class);
//        ProbaDbRepository repoProba = context.getBean(ProbaDbRepository.class);
//        InscriereDbRepository repoInscriere = context.getBean(InscriereDbRepository.class);

        return context.getBean(Service.class);
    }

//    public static void main(String[] args) {
//        ApplicationContext context=new ClassPathXmlApplicationContext("SwimApp.xml");
//        System.out.println("boss");
//        Participant p = new Participant(1,"abc",23);
//        System.out.println(p.getNume());
//
//
//        Properties serverProps=new Properties();
//        try {
//            serverProps.load(new FileReader("bd.config"));
//            //System.setProperties(serverProps);
//
//            System.out.println("Properties set. ");
//            //System.getProperties().list(System.out);
//            serverProps.list(System.out);
//        } catch (IOException e) {
//            System.out.println("Cannot find bd.config "+e);
//        }
////        ParticipantDbRepository repoParticipant=new ParticipantDbRepository(serverProps);
////        ProbaDbRepository repoProba = new ProbaDbRepository(serverProps);
////        InscriereDbRepository repoInscriere = new InscriereDbRepository(serverProps);
//
//        ParticipantDbRepository repoParticipant = context.getBean(ParticipantDbRepository.class);
//        ProbaDbRepository repoProba = context.getBean(ProbaDbRepository.class);
//        InscriereDbRepository repoInscriere = context.getBean(InscriereDbRepository.class);
//
//        ServiceParticipant serviceParticipant = context.getBean(ServiceParticipant.class);
//        for (Participant participant:serviceParticipant.findAll()
//             ) {
//            System.out.println(participant);
//        }
//
//
////        repoParticipant.delete(1);
////        repoProba.delete(1);
////        repoInscriere.delete(1);
////
////        repoParticipant.save(p);
////        repoProba.save(new Proba(1, 100, "liber", 0));
////        repoInscriere.save(new Inscriere(1,"1","sal"));
//    }

}