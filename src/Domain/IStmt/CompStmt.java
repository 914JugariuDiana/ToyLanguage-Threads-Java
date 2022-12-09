package Domain.IStmt;
import Domain.ADT.MyIStack;
import Domain.Exception.MyException;
import PrgState.PrgState;

public class CompStmt implements IStmt {
    private IStmt first, second;

    public CompStmt(IStmt f, IStmt s){
        first = f;
        second = s;
    }

    @Override
    public String toString() {
        return "("+first.toString() + ";" + second.toString() + ")";
    }

    public PrgState execute(PrgState state) throws MyException {
        MyIStack<IStmt> stk = state.getStk();
        stk.push(second);
        stk.push(first);
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new CompStmt(first.deepCopy(), second.deepCopy());
    }
}
