package Domain.Expression;

import Domain.ADT.MyIDictionary;
import Domain.ADT.MyIHeap;
import Domain.Exception.MyException;
import Domain.Type.Type;
import Domain.Value.Value;

public class VariableExpression implements Expression {
    private String id;

    public VariableExpression(String i){
        id = i;
    }
    @Override
    public Value eval(MyIDictionary<String, Value> tabel, MyIHeap<Integer, Value> heap) throws MyException {
        return tabel.lookup(id);
    }
    @Override
    public String toString(){
        return id;
    }

    @Override
    public Expression deepCopy() {
        return new VariableExpression(new String(id));
    }

    @Override
    public Type typeCheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        return typeEnv.lookup(id);
    }
}
