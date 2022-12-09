package Domain.IStmt;

import Domain.Exception.MyException;
import Domain.Expression.Expression;
import Domain.Type.StringType;
import Domain.Value.StringValue;
import Domain.Value.Value;
import PrgState.PrgState;

import java.io.BufferedReader;
import java.io.IOException;

public class CloseRFileStmt implements IStmt{
    private Expression exp;

    public CloseRFileStmt(Expression e){
        exp = e;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        Value res = exp.eval(state.getSymTable(), state.getHeap());
        if (res.getType().equals(new StringType())){
            StringValue result = (StringValue) exp.eval(state.getSymTable(), state.getHeap());
            if (state.getFileTable().isVarDef(result)){
                try{
                    BufferedReader br = state.getFileTable().lookup(result);
                    br.close();
                    state.getFileTable().delete(result);
                }catch (IOException ioe){
                    throw new MyException("error - "+ ioe);
                }
            }else
                throw new MyException(result.toString() + "not defined in File Table");
        } else
            throw new MyException("Expression is not a string type");
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new CloseRFileStmt(exp.deepCopy());
    }

    public String toString(){
        return "closeRFile (" + exp.toString() + ")";
    }
}
