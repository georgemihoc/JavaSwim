package server.utils;


import client.ClientRpcWorker;
import javafx.application.Platform;
import service.IServices;
import service.Service;

import java.net.Socket;


public class ChatRpcConcurrentServer extends AbsConcurrentServer {
    private IServices server;
    private Service service;
    public ChatRpcConcurrentServer(int port, IServices server,Service service) {
        super(port);
        this.server = server;
        this.service =service;
        System.out.println("Chat- ChatRpcConcurrentServer");
    }

    @Override
    protected Thread createWorker(Socket client) {
        ClientRpcWorker worker=new ClientRpcWorker(server,service, client);
//        ClientRpcReflectionWorker worker=new ClientRpcReflectionWorker(server, client);
        Thread tw=new Thread(worker);
//        Platform.runLater(tw);
        return tw;
    }

    @Override
    public void stop(){
        System.out.println("Stopping services ...");
    }
}
