package Domain.Expression;

import Domain.ADT.MyIDictionary;
import Domain.ADT.MyIHeap;
import Domain.Exception.MyException;
import Domain.Type.Type;
import Domain.Value.Value;

public interface Expression {
    Value eval(MyIDictionary<String, Value> tabel, MyIHeap<Integer, Value> heap) throws MyException;
    String toString();

    Expression deepCopy();

    Type typeCheck(MyIDictionary<String, Type> typeEnv) throws MyException;
}
