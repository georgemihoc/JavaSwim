package start;

import model.Proba;
import rest.client.ProbeClient;
import org.springframework.web.client.RestClientException;


public class StartRestClient {
    private final static ProbeClient probeClient=new ProbeClient();
    public static void main(String[] args) {
        Proba probaTest = new Proba(9,100,"spate",5);
        try{
        show(()-> System.out.println(probeClient.create(probaTest)));
        show(()->{
            Proba[] list=probeClient.getAll();
            for(Proba proba : list){
                System.out.println(proba.getIdProba()+": "+proba.getStil());
            }
        });

        System.out.println("Before update:");
        show(()-> System.out.println(probeClient.getById(probaTest.getIdProba())));

        System.out.println("After update:");
        Proba probaUpdate = new Proba(9,100,"spate",500);
        show(()-> probeClient.update(probaUpdate));
        show(()-> System.out.println(probeClient.getById(probaTest.getIdProba())));


        show(()-> probeClient.delete(probaTest.getIdProba()));


        }catch(RestClientException ex){
            System.out.println("Exception ... "+ex.getMessage());
        }
    }

    private static void show(Runnable task) {
        try {
            task.run();
        } catch (Exception e) {
            System.out.println("Service exception"+ e);
        }
    }
}
