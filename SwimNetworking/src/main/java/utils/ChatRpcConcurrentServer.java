package utils;

import services.IServices;
import services.Service;
import rpcprotocol.ClientRpcWorker;


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
        ClientRpcWorker worker=new ClientRpcWorker(this.server,service, client);
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
