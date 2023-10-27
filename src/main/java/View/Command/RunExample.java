package View.Command;

import Controller.Controller;
import Domain.ADT.*;
import Domain.Exception.MyException;
import Domain.IStmt.IStmt;
import Domain.Type.Type;
import Domain.Value.StringValue;
import Domain.Value.Value;
import PrgState.PrgState;
import Repository.IRepository;
import Repository.Repository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class RunExample extends Command{
    private IStmt orgPrg;
    String fileName;
    public RunExample(String key, String description, IStmt orgprg, String file){
        super(key, description);
        orgPrg = orgprg;
        fileName = file;
    }
    @Override
    public void execute() {
        try{
            orgPrg.typecheck(new MyDictionary<String, Type>());
            MyIDictionary<String, Value> symTbl = new MyDictionary<String, Value>();
            MyIStack<IStmt> stk = new MyStack<IStmt>();
            MyIList<Value> out = new MyList<Value>();
            MyIDictionary<StringValue, BufferedReader> fileMap = new MyDictionary<StringValue, BufferedReader>();
            MyIHeap<Integer, Value> heap = new MyHeap<Integer, Value>();
            PrgState prg = new PrgState(stk, symTbl, out, fileMap, heap, orgPrg, PrgState.getNumberThread());
            IRepository repo = new Repository(new StringValue(fileName));
            repo.addPrgState(prg);
            Controller ctr = new Controller(repo);
            try {
                PrintWriter writer = new PrintWriter(fileName);
                writer.print("");
                writer.close();
            }catch (IOException ignored){}
            MyHeap.clearAddr();
            ctr.allStep();
        }catch (MyException e) {
            System.out.println(e);
        }
    }
}
