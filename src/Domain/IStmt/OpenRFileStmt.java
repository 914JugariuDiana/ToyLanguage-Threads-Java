package Domain.IStmt;

import Domain.ADT.MyIDictionary;
import Domain.Exception.MyException;
import Domain.Expression.Expression;
import Domain.Type.StringType;
import Domain.Value.StringValue;
import Domain.Value.Value;
import PrgState.PrgState;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class OpenRFileStmt implements IStmt{
    private Expression exp;

    public OpenRFileStmt (Expression e) {
        exp = e;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        Value result = exp.eval(state.getSymTable(), state.getHeap());
        MyIDictionary<StringValue, BufferedReader> fileTbl = state.getFileTable();
        if (!result.getType().equals(new StringType()))
            throw new MyException(result.toString() + "does not have String type");
        if (fileTbl.isVarDef((StringValue) result))
            throw new MyException(result.toString() + "is already defined in File Table");
        else {
            try  {
                BufferedReader br = new BufferedReader(new FileReader(result.toString()));
                fileTbl.put((StringValue) result, br);
            }catch (IOException ioe){
                throw new MyException("error-"+ioe);
            }
        }
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new OpenRFileStmt(exp.deepCopy());
    }

    public String toString(){
        return "openRFile (" + exp.toString() + ")";
    }
}
