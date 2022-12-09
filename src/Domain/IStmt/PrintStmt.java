package Domain.IStmt;
import Domain.ADT.MyIList;
import Domain.Expression.Expression;
import Domain.Exception.MyException;
import Domain.Value.Value;
import PrgState.PrgState;

public class PrintStmt implements IStmt {
    private Expression exp;
    public PrintStmt(Expression e){
        exp = e;
    }
    public String toString(){
        return ("print(" + exp.toString() + ")");
    }
    public PrgState execute(PrgState state) throws MyException{
        MyIList<Value> out = state.getOut();
        Value v = exp.eval(state.getSymTable(), state.getHeap());
        out.add(v);
        state.setOut(out);
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new PrintStmt(exp.deepCopy());
    }
}
