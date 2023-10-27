package Domain.Expression;

import Domain.ADT.MyIDictionary;
import Domain.ADT.MyIHeap;
import Domain.Exception.MyException;
import Domain.Type.RefType;
import Domain.Type.Type;
import Domain.Value.RefValue;
import Domain.Value.Value;

public class HeapReadingExpression implements Expression {
    private Expression expression;

    public HeapReadingExpression(Expression exp){
        expression = exp;
    }

    @Override
    public Value eval(MyIDictionary<String, Value> tabel, MyIHeap<Integer, Value> heap) throws MyException {
        Value result = expression.eval(tabel, heap);
        if (!(result instanceof RefValue))
            throw new MyException(result.toString() + " - is not a reference value");
        if (!heap.isVarDef((((RefValue) result).getAddr())))
            throw new MyException(((RefValue) result).getAddr() + " address is not in heap tabel");
        Value val = heap.lookup(((RefValue) result).getAddr());
        return val;
    }

    @Override
    public String toString() {
        return "rH(" + expression.toString() + ")";
    }

    @Override
    public Expression deepCopy() {
        return new HeapReadingExpression(expression.deepCopy());
    }

    @Override
    public Type typeCheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typ = expression.typeCheck(typeEnv);
        if (typ instanceof RefType){
            RefType reft = (RefType) typ;
            return reft.getInner();
        }else
            throw new MyException("the read heap argument is not a Reference type");
    }
}
