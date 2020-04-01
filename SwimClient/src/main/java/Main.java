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
import services.Service;


public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Concurs de inot");
        FXMLLoader loader=new FXMLLoader(getClass().getResource("mainView.fxml"));
        Pane myPane = (AnchorPane) loader.load();
        MainViewFXML ctrl=loader.getController();

        ClientController clientController = StartRpcClient.main(getParticipantService(),ctrl);
        System.out.println("BOss");


        ctrl.setTasksService(getParticipantService(),clientController);
        Scene myScene = new Scene(myPane);
        primaryStage.setScene(myScene);
        primaryStage.setAlwaysOnTop(true);


        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
//                ctrl.close();
            }
        });
        primaryStage.show();
    }
    public static void main(String[] args) throws Exception {
        System.out.println("boss");
        launch(args);
    }

    static Service getParticipantService(){
        ApplicationContext context=new ClassPathXmlApplicationContext("SwimApp.xml");

        return context.getBean(Service.class);
    }

//    public static void main(String[] args) {
//        ApplicationContext context=new ClassPathXmlApplicationContext("SwimApp.xml");
//        System.out.println("boss");
//        model.Participant p = new model.Participant(1,"abc",23);
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
//        for (model.Participant participant:serviceParticipant.findAll()
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
////        repoProba.save(new model.Proba(1, 100, "liber", 0));
////        repoInscriere.save(new model.Inscriere(1,"1","sal"));
//    }

}