package client;


import model.Inscriere;
import model.Organizator;
import service.IObserver;
import service.IServices;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;


public class ClientRpcWorker implements Runnable, IObserver {
    private IServices server;
    private Socket connection;

    private ObjectInputStream input;
    private ObjectOutputStream output;
    private volatile boolean connected;
    public ClientRpcWorker(IServices server, Socket connection) {
        this.server = server;
        this.connection = connection;
        try{
            output=new ObjectOutputStream(connection.getOutputStream());
            output.flush();
            input=new ObjectInputStream(connection.getInputStream());
            connected=true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        while(connected){
            try {
                Object request=input.readObject();
                Response response=handleRequest((Request)request);
                if (response!=null){
                    sendResponse(response);
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try {
            input.close();
            output.close();
            connection.close();
        } catch (IOException e) {
            System.out.println("Error "+e);
        }
    }

    private static Response okResponse=new Response.Builder().type(ResponseType.OK).build();
  //  private static Response errorResponse=new Response.Builder().type(ResponseType.ERROR).build();
    private Response handleRequest(Request request){
        Response response=null;
        if (request.type()== RequestType.LOGIN){
            System.out.println("Login request ..."+request.type());
            UserDTO udto=(UserDTO)request.data();
            Organizator user= DTOUtils.getFromDTO(udto);
            try {
                server.login(user,this);
                return okResponse;
            } catch (Exception e) {
                connected=false;
                return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
            }
        }
        if (request.type()== RequestType.ADD_PARTICIPANT){
            System.out.println("SendMessageRequest ...");
            InscriereDTO mdto=(InscriereDTO) request.data();
            Inscriere inscriere= DTOUtils.getFromDTO(mdto);
            try {
                System.out.println("PAS 3");
                System.out.println(connection);
                server.addInscriere(inscriere);
                return okResponse;
            } catch (Exception e) {
                return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
            }
        }
        return response;
    }

    private void sendResponse(Response response) throws IOException{
        System.out.println("sending response "+response);
        output.writeObject(response);
        output.flush();
    }

    @Override
    public void participantInscris(Inscriere inscriere) throws Exception {
        System.out.println("4");
        InscriereDTO mdto= DTOUtils.getDTO(inscriere);
        Response resp=new Response.Builder().type(ResponseType.PARTICIPANT_ADAUGAT).data(mdto).build();
        System.out.println("Message received  "+inscriere);
        try {
            sendResponse(resp);
        } catch (IOException e) {
            throw new Exception("Sending error: "+e);
        }
    }

    @Override
    public void addParticipant(Inscriere inscriere) throws Exception {

    }

    @Override
    public void loggedIn(Organizator user) {
        UserDTO udto= DTOUtils.getDTO(user);
        Response resp=new Response.Builder().type(ResponseType.LOGGED_IN).data(udto).build();
        System.out.println("Friend logged in "+user);
        try {
            sendResponse(resp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void refresh(Inscriere inscriere) {
        System.out.println("MERGE AICI");
    }
}
