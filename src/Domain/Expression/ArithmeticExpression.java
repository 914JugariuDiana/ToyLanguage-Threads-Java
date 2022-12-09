package Domain.Expression;

import Domain.ADT.MyIDictionary;
import Domain.ADT.MyIHeap;
import Domain.Exception.MyException;
import Domain.Type.IntType;
import Domain.Value.IntValue;
import Domain.Value.Value;

public class ArithmeticExpression implements Expression {
    private Expression e1;
    private Expression e2;
    private int operant; // 1-plus, 2-minus, 3-star, 4-divide

    public ArithmeticExpression(Expression ee1, Expression ee2, int o) {
        e1 = ee1;
        e2 = ee2;
        operant = o;
    }

    public Value eval(MyIDictionary<String, Value> tabel, MyIHeap<Integer, Value> heap) throws MyException {
        Value v1, v2;
        v1 = e1.eval(tabel, heap);
        if (v1.getType().equals(new IntType())) {
            v2 = e2.eval(tabel, heap);
            if (v2.getType().equals(new IntType())) {
                IntValue i1 = (IntValue) v1;
                IntValue i2 = (IntValue) v2;
                int n1, n2;
                n1 = i1.getVal();
                n2 = i2.getVal();
                switch (operant) {
                    case 1:
                        return new IntValue(n1 + n2);
                    case 2:
                        return new IntValue(n1 - n2);
                    case 3:
                        return new IntValue(n1 * n2);
                    case 4: {
                        if (n2 == 0)
                            throw new MyException("Can not divide by zero");
                        else
                            return new IntValue(n1 / n2);
                    }
                    default:
                        throw new MyException("operand is not recognized");
                }
            } else
                throw new MyException("first operand is not an integer");

        } else
            throw new MyException("second operand is not an integer");
    }
    @Override
    public String toString(){
        return switch (operant) {
            case 1 -> e1.toString() + " + " + e2.toString();
            case 2 -> e1.toString() + " - " + e2.toString();
            case 3 -> e1.toString() + " * " + e2.toString();
            default -> e1.toString() + " / " + e2.toString();
        };
    }

    @Override
    public Expression deepCopy() {
        return new ArithmeticExpression(e1.deepCopy(), e2.deepCopy(), operant);
    }
}