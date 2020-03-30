package server.utils;


import client.ClientRpcWorker;
import javafx.application.Platform;
import service.IServices;
import service.Service;

import java.net.Socket;


public class ChatRpcConcurrentServer extends AbsConcurrentServer {
    private IServices server;
    public ChatRpcConcurrentServer(int port, IServices server) {
        super(port);
        this.server = server;
        System.out.println("Chat- ChatRpcConcurrentServer");
    }

    @Override
    protected Thread createWorker(Socket client) {
        ClientRpcWorker worker=new ClientRpcWorker(server, client);
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
