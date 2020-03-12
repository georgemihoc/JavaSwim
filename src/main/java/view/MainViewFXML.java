package view;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import model.Participant;
import model.Proba;
import service.Service;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;


public class MainViewFXML implements Initializable {
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

    public MainViewFXML() {


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
//                lista = lista.substring(lista.length());
                listInscrisi.getItems().add(participant.getNume() + " | " + participant.getVarsta()  + " | Probe: " + lista);
            }


        }
    }
    private Service service;
    public void setTasksService(Service service){
        this.service=service;
//        service.addObserver(this);
//        service.addRunnerObserver(new RunnerObserver());
        initData();
        mainPane.setVisible(false);
    }

    private void initData() {
        list.getItems().clear();
        listInscriere.getItems().clear();
        listInscriere.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        listInscrisi.getItems().clear();

        for(Proba proba : service.findAllProba()){
            list.getItems().add(proba);
            listInscriere.getItems().add(proba.getLungime()+"m " + proba.getStil());
        }

        System.out.println(listInscriere.getSelectionModel().getSelectedItems());


//        table.getItems().clear();
//        for (Participant participant:service.findAllParticipant()) {
//            table.getItems().add(participant);
//        }
    }

    public void handleAddButton(ActionEvent actionEvent) {
        String nume = textfieldNume.getText();
        int varsta = Integer.parseInt(textFieldVarsta.getText());
        service.addParticipant(nume,varsta);
        ObservableList indexes = listInscriere.getSelectionModel().getSelectedIndices();
        for (Object index:indexes
             ) {
            service.addInscriere(nume,varsta,(int)index+1);
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

    public void handleLoginButton(ActionEvent actionEvent) {
        String username = textfieldUsername.getText();
        String password = passwordField.getText();
        if( service.validateLogin(username,password))
        {
            textfieldUsername.clear();
            passwordField.clear();
            loginPane.setVisible(false);
            mainPane.setVisible(true);
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

//    @FXML
//    private void addButton(ActionEvent ec) {
//        String id=taskIdText.getText();
//        String desc=descBox.getText();
//        SortingOrder orderV=(SortingOrder)orderGroup.getSelectedToggle().getUserData();
//        SortingAlgorithm algo=alg.getSelectionModel().getSelectedItem();
//        try{
//            int idVal=Integer.parseInt(id);
//            int nrElemVal=Integer.parseInt(nrElem.getText());
//            addSortingTask(idVal,desc,orderV,algo,nrElemVal);
//            clearFields();
//
//        }catch(NumberFormatException ex){
//            showErrorMessage( "Id-ul si nr elem trebuie sa fie numere intregi! " + ex.getMessage());
//        }catch (RepositoryException ex){
//            showErrorMessage("Eroare la adaugare: " + ex.getMessage());
//        }
//    }
//
//    public void addSortingTask(int id, String desc, SortingOrder order, SortingAlgorithm alg, int nrElem){
//        SortingTask task=new SortingTask(id,desc,alg,order,nrElem);
//        service.addSortingTask(task);
//
//    }

//    @FXML private void deleteButton(ActionEvent ev){
//        int index=table.getSelectionModel().getSelectedIndex();
//        if (index<0) {
//            showErrorMessage("Eroare la stergere: trebuie sa selectati un task");
//            return;
//        }
//        SortingTask task=table.getSelectionModel().getSelectedItem();
//        deleteTask(task);
//    }
//    public void deleteTask(SortingTask task){
//        service.deleteSortingTask(task);
//    }
//
//    @Override
//    public void update(SortingTaskEvent sortingTaskEvent) {
//        switch (sortingTaskEvent.getType()){
//            case ADD:{ table.getItems().add(sortingTaskEvent.getData()); break;}
//            case DELETE:{table.getItems().remove(sortingTaskEvent.getData()); break;}
//            case UPDATE:{ table.getItems().remove(sortingTaskEvent.getOldData());
//                table.getItems().add(sortingTaskEvent.getData()); break;}
//        }
//    }
//
//    @FXML public void updateButton(ActionEvent ev){
//        int index=table.getSelectionModel().getSelectedIndex();
//        if (index<0){
//            showErrorMessage("Trebuie sa selectati un task!!!");
//            return;
//        }
//        SortingTask oldTask=table.getSelectionModel().getSelectedItem();
//        String id=taskIdText.getText();
//        String desc=descBox.getText();
//        SortingOrder orderV=(SortingOrder)orderGroup.getSelectedToggle().getUserData();
//        SortingAlgorithm algo=alg.getSelectionModel().getSelectedItem();
//        try{
//            int idVal=Integer.parseInt(id);
//            int nrElemVal=Integer.parseInt(nrElem.getText());
//            updateTask(oldTask, idVal, desc, orderV, algo, nrElemVal);
//
//        }catch(NumberFormatException ex){
//            showErrorMessage( "Id-ul si nr elem trebuie sa fie numere intregi! " + ex.getMessage());
//        }catch (RepositoryException ex){
//            showErrorMessage("Eroare la adaugare: " + ex.getMessage());
//        }
//    }
//    public void updateTask(SortingTask oldTask, int idVal, String desc, SortingOrder orderV, SortingAlgorithm algo, int nrElemVal) {
//        SortingTask newTask=new SortingTask(idVal,desc,algo,orderV,nrElemVal);
//        service.updateSortingTask(oldTask,newTask);
//    }
//
    void showErrorMessage(String text){
        Alert message=new Alert(Alert.AlertType.ERROR);
        message.setTitle("Mesaj eroare");
        message.setContentText(text);
        message.showAndWait();
    }
//
//    @FXML public void cancelButton(ActionEvent e){
//        table.getSelectionModel().clearSelection();
//        clearFields();
//    }
//    private void clearFields(){
//        taskIdText.setText("");
//        descBox.setText("");
//        nrElem.setText("");
//        asc.setSelected(true);
//        alg.getSelectionModel().selectFirst();
//        exeTask.setDisable(true);
//
//        //cancelExec.setDisable(true);
//    }
//
//    private class RunnerObserver implements Observer<TaskEvent> {
//
//        @Override
//        public void update(TaskEvent taskEvent) {
//            switch (taskEvent.getType()){
//                case StartingTaskExecution:{appendMessage("Starting execution of :"+taskEvent.getTask()); break;}
//                case TaskExecutionCompleted:{appendMessage("Completed execution of "+taskEvent.getTask()); break;}
//            }
//        }
//    }
//    @FXML void  handleAddTaskRunner(ActionEvent e){
//        TextInputDialog inputControl=new TextInputDialog();
//        inputControl.setContentText("Introduceti un task id:");
//        inputControl.setTitle("Add task to Runner");
//        inputControl.setHeaderText("");
//        Optional<String> result=inputControl.showAndWait();
//        if (result.isPresent()){
//            try{
//                int idV=Integer.parseInt(result.get());
//                service.addTaskToRunner(idV);
//                appendMessage("Task-ul cu  "+idV+" adaugat la TaskRunner");
//            }catch (NumberFormatException ex){
//                showErrorMessage("Trebuie sa introduceti un id valid"+ex.getMessage());
//            }catch (RepositoryException ex){
//                showErrorMessage(ex.getMessage());
//            }
//        }
//    }
//
//    @FXML void  handleExecuteOne(ActionEvent e){
//        service.executeOneTask();
//    }
//
//    private Task<Void> executeAllTask;
//    @FXML void handleExecuteALL(ActionEvent e){
//        executeAllTask=new Task<Void>(){
//            @Override public Void call(){
//                service.executeAll();
//                return null;
//            }
//
//            @Override
//            protected void cancelled() {
//                super.cancelled();
//                service.cancelRunner();
//            }
//        };
//        // service.executeAll();
//        Thread th=new Thread(executeAllTask);
//        th.setDaemon(true);
//        th.start();
//    }
//    private void appendMessage(String text){
//        // execMessages.appendText(text+"\n");
//        Platform.runLater(new Runnable() {
//            @Override public void run() {
//                execMessages.appendText(text+"\n");
//            }
//        });
//
//    }
//
//    @FXML Button exeTask;
//    @FXML Button cancelExec;
//
//    private Task<Void> runningTask;
//    @FXML void execButton(ActionEvent e){
//        int index=table.getSelectionModel().getSelectedIndex();
//        if (index<0){
//            showErrorMessage("Trebuie sa selectati un task!!!");
//            return;
//        }
//        SortingTask oldTask=table.getSelectionModel().getSelectedItem();
//        //oldTask.execute();
//
//        runningTask = new Task<Void>() {
//            @Override public Void call() {
//
//                try{
//                    oldTask.execute();
//                    updateMessage("Done");
//                    updateProgress(1,1);
//                }catch(TaskExecutionException ex){
//                    System.out.println("Execution task cancelled");
//                    updateMessage("Cancelled");
//
//
//                    updateProgress(0,1);
//                    System.out.println("Cancelled before unbind ");
//                    // progress.progressProperty().unbind();
//
//                    // progress.progressProperty().unbind();
//                    if (table.getSelectionModel().getSelectedIndex()>=0)
//                        exeTask.setDisable(false);
//                    cancelExec.setDisable(true);
//                }
//                return null;
//            }
//
//            @Override protected void succeeded() {
//                super.succeeded();
//                updateMessage("Done!");
//                progress.progressProperty().unbind();
//                if (table.getSelectionModel().getSelectedIndex()>=0)
//                    exeTask.setDisable(false);
//                cancelExec.setDisable(true);
//            }
//
//
//        };
//        // progress = new ProgressBar();
//        progress.progressProperty().bind(runningTask.progressProperty());
//        execLabel.textProperty().bind(runningTask.messageProperty());
//
//
//        cancelExec.setDisable(false);
//        exeTask.setDisable(true);
//
//        Thread th=new Thread(runningTask);
//        th.setDaemon(true);
//        th.start();
//    }
//
//    @FXML void cancelExecButton(ActionEvent e){
//        runningTask.cancel();
//        if (table.getSelectionModel().getSelectedIndex()>=0)
//            exeTask.setDisable(false);
//        cancelExec.setDisable(true);
//
//    }
//
//    @FXML ProgressBar progress;
//    @FXML Label execLabel;
//
//    public void close(){
//        System.out.println("Ctrl closing");
//        if((runningTask!=null)&&(runningTask.isRunning()))
//        {
//            System.out.println("Task still running ...");
//            runningTask.cancel();
//
//        }
//        if ((executeAllTask!=null)&&(executeAllTask.isRunning())) {
//            System.out.println("Execute all still running ..");
//            executeAllTask.cancel();
//            // service.close();
//        }
//    }
}
