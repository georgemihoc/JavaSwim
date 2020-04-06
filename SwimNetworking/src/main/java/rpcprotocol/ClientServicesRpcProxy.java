package rpcprotocol;


import dto.*;
import model.Inscriere;
import model.Organizator;

import model.Participant;
import model.Proba;
import services.IObserver;
import services.IServices;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;


public class ClientServicesRpcProxy implements IServices {
    private String host;
    private int port;

    private IObserver client;

    private ObjectInputStream input;
    private ObjectOutputStream output;
    private Socket connection;

    private BlockingQueue<Response> qresponses;
    private volatile boolean finished;
    public ClientServicesRpcProxy(String host, int port) {
        this.host = host;
        this.port = port;
        qresponses=new LinkedBlockingQueue<Response>();
    }
    @Override
    public void login(Organizator user,IObserver client) throws Exception {
        System.out.println("1");

        initializeConnection();
        UserDTO udto= DTOUtils.getDTO(user);
        Request req=new Request.Builder().type(RequestType.LOGIN).data(udto).build();
        sendRequest(req);
        Response response=readResponse();
        if (response.type()== ResponseType.OK){
            this.client=client;
            return;
        }
        if (response.type()== ResponseType.ERROR){
            String err=response.data().toString();
            closeConnection();
            throw new Exception(err);
        }
    }

    @Override
    public Proba[] getProbe() throws Exception {
        Request req = new Request.Builder().type(RequestType.GET_PROBE).build();
        sendRequest(req);
        Response response = readResponse();
        if (response.type()== ResponseType.ERROR){
            String err=response.data().toString();
            throw new Exception(err);
        }
        ProbaDTO[] frDTO=(ProbaDTO[])response.data();
        Proba[] friends= DTOUtils.getFromDTO(frDTO);
        return friends;
    }

    @Override
    public Participant[] getParticipanti() throws Exception {
        Request req = new Request.Builder().type(RequestType.GET_PARTICIPANTI).build();
        sendRequest(req);
        Response response = readResponse();
        if (response.type()== ResponseType.ERROR){
            String err=response.data().toString();
            throw new Exception(err);
        }
        ParticipantDTO[] frDTO=(ParticipantDTO[])response.data();
        Participant[] friends= DTOUtils.getFromDTO(frDTO);
        return friends;
    }
    @Override
    public Inscriere[] getInscrieri() throws Exception {
        Request req = new Request.Builder().type(RequestType.GET_INSCRIERI).build();
        sendRequest(req);
        Response response = readResponse();
        if (response.type()== ResponseType.ERROR){
            String err=response.data().toString();
            throw new Exception(err);
        }
        InscriereDTO[] frDTO=(InscriereDTO[])response.data();
        Inscriere[] friends= DTOUtils.getFromDTO(frDTO);
        return friends;
    }


    @Override
    public void addInscriere(String nume, int varsta , int idProba) throws Exception {
//        initializeConnection();
        System.out.println(" PAS 2");
        Inscriere i = new Inscriere(0,0,idProba);
        InscriereDTO inscriereDTO = DTOUtils.getDTO(i,nume,varsta);
        Request req=new Request.Builder().type(RequestType.ADD_PARTICIPANT).data(inscriereDTO).build();
        sendRequest(req);
        Response response=readResponse();
        if (response.type()== ResponseType.ERROR){
            String err=response.data().toString();
            throw new Exception(err);
        }
    }

    private void closeConnection() {
        finished=true;
        try {
            input.close();
            output.close();
            connection.close();
            client=null;
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void sendRequest(Request request) throws Exception {
        try {
            System.out.println("REQUESTUL DIN PROXY : " + request);
            output.writeObject(request);
            output.flush();
        } catch (IOException e) {
            throw new Exception("Error sending object "+e);
        }

    }

    private Response readResponse() throws Exception {
        Response response=null;
        try{

            response=qresponses.take();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return response;
    }
    private void initializeConnection() throws Exception {
        try {
            connection=new Socket(host,port);
            output=new ObjectOutputStream(connection.getOutputStream());
            output.flush();
            input=new ObjectInputStream(connection.getInputStream());
            finished=false;
            startReader();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void startReader(){
        Thread tw=new Thread(new ReaderThread());
        tw.start();
    }

    private void handleUpdate(Response response){
        if (response.type()== ResponseType.PARTICIPANT_ADAUGAT){
            Inscriere inscriere= DTOUtils.getFromDTO((InscriereDTO) response.data());
            try {
                client.refresh(inscriere);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (response.type()== ResponseType.LOGGED_IN){
            Organizator friend= DTOUtils.getFromDTO((UserDTO)response.data());
            System.out.println("Friend logged out "+friend);
            try {
                client.loggedIn(friend);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    private boolean isUpdate(Response response){
        return response.type()== ResponseType.PARTICIPANT_ADAUGAT || response.type()== ResponseType.LOGGED_IN;
    }


    private class ReaderThread implements Runnable{
        public void run() {
            while(!finished){
                try {
                    Object response=input.readObject();
                    System.out.println("response received "+response);
                    if (isUpdate((Response)response)){
                        handleUpdate((Response)response);
                    }else{
                        try {
                            qresponses.put((Response)response);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (IOException | ClassNotFoundException e) {
                    System.out.println("Reading error "+e);
                }
            }
        }
    }
}
