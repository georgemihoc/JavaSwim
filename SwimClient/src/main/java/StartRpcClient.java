
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import services.IServices;
import services.Service;


import java.io.IOException;
import java.util.Properties;


public class StartRpcClient {
    private static int defaultChatPort=55556;
    private static String defaultServer="localhost";
    public static ClientController main(MainViewFXML view) throws Exception {
//        Properties clientProps=new Properties();
//        try {
//            clientProps.load(StartRpcClient.class.getResourceAsStream("/client.properties"));
//            System.out.println("Client properties set. ");
//            clientProps.list(System.out);
//        } catch (IOException e) {
//            System.err.println("Cannot find chatclient.properties "+e);
//            return null;
//        }
//        String serverIP=clientProps.getProperty("chat.server.host",defaultServer);
//        int serverPort=defaultChatPort;
//        try{
//            serverPort=Integer.parseInt(clientProps.getProperty("chat.server.port"));
//        }catch(NumberFormatException ex){
//            System.err.println("Wrong port number "+ex.getMessage());
//            System.out.println("Using default port: "+defaultChatPort);
//        }
//        System.out.println("Using server IP "+serverIP);
//        System.out.println("Using server port "+serverPort);
//
//
//        IServices server=new ClientServicesRpcProxy(serverIP, serverPort);
//
//        ClientController ctrl=new ClientController(server,view);
//
//        return ctrl;
        ApplicationContext factory = new ClassPathXmlApplicationContext("classpath:spring-client.xml");
        IServices server=(IServices) factory.getBean("swimService");
        System.out.println("Obtained a reference to remote chat server");
        ClientController ctrl=new ClientController(server,view);

        return ctrl;

    }
}
