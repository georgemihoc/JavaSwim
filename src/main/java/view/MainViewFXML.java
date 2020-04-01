package view;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import model.Inscriere;
import model.Organizator;
import model.Participant;
import model.Proba;
import service.IObserver;
import service.Service;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Vector;


public class MainViewFXML implements Initializable, IObserver {
    private ClientController ctrl;

    public ListView listInscriere;
    public TextField textfieldNume;
    public TextField textFieldVarsta;
    public ListView listInscrisi;
    public AnchorPane loginPane;
    public AnchorPane mainPane;
    public TextField textfieldUsername;
    public PasswordField passwordField;
    @FXML
    private ListView<Proba> list;



    private static Vector allInstances = new Vector();


    public MainViewFXML() {
            allInstances.add(this);

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        list.getSelectionModel().selectedItemProperty().addListener(
                (observable,oldvalue,newValue)->showInscrisi(newValue) );
    }

    private void showInscrisi(Proba proba) {
        if (proba==null)
            clearFields();
        else{
            listInscrisi.getItems().clear();
            for (Participant participant:
                 service.findAllInscrisi(proba.getIdProba())) {
                String lista = "";
                for (Proba p:
                        service.findAllProbeInscris(participant.getIdParticipant())) {
                        if(lista!= "")
                            lista+= ", ";
                        lista += p.getLungime()+ "m "+ p.getStil()+ " ";
                }
                listInscrisi.getItems().add(participant.getNume() + " | " + participant.getVarsta()  + " | Probe: " + lista);
            }


        }
    }
    private Service service;
    public void setTasksService(Service service , ClientController ctrl){
        this.service=service;
        this.ctrl = ctrl;

        initData();
        mainPane.setVisible(false);
    }

    public void initData() {
        list.getItems().clear();
        listInscriere.getItems().clear();
        listInscriere.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        listInscrisi.getItems().clear();

//        list.getItems().setAll()
        for(Proba proba : service.findAllProba()){
            list.getItems().add(proba);
            listInscriere.getItems().add(proba.getLungime()+"m " + proba.getStil());
        }
//        for(Proba proba: ctrl.getProbeListModel()){
//            list.getItems().add(proba);
//        }
//        list.getItems().setAll(ctrl.getProbeListModel());

        System.out.println(listInscriere.getSelectionModel().getSelectedItems());


//        table.getItems().clear();
//        for (Participant participant:service.findAllParticipant()) {
//            table.getItems().add(participant);
//        }
    }


    public void handleAddButton(ActionEvent actionEvent) throws Exception {
        String nume = textfieldNume.getText();
        int varsta = Integer.parseInt(textFieldVarsta.getText());
//        service.addParticipant(nume,varsta);
        ObservableList indexes = listInscriere.getSelectionModel().getSelectedIndices();
        for (Object index:indexes
             ) {
//            service.addInscriere(nume,varsta,(int)index+1);
//            wait(5);
//            ctrl.participantInscris(service,service.findInscriere(service.findParticipant(nume,varsta).getIdParticipant(),(int) index + 1));
            ctrl.participantInscris(service,nume,varsta,(int) index + 1);
        }
        clearFields();
    }
    public void clearFields(){
        textfieldNume.clear();
        textFieldVarsta.clear();
    }

    public void handleRefreshButton(ActionEvent actionEvent) {
        initData();
    }

    public void handleLoginButton(ActionEvent actionEvent) throws Exception {
        String username = textfieldUsername.getText();
        String password = passwordField.getText();
        if( service.validateLogin(username,password))
        {
            textfieldUsername.clear();
            passwordField.clear();
            loginPane.setVisible(false);
            mainPane.setVisible(true);
            ctrl.login(username,password);
        }
        else {
            showErrorMessage("Invalid credentials");
        }
    }

    public void enterKey(javafx.scene.input.KeyEvent keyEvent) throws Exception {
        if(keyEvent.getCode().toString().equals("ENTER"))
            handleLoginButton(null);
    }

    public void handleLogout(ActionEvent actionEvent) {
        mainPane.setVisible(false);
        loginPane.setVisible(true);
    }

    void showErrorMessage(String text){
        Alert message=new Alert(Alert.AlertType.ERROR);
        message.setTitle("Mesaj eroare");
        message.setContentText(text);
        message.showAndWait();
    }

    @Override
    public void participantInscris(Service service,String nume, int varsta, int idProba) throws Exception {

    }

    @Override
    public void loggedIn(Organizator user) {
//        initData();
    }

    @Override
    public void refresh(Inscriere inscriere) {
        Platform.runLater(new Runnable() {
            public void run() {
//                for(Proba proba : service.findAllProba()){
//                    list.getItems().add(proba);
//                }
//                list.getItems().remove(inscriere.getIdProba()-1);
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }

                Proba p =service.findProba(inscriere.getIdProba());
                System.out.println(p);
                list.getItems().remove(p.getIdProba()-1);
                list.getItems().add(p.getIdProba()-1,p);

            }

        });
    }

}
