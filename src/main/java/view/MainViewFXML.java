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
import service.Event;
import service.IObserver;
import service.Observer;
import service.Service;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Vector;


public class MainViewFXML implements Initializable, Observer<Event>, IObserver {
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
    private TableView <Participant> table;

    @FXML
    private ListView<Proba> list;

    @FXML private RadioButton asc, desc;

//    @FXML private ChoiceBox<SortingAlgorithm> alg;
    @FXML private ToggleGroup orderGroup;

    @FXML private TextField taskIdText,descBox, nrElem;
    @FXML private TextArea execMessages;

    private JList listprobe;

    private static Vector allInstances = new Vector();


    public MainViewFXML() {
            allInstances.add(this);

    }
    public static synchronized Vector getAllInstances()
    {
        return (Vector) (allInstances.clone());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        alg.setItems(FXCollections.observableArrayList(SortingAlgorithm.values()));
//        alg.getSelectionModel().selectFirst();
//        asc.setSelected(true);
//        asc.setUserData(SortingOrder.Ascending);
//        desc.setUserData(SortingOrder.Descending);
//        table.getSelectionModel().selectedItemProperty().addListener(
//                (observable,oldvalue,newValue)->showSortingTaskDetails(newValue) );
//        execMessages.setEditable(false);
//        exeTask.setDisable(true);
//        cancelExec.setDisable(true);
//        progress.indeterminateProperty().not();

//        initData();
        list.getSelectionModel().selectedItemProperty().addListener(
                (observable,oldvalue,newValue)->showInscrisi(newValue) );
//        listprobe=new JList(ctrl.getProbeListModel());
//        listprobe.addListSelectionListener(new ListSelectionListener() {
//
//            @Override
//            public void valueChanged(ListSelectionEvent listSelectionEvent) {
//                listprobe=new JList(ctrl.getProbeListModel());
//
//                initData();
//            }
//        });

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

        service.addObserver(this);
//        service.addRunnerObserver(new RunnerObserver());
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
        service.addParticipant(nume,varsta);
        ObservableList indexes = listInscriere.getSelectionModel().getSelectedIndices();
        for (Object index:indexes
             ) {
            service.addInscriere(nume,varsta,(int)index+1);
//            wait(5);
            ctrl.participantInscris(service.findInscriere(service.findParticipant(nume,varsta).getIdParticipant(),(int) index + 1));
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
    public void update(Event event) {
//        initData();
    }

    @Override
    public void participantInscris(Inscriere inscriere) throws Exception {
//        initData();
    }

    @Override
    public void addParticipant(Inscriere inscriere) throws Exception {

    }

    @Override
    public void loggedIn(Organizator user) {
//        initData();
    }

    @Override
    public void refresh(Inscriere inscriere)
    {
        Platform.runLater(new Runnable() {
            public void run() {
//                for(Proba proba : service.findAllProba()){
//                    list.getItems().add(proba);
//                }
//                list.getItems().remove(inscriere.getIdProba()-1);
                Proba p =service.findProba(inscriere.getIdProba());
                list.getItems().remove(p.getIdProba()-1);
                list.getItems().add(p.getIdProba()-1,p);
            }
        });
    }

    private class RunnerObserver implements Observer<Event> {
        @Override
        public void update(Event event) {
            initData();
        }

    }

}
