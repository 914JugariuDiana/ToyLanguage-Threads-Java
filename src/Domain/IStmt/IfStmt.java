package Domain.IStmt;

import Domain.ADT.MyIStack;
import Domain.Expression.Expression;
import Domain.Exception.MyException;
import Domain.Type.BoolType;
import Domain.Value.BoolValue;
import Domain.Value.Value;
import PrgState.PrgState;

public class IfStmt implements IStmt {
    private Expression exp;
    private IStmt thenS, elseS;
    public IfStmt(Expression e, IStmt t, IStmt el){
        exp = e;
        thenS = t;
        elseS = el;
    }
    public String toString(){
        return ("(IF (" + exp.toString() +") THEN(" + thenS.toString() + ") ELSE (" + elseS.toString() + " ))");
    }
    public PrgState execute(PrgState state) throws  MyException {
        Value result = exp.eval(state.getSymTable(), state.getHeap());
        MyIStack<IStmt> stack = state.getStk();
        if (result.getType().equals(new BoolType())){
            BoolValue res = (BoolValue) result;
            if (res.getVal()){
                stack.push(thenS);
            }
            else{
                stack.push(elseS);
            }
        }
        else{
            throw new MyException("conditional expresion is not boolean");
        }
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new IfStmt(exp.deepCopy(), thenS.deepCopy(), elseS.deepCopy());
    }
}
