package PrgState;

import Domain.ADT.MyIDictionary;
import Domain.ADT.MyIHeap;
import Domain.ADT.MyIList;
import Domain.ADT.MyIStack;
import Domain.Exception.MyException;
import Domain.IStmt.IStmt;
import Domain.Value.StringValue;
import Domain.Value.Value;

import java.io.BufferedReader;

public class PrgState {
    private MyIStack<IStmt> exeStack;
    private MyIDictionary<String, Value> symTable;
    private MyIList<Value> out;
    private IStmt originalProgram;
    private MyIDictionary<StringValue, BufferedReader> fileTable;
    private MyIHeap<Integer, Value> heap;
    private int id = 0;
    public static int numberThreads = 0;


    public PrgState(MyIStack<IStmt> stk, MyIDictionary<String, Value> symtbl, MyIList<Value> ot, MyIDictionary<StringValue, BufferedReader> ft, MyIHeap<Integer, Value> h, IStmt prg, int i){

        exeStack = stk;
        symTable = symtbl;
        out = ot;
        fileTable = ft;
        heap = h;
        originalProgram = prg.deepCopy();
        stk.push(prg);
        id = i;
    }

    public int getId(){ return id; }
    static public int getNumberThread (){return ++numberThreads;}
    public MyIHeap<Integer, Value> getHeap(){
        return heap;
    }
    public void setHeap(MyIHeap<Integer, Value> h){
        heap = h;
    }
    public MyIDictionary<StringValue, BufferedReader> getFileTable() {
        return fileTable;
    }
    public void setFileTable(MyIDictionary<StringValue, BufferedReader> fileTable) {
        this.fileTable = fileTable;
    }
    public MyIStack<IStmt> getStk(){
        return  exeStack;
    }
    public void setExeStack(MyIStack<IStmt> ex){
        exeStack = ex;
    }
    public void setSymTable(MyIDictionary<String, Value> tbl){
        symTable = tbl;
    }
    public void setOut(MyIList<Value> ot){
        out = ot;
    }
    public void setOriginalProgram(IStmt op){
        originalProgram = op;
    }
    public IStmt getCrtPrg(){
        return originalProgram;
    }
    public MyIList<Value> getOut(){
        return out;
    }
    public MyIDictionary<String, Value> getSymTable(){
        return symTable;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        for (StringValue s : fileTable.getKeys())
            str.append("    " + s + " \n");
        return  "State id : " + id + "\nExeStack: \n" + exeStack.toString()+ "\n" +
                "SymTable: \n" + symTable.toString() + "\n" +
                "Output: \n" + out.toString() + "\n" +
                "Heap: \n" + heap.toString() + "\n" +
                "File table:\n" + str.toString() + "\n";
    }
    public boolean isNotCompleted(){
        if (exeStack.isEmpty())
            numberThreads--;
        return !exeStack.isEmpty();
    }

    public PrgState oneStep() throws MyException {
        if(exeStack.isEmpty())
            throw new MyException("prgState stack is empty");
        IStmt crtStmt = exeStack.pop();
        return crtStmt.execute(this);
    }
}
