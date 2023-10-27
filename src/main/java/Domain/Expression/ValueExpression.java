package Domain.Expression;

import Domain.ADT.MyIDictionary;
import Domain.ADT.MyIHeap;
import Domain.Exception.MyException;
import Domain.Type.Type;
import Domain.Value.Value;

public class ValueExpression implements Expression {
    private Value e;

    public ValueExpression(Value ee){
        e = ee;
    }
    public Value eval(MyIDictionary<String,Value> tabel, MyIHeap<Integer, Value> heap)  throws MyException {
        return e;
    }

    @Override
    public String toString(){
        return e.toString();
    }

    @Override
    public Expression deepCopy() {
        return new ValueExpression(e.deepCopy());
    }

    @Override
    public Type typeCheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        return e.getType();
    }
}
