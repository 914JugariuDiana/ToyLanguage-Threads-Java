package Domain.IStmt;

import Domain.ADT.MyIDictionary;
import Domain.Exception.MyException;
import Domain.Expression.Expression;
import Domain.Expression.VariableExpression;
import Domain.Type.RefType;
import Domain.Type.Type;
import Domain.Value.RefValue;
import Domain.Value.Value;
import PrgState.PrgState;

import javax.lang.model.type.NullType;

public class HeapWritingStmt implements IStmt{
    private String name;
    private Expression exp;

    public HeapWritingStmt(String n, Expression e){
        name = n;
        exp = e;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        if (!state.getSymTable().isVarDef(name))
            throw new MyException(name + " - variable is not declared");
        if (!(state.getSymTable().lookup(name).getType() instanceof RefType))
            throw new MyException(name + " - variable does not have reference type");
        if (!state.getHeap().isVarDef(((RefValue)state.getSymTable().lookup(name)).getAddr()))
            throw new MyException(((RefValue)state.getSymTable().lookup(name)).getAddr() + " - address is not used");
        Value result = exp.eval(state.getSymTable(), state.getHeap());
        if (!result.getType().equals((state.getHeap().lookup(((RefValue) state.getSymTable().lookup(name)).getAddr()).getType())))
            throw new MyException(result.toString() + " does not have " + state.getSymTable().lookup(name).getType().toString());
        state.getHeap().put(((RefValue) state.getSymTable().lookup(name)).getAddr(), result);
        return null;
    } //wh(v, 30)  v->(1, int) v->RefInt()   1->20

    @Override
    public String toString() {
        return "wH(" + name + ", " + exp.toString() + ")";
    }

    @Override
    public IStmt deepCopy() {
        return new HeapWritingStmt(name, exp.deepCopy());
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typvar = typeEnv.lookup(name);
        Type typexp = exp.typeCheck(typeEnv);
        if (typvar.equals(new RefType(typexp)))
            return typeEnv;
        else
            throw new MyException("WRITE stmt: right hand side and left hand side have different types");
    }
}
