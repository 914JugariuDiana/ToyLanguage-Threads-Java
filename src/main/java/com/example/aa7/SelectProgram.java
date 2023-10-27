package com.example.aa7;

import Controller.Controller;
import Domain.ADT.*;
import Domain.Exception.MyException;
import Domain.Expression.*;
import Domain.IStmt.*;
import Domain.Type.*;
import Domain.Value.BoolValue;
import Domain.Value.IntValue;
import Domain.Value.StringValue;
import Domain.Value.Value;
import PrgState.PrgState;
import Repository.IRepository;
import Repository.Repository;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import java.io.BufferedReader;
import java.net.URL;
import java.util.ResourceBundle;

public class SelectProgram implements Initializable {

    @FXML
    private ListView<IStmt> listView;

    private RunProgram runPrg;

    public void setRunProgramController(RunProgram runPrg) {
        this.runPrg = runPrg;
    }

    public RunProgram getRunPrg(){
        return runPrg;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        listView.setItems(FXCollections.observableArrayList (ex1, ex2, ex3, ex4, ex5, ex6, ex7, ex8, ex9, ex10, ex11));
        listView.getSelectionModel().selectedItemProperty().addListener((observableValue, iStmt, t1) -> {
            int nrPrg = listView.getSelectionModel().getSelectedIndex();
            IStmt prg = listView.getSelectionModel().getSelectedItem();
           // System.out.println(prg);
            MyIDictionary<String, Value> symTbl = new MyDictionary<>();
            MyIStack<IStmt> stk = new MyStack<>();
            MyIList<Value> out = new MyList<>();
            MyIDictionary<StringValue, BufferedReader> fileMap = new MyDictionary<>();
            MyIHeap<Integer, Value> heap = new MyHeap<>();
            PrgState prgState = new PrgState(stk, symTbl, out, fileMap, heap, prg, PrgState.getNumberThread());
            IRepository repo = new Repository(new StringValue("log" + nrPrg));
            repo.addPrgState(prgState);
            Controller ctr = new Controller(repo);

            try {
                prg.typecheck(new MyDictionary<>());
                runPrg.setCtrl(ctr);
            } catch (MyException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR, ex.getMessage());
                alert.show();
            }
        });
    }

    IStmt ex1 = new CompStmt(new VarDeclStmt("v", new IntType()),
            new CompStmt(new AssignStmt("v", new ValueExpression(new IntValue(2))),
                    new PrintStmt(new VariableExpression("v"))));
    IStmt ex2 = new CompStmt( new VarDeclStmt("a",new IntType()), new CompStmt(new VarDeclStmt("b",new IntType()),
            new CompStmt(new AssignStmt("a", new ArithmeticExpression(new ValueExpression(new IntValue(2)),
                    new ArithmeticExpression(new ValueExpression(new IntValue(3)), new ValueExpression(new IntValue(5)), 3), 1)),
                    new CompStmt(new AssignStmt("b",new ArithmeticExpression(new VariableExpression("a"), new ValueExpression(
                            new IntValue(1)), 1)), new PrintStmt(new VariableExpression("b"))))));
    IStmt ex3 = new CompStmt(new VarDeclStmt("a",new BoolType()), new CompStmt(new VarDeclStmt("v", new IntType()),
            new CompStmt(new AssignStmt("a", new ValueExpression(new BoolValue(true))), new CompStmt(new IfStmt(new VariableExpression("a"),
                    new AssignStmt("v",new ValueExpression(new IntValue(2))), new AssignStmt("v", new ValueExpression(new IntValue(3)))),
                    new PrintStmt(new VariableExpression("v"))))));
    IStmt ex5 = new CompStmt(new VarDeclStmt("a",new BoolType()), new CompStmt(new VarDeclStmt("v", new IntType()),
            new CompStmt(new AssignStmt("a", new ValueExpression(new BoolValue(true))), new CompStmt(new IfStmt(new RelationalExpression(
                    new ValueExpression(new IntValue(3)), new ValueExpression(new IntValue(4)), "<"),
                    new AssignStmt("v",new ValueExpression(new IntValue(2))), new AssignStmt("v", new ValueExpression(new IntValue(3)))),
                    new PrintStmt(new VariableExpression("v"))))));
    IStmt ex4 = new CompStmt(new VarDeclStmt("varf", new StringType()), new CompStmt(new AssignStmt("varf", new ValueExpression(new StringValue("test.in"))),
            new CompStmt(new OpenRFileStmt(new VariableExpression("varf")), new CompStmt(new VarDeclStmt("varc", new IntType()), new CompStmt(
                    new ReadFileStmt(new VariableExpression("varf"), "varc"), new CompStmt(new PrintStmt(new VariableExpression("varc")), new CompStmt(
                    new ReadFileStmt(new VariableExpression("varf"), "varc"), new CompStmt(new PrintStmt(new VariableExpression("varc")), new CloseRFileStmt(
                    new VariableExpression("varf"))))))))));

    IStmt ex6 = new CompStmt(new VarDeclStmt("v", new IntType()), new CompStmt(new AssignStmt("v", new ValueExpression(new IntValue(4))), new CompStmt(
            new WhileStmt(new RelationalExpression(new VariableExpression("v"), new ValueExpression(new IntValue(0)), ">"), new CompStmt(new PrintStmt(
                    new VariableExpression("v")), new AssignStmt("v", new ArithmeticExpression(new VariableExpression("v"), new ValueExpression(new IntValue(1)), 2)))),
            new PrintStmt(new VariableExpression("v")))));

    IStmt ex7 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())), new CompStmt(new HeapAllocationStmt("v", new ValueExpression(new IntValue(20))),
            new CompStmt(new PrintStmt(new HeapReadingExpression(new VariableExpression("v"))), new CompStmt(new HeapWritingStmt("v", new ValueExpression(
                    new IntValue(30))), new PrintStmt(new ArithmeticExpression(new HeapReadingExpression(new VariableExpression("v")), new ValueExpression(
                    new IntValue(5)), 1))))));
    IStmt ex8 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())), new CompStmt(new HeapAllocationStmt("v", new ValueExpression(new IntValue(20))),
            new CompStmt(new VarDeclStmt("a", new RefType(new RefType(new IntType()))), new CompStmt(new HeapAllocationStmt("a", new VariableExpression("v")),
                    new CompStmt(new HeapAllocationStmt("v", new ValueExpression(new IntValue(30))), new PrintStmt(new HeapReadingExpression( new HeapReadingExpression(
                            new VariableExpression("a")))))))));
    //int v; Ref int a; v=10;new(a,22);fork(wH(a,30);v=32;print(v);print(rH(a)));print(v);print(rH(a))
    IStmt ex9 = new CompStmt(new VarDeclStmt("v", new IntType()), new CompStmt(new VarDeclStmt("a", new RefType(new IntType())), new CompStmt(new AssignStmt("v",
            new ValueExpression(new IntValue(10))), new CompStmt(new HeapAllocationStmt("a", new ValueExpression(new IntValue(22))), new CompStmt(
            new ForkStmt(new CompStmt(new HeapWritingStmt("a", new ValueExpression(new IntValue(30))), new CompStmt(new AssignStmt("v", new ValueExpression(
                    new IntValue(32))), new CompStmt(new PrintStmt(new VariableExpression("v")), new PrintStmt(new HeapReadingExpression(new VariableExpression("a"))))))),
            new CompStmt(new PrintStmt(new VariableExpression("v")), new PrintStmt(new HeapReadingExpression(new VariableExpression("a")))))))));
    IStmt ex10 = new CompStmt(new VarDeclStmt("v", new BoolType()), new CompStmt(new AssignStmt("v", new ValueExpression(new IntValue(2))),
            new PrintStmt(new VariableExpression("v"))));

    IStmt ex11 = new CompStmt(new VarDeclStmt("v", new RefType(new RefType(new IntType()))), new CompStmt(new VarDeclStmt("a", new RefType(new IntType())), new CompStmt(
            new HeapAllocationStmt("a", new ValueExpression(new IntValue(20))), new CompStmt(new ForkStmt(new CompStmt(new HeapAllocationStmt("v",
            new VariableExpression("a")), new PrintStmt(new HeapReadingExpression(new VariableExpression("a"))))), new CompStmt(new ForkStmt(new PrintStmt(new HeapReadingExpression(
                    new HeapReadingExpression(new VariableExpression("v"))))), new PrintStmt(new ValueExpression(new IntValue(15))))))));


    public IStmt selectProgram(MouseEvent mouseEvent) {
        return listView.getItems().get(listView.getSelectionModel().getSelectedIndex());
    }
}
