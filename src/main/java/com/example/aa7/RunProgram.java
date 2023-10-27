package com.example.aa7;

import Controller.Controller;
import Domain.Exception.MyException;
import Domain.IStmt.IStmt;
import Domain.Value.StringValue;
import Domain.Value.Value;
import PrgState.PrgState;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ArrayList;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class RunProgram implements Initializable{
    private Controller ctrl;

    private IStmt selPrg;
    @FXML
    private Button button;
    @FXML
    private TableView<Map.Entry<String, Value>> symTable;
    @FXML
    private TableColumn<Map.Entry<String,Value>,String> symTableName;
    @FXML
    private TableColumn <Map.Entry<Integer, Value>, String> symTableValues;
    @FXML
    private TableColumn<Map.Entry<Integer,Value>,Integer> heapTableAddr;
    @FXML
    private TableColumn <Map.Entry<Integer, Value>, String> heapTableValues;
    @FXML
    private TableView<Map.Entry<Integer, Value>> heapTable;
    @FXML
    private ListView<IStmt> exeStack;
    @FXML
    private ListView<StringValue> fileTable;
    @FXML
    private ListView<Value> outputList;
    @FXML
    private ListView<Integer> prgids;
    @FXML
    private Label nrPrgStates;

    public Controller getCtrl() {
        return ctrl;
    }

    public void setCtrl(Controller ctrl) {
        this.ctrl = ctrl;
        //populatePrgStateNumber();
        populatePrgids();
        button.setDisable(false);
    }

    private PrgState getPrgState(){
        if (prgids.getSelectionModel().getSelectedItem() == null)
            return null;
        int id = prgids.getSelectionModel().getSelectedItem();
        return ctrl.getRepo().getPrgById(id);
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.ctrl = null;
        heapTableAddr.setCellValueFactory(p-> new SimpleIntegerProperty(p.getValue().getKey()).asObject());
        heapTableValues.setCellValueFactory(p-> new SimpleStringProperty(p.getValue().getValue() + " "));

        symTableName.setCellValueFactory(p-> new SimpleStringProperty(p.getValue().getKey() + " "));
        symTableValues.setCellValueFactory(p-> new SimpleStringProperty(p.getValue().getValue() + " "));

        prgids.setOnMouseClicked(mouseEvent -> changeProgramStateHandler(getPrgState()));
        button.setDisable(true);

/*
        indexColumn.setCellValueFactory(p->new SimpleStringProperty(p.getValue().getKey() + " "));
        valueColumn.setCellValueFactory(p->new SimpleStringProperty(p.getValue().getValue().getKey() + " "));
        listColumn.setCellValueFactory(p->new SimpleStringProperty(p.getValue().getValue().getValue() + " "));
        execButton.setDisable(true);*/
    }

    private void changeProgramStateHandler(PrgState currentPrgState){
        if (currentPrgState == null)
            return;
        try {
            nrPrgStates.setText("Number of program states : \n" + String.valueOf(ctrl.getRepo().getPrgList().size()));
            populatePrgids();
            populateHeapTableView(currentPrgState);
            populateOutput(currentPrgState);
            populateFileTable(currentPrgState);
            populateExeStack(currentPrgState);
            populateSymTable(currentPrgState);
        }catch (Exception ex) {
            Alert error = new Alert(Alert.AlertType.ERROR, ex.getMessage());
            error.show();
        }
    }

    @FXML
    void oneStepHandler(MouseEvent event) {
        if (ctrl == null){
            Alert error = new Alert(Alert.AlertType.ERROR, "No program selected");
            error.show();
            button.setDisable(true);
            return;
        }
        PrgState prg = getPrgState();
        if(prg == null){
            Alert error = new Alert(Alert.AlertType.ERROR, "Execution is finished");
            error.show();
            return;
        }
        try{
            ctrl.executeOneStep();
            changeProgramStateHandler(prg);
            //if (ctrl.getRepo().getPrgList().size() == 0)
              //  button.setDisable(true);
        } catch (MyException e) {
            Alert error = new Alert(Alert.AlertType.ERROR, e.toString());
            error.show();
            button.setDisable(true);
        }
    }
    private void populateSymTable(PrgState currentPrgState) {
        symTable.setItems(FXCollections.observableList(new ArrayList<>(currentPrgState.getSymTable().getContent().entrySet())));
        symTable.refresh();
    }
    private void populatePrgids() {
        prgids.setItems(FXCollections.observableList(ctrl.getRepo().getPrgList().stream()
                                                .map(PrgState::getId)
                                                .collect(Collectors.toList())));
        prgids.refresh();
    }
    private void populateExeStack(PrgState currentPrgState) {
        exeStack.setItems(FXCollections.observableList(currentPrgState.getStk().getReverse()));
    }
    private void populateFileTable(PrgState currentPrgState) {
        fileTable.setItems(FXCollections.observableArrayList(currentPrgState.getFileTable().getContent().keySet()));
    }
    private void populateOutput(PrgState currentPrgState) {
        outputList.setItems(FXCollections.observableList(currentPrgState.getOut().getContent()));
    }
    private void populateHeapTableView(PrgState currentPrgState) {
        heapTable.setItems(FXCollections.observableList(new ArrayList<>(currentPrgState.getHeap().getContent().entrySet())));
        heapTable.refresh();
    }
}




