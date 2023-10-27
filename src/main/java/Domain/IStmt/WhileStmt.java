package Domain.IStmt;

import Domain.ADT.MyIDictionary;
import Domain.Exception.MyException;
import Domain.Expression.Expression;
import Domain.Type.BoolType;
import Domain.Type.Type;
import Domain.Value.BoolValue;
import Domain.Value.Value;
import PrgState.PrgState;

public class WhileStmt implements IStmt{
    private Expression exp;
    private IStmt stmt;

    public WhileStmt(Expression e, IStmt st){
        exp = e;
        stmt = st;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        Value result = exp.eval(state.getSymTable(), state.getHeap());
        if (!result.getType().equals(new BoolType()))
            throw new MyException(exp.toString() + " - condition expression is not boolean");
        if (((BoolValue) result).getVal()) {
            state.getStk().push(new WhileStmt(exp.deepCopy(), stmt.deepCopy()));
            state.getStk().push(stmt);
        }
        return null;
    }

    @Override
    public String toString() {
        return "while (" + exp.toString() + "){" + stmt.toString() + "}";
    }

    @Override
    public IStmt deepCopy() {
        return new WhileStmt(exp.deepCopy(), stmt.deepCopy());
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        if (exp.typeCheck(typeEnv).equals(new BoolType())) {
            stmt.typecheck(typeEnv.cloned());
            return typeEnv;
        } else {
            throw new MyException("While cond does not have Bool Type");
        }
    }
}
