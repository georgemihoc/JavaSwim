package client;


import service.IServices;
import service.Service;
import view.ClientController;
import view.MainViewFXML;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Properties;


public class StartRpcClient {
    private static int defaultChatPort=55556;
    private static String defaultServer="localhost";
    public static ClientController main(Service service,MainViewFXML view) throws Exception {
        Properties clientProps=new Properties();
        try {
            clientProps.load(StartRpcClient.class.getResourceAsStream("/client.properties"));
            System.out.println("Client properties set. ");
            clientProps.list(System.out);
        } catch (IOException e) {
            System.err.println("Cannot find chatclient.properties "+e);
            return null;
        }
        String serverIP=clientProps.getProperty("chat.server.host",defaultServer);
        int serverPort=defaultChatPort;
        try{
            serverPort=Integer.parseInt(clientProps.getProperty("chat.server.port"));
        }catch(NumberFormatException ex){
            System.err.println("Wrong port number "+ex.getMessage());
            System.out.println("Using default port: "+defaultChatPort);
        }
        System.out.println("Using server IP "+serverIP);
        System.out.println("Using server port "+serverPort);

//        try {
//            InetAddress addr;
//            Socket sock = new Socket("localhost", 55556);
//            addr = sock.getInetAddress();
//            System.out.println("Connected to " + addr);
////            launch(args);
////            Main.run();
//            sock.close();
//        } catch (java.io.IOException e) {
//            System.out.println("Can't connect to server");
//            System.out.println(e);
//        }
        IServices server=new ClientServicesRpcProxy(serverIP, serverPort);

        ClientController ctrl=new ClientController(server,service,view);

        return ctrl;
//
//


    }

//    @Override
//    public void start(Stage primaryStage) throws Exception {
//        primaryStage.setTitle("Concurs de inot");
//        FXMLLoader loader=new FXMLLoader(getClass().getResource("mainView.fxml"));
//        Pane myPane = (AnchorPane) loader.load();
//        MainViewFXML ctrl=loader.getController();
//
//
//
//        ctrl.setTasksService(getParticipantService());
//        Scene myScene = new Scene(myPane);
//        primaryStage.setScene(myScene);
//
//
//        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
//            public void handle(WindowEvent we) {
////                ctrl.close();
//            }
//        });
//        primaryStage.show();
//    }
//    static Service getParticipantService(){
//        ApplicationContext context=new ClassPathXmlApplicationContext("SwimApp.xml");
//
////        ParticipantDbRepository repoParticipant = context.getBean(ParticipantDbRepository.class);
////        ProbaDbRepository repoProba = context.getBean(ProbaDbRepository.class);
////        InscriereDbRepository repoInscriere = context.getBean(InscriereDbRepository.class);
//
//        return context.getBean(Service.class);
//    }
}
