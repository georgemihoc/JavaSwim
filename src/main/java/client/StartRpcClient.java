package client;


import service.IServices;
import service.Service;
import view.ClientController;
import view.MainViewFXML;

import java.io.IOException;
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


        IServices server=new ClientServicesRpcProxy(serverIP, serverPort);

        ClientController ctrl=new ClientController(server,service,view);

        return ctrl;

    }
}
