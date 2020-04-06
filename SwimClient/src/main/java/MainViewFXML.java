import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import javafx.stage.Stage;
import javafx.stage.Window;
import model.*;
import services.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


public class MainViewFXML extends Window implements Initializable, IObserver {
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
    private List<Proba> listaProbe;
    private List<Inscriere> listaInscrieri;
    private List<Participant> listaParticipanti;


    public MainViewFXML() {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        list.getSelectionModel().selectedItemProperty().addListener(
                (observable,oldvalue,newValue)-> showInscrisi(newValue));
    }
    public Iterable<Participant> findAllInscrisi(int idProba){
        List<Participant> list = new ArrayList<>();
        for (Inscriere inscriere:
                listaInscrieri) {
            if( inscriere.getIdProba() == idProba) {
                for (Participant participant :
                        listaParticipanti) {
                    if (inscriere.getIdParticipant() == participant.getIdParticipant())
                        list.add(participant);
                }
            }
        }
        return list;
    }
    public Iterable<Proba> findAllProbeInscris(int idParticipant){
        List<Proba> list = new ArrayList<>();
        for (Inscriere inscriere:
                listaInscrieri) {
            if(inscriere.getIdParticipant() == idParticipant)
                for (Proba proba :
                        listaProbe) {
                    if(inscriere.getIdProba() == proba.getIdProba())
                        list.add(proba);

                }
        }
        return list;
    }
    private void showInscrisi(Proba proba)  {
        if (proba==null)
            clearFields();
        else{
            listInscrisi.getItems().clear();
            System.out.println("INSCRIERI:"+listaInscrieri.size());
            System.out.println("PARTICIPANTI"+listaParticipanti.size());
            try {
                listaParticipanti = ctrl.getParticipanti();
            } catch (Exception e) {
                e.printStackTrace();
            }
            for (Participant participant:
                 findAllInscrisi(proba.getIdProba())) {
                String lista = "";
                for (Proba p:
                        findAllProbeInscris(participant.getIdParticipant())) {
                        if(lista!= "")
                            lista+= ", ";
                        lista += p.getLungime()+ "m "+ p.getStil()+ " ";
                }
                listInscrisi.getItems().add(participant.getNume() + " | " + participant.getVarsta()  + " | Probe: " + lista);
            }


        }
    }
    public void setTasksService(ClientController ctrl) throws Exception {
        this.ctrl = ctrl;
        mainPane.setVisible(false);
    }

    public void initData() {
        list.getItems().clear();
        listInscriere.getItems().clear();
        listInscriere.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        listInscrisi.getItems().clear();

        for(Proba proba : listaProbe){
            list.getItems().add(proba);
            listInscriere.getItems().add(proba.getLungime()+"m " + proba.getStil());
        }

        System.out.println(listInscriere.getSelectionModel().getSelectedItems());


    }


    public void handleAddButton(ActionEvent actionEvent) throws Exception {
        String nume = textfieldNume.getText();
        int varsta = Integer.parseInt(textFieldVarsta.getText());
        ObservableList indexes = listInscriere.getSelectionModel().getSelectedIndices();
        for (Object index:indexes
             ) {
            ctrl.participantInscris(nume,varsta,(int) index + 1);
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
        try {
            ctrl.login(username, password);
            textfieldUsername.clear();
            passwordField.clear();
            loginPane.setVisible(false);
            mainPane.setVisible(true);

            listaProbe = ctrl.getProbe();
            initData();
            listaParticipanti = ctrl.getParticipanti();
            listaInscrieri = ctrl.getInscrieri();

        }
        catch (Exception exception){
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
        Stage stage = (Stage) message.getDialogPane().getScene().getWindow();
        message.initOwner(stage);
    }

    @Override
    public void participantInscris(String nume, int varsta, int idProba) throws Exception {

    }

    @Override
    public void loggedIn(Organizator user) {
//        initData();
    }

    @Override
    public void refresh(Inscriere inscriere) {
        Platform.runLater(new Runnable() {
            public void run() {
                Proba p = null;

                for (Proba proba :
                        listaProbe) {
                    if(proba.getIdProba() == inscriere.getIdProba())
                        p = proba;
                }
                p.setNrParticipanti(p.getNrParticipanti() + 1);
                System.out.println(p);
                list.getItems().remove(p.getIdProba()-1);
                list.getItems().add(p.getIdProba()-1,p);

                listaInscrieri.add(inscriere);
            }
        });
    }

    @Override
    public void inscriereEfectuata(Inscriere inscriere) throws Exception {

    }
}