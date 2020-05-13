package rest.client;

import model.Proba;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.Callable;


public class ProbeClient {
    public static final String URL = "http://localhost:8080/swim/probe";

    private RestTemplate restTemplate = new RestTemplate();

    private <T> T execute(Callable<T> callable) {
        try {
            return callable.call();
        } catch (Exception e) { // server down, resource exception
            try {
                throw new Exception(e);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }

    public Proba[] getAll() {
        return execute(() -> restTemplate.getForObject(URL, Proba[].class));
    }

    public Proba getById(Integer id) {
        String urlGetById= URL + "/" + id;
        return execute(() -> restTemplate.getForObject(urlGetById, Proba.class));
    }

    public Proba create(Proba user) {
        return execute(() -> restTemplate.postForObject(URL, user, Proba.class));
    }

    public void update(Proba proba) {
        String urlUpdate= URL + "/" + proba.getIdProba();
        execute(() -> {
            restTemplate.put(urlUpdate, proba);
            return null;
        });
    }

    public void delete(int id) {
        String urlDelete= URL + "/" + id;
        execute(() -> {
            restTemplate.delete(urlDelete, id);
            System.out.println("Proba DELETED");
            return null;
        });
    }

}
