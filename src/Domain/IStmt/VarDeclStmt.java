package Domain.IStmt;

import Domain.ADT.MyIDictionary;
import Domain.ADT.MyIStack;
import Domain.Exception.MyException;
import Domain.Type.StringType;
import Domain.Type.Type;
import Domain.Value.Value;
import PrgState.PrgState;

public class VarDeclStmt implements IStmt {
    private String name;
    private Type typ;

    public VarDeclStmt(String n, Type t){
        name = n;
        typ = t;
    }
    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIStack<IStmt> stk = state.getStk();
        MyIDictionary<String, Value> symTbl = state.getSymTable();
        if (symTbl.isVarDef(name))
            throw new MyException("variable is already declared");
        else {
            symTbl.put(name, typ.getDefaultValue());
        }
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new VarDeclStmt(new String(name), typ.deepCopy());
    }

    public String toString(){
        if (typ.equals(new StringType()))
            return typ.toString() + " " + name;
        return typ.toString() + " " + name + " = " + typ.getDefaultValue().toString();
    }
}
