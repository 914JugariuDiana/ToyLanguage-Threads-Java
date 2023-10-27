package Domain.IStmt;

import Domain.ADT.MyHeap;
import Domain.ADT.MyIDictionary;
import Domain.Exception.MyException;
import Domain.Expression.Expression;
import Domain.Type.RefType;
import Domain.Type.Type;
import Domain.Value.RefValue;
import Domain.Value.Value;
import PrgState.PrgState;

public class HeapAllocationStmt implements IStmt{
    private String name;
    private Expression exp;

    public HeapAllocationStmt (String n, Expression e){
        name = n;
        exp = e;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        if (!state.getSymTable().isVarDef(name))
            throw new MyException(name + "- is not defined in Symbol Table");
        Value result = exp.eval(state.getSymTable(), state.getHeap());
        if (!(state.getSymTable().lookup(name).getType() instanceof RefType))
            throw new MyException(name + "-does not have reference type");
        if (!state.getSymTable().lookup(name).getType().equals(new RefType(result.getType())))
            throw new MyException(name + "-does not have reference type");
        if (!result.getType().equals(((RefType) state.getSymTable().lookup(name).getType()).getInner()))
            throw new MyException(result.toString() + " has different type from " + state.getSymTable().lookup(name).toString());
        int addr = MyHeap.getNewAddr();
        state.getHeap().put(addr, result);
        state.getSymTable().put(name, new RefValue(addr, ((RefType) state.getSymTable().lookup(name).getType()).getInner()));
        return null;
    }

    @Override
    public String toString() {
        return "new(" + name + ", " + exp.toString() + ")";
    }

    @Override
    public IStmt deepCopy() {
        return new HeapAllocationStmt(new String(name), exp.deepCopy());
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typvar = typeEnv.lookup(name);
        Type typexp = exp.typeCheck(typeEnv);
        if (typvar.equals(new RefType(typexp)))
            return typeEnv;
        else
            throw new MyException("NEW stmt: right hand side and left hand side have different types");
    }
}
