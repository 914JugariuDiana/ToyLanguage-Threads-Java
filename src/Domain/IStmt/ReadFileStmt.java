package Domain.IStmt;

import Domain.ADT.MyIDictionary;
import Domain.Exception.MyException;
import Domain.Expression.Expression;
import Domain.Type.IntType;
import Domain.Type.StringType;
import Domain.Value.IntValue;
import Domain.Value.StringValue;
import Domain.Value.Value;
import PrgState.PrgState;
import java.io.BufferedReader;
import java.io.IOException;

public class ReadFileStmt implements IStmt{
    private Expression exp;
    private String name;

    public ReadFileStmt(Expression e, String n){
        exp = e;
        name = n;
    }
    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<String, Value> symtbl = state.getSymTable();
        if (!symtbl.isVarDef(name))
            throw new MyException(name + "variable is not declared in Symbol Table");
        Value res = symtbl.lookup(name);
        if (!res.getType().equals(new IntType()))
            throw new MyException(name + " does not have int type");
        Value result = exp.eval(symtbl, state.getHeap());
        if (result.getType().equals(new StringType())){
            MyIDictionary<StringValue, BufferedReader> fileTbl = state.getFileTable();
            if (fileTbl.isVarDef((StringValue) result)){
                BufferedReader br = fileTbl.lookup((StringValue) result);
                try {
                    String content = br.readLine();
                    symtbl.update(name, new IntValue(Integer.parseInt(content)));
                }catch (NumberFormatException nfe){
                    symtbl.update(name, new IntValue(0));
                }catch (IOException ioe) {
                    throw new MyException("error - " + ioe);
                }
            }else
                throw new MyException(result.toString() + " is not opened");
        }
        else
            throw new MyException(result.toString() + "file name is not String type");
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new ReadFileStmt(exp.deepCopy(), new String(name));
    }

    public String toString(){
        return "read (" + exp.toString() + ", " + name + ")";
    }
}
