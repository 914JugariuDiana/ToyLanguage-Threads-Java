package Domain.Expression;

import Domain.ADT.MyIDictionary;
import Domain.ADT.MyIHeap;
import Domain.Exception.MyException;
import Domain.Type.BoolType;
import Domain.Type.IntType;
import Domain.Type.Type;
import Domain.Value.BoolValue;
import Domain.Value.IntValue;
import Domain.Value.Value;

public class RelationalExpression implements Expression{
    private Expression expression1;
    private Expression expression2;
    private String operant;

    public RelationalExpression(Expression e1, Expression e2, String o){
        expression1 = e1;
        expression2 = e2;
        operant = o;
    }

    @Override
    public Value eval(MyIDictionary<String, Value> tabel, MyIHeap<Integer, Value> heap) throws MyException {
        Value result1 = expression1.eval(tabel, heap);
        if (result1.getType().equals(new IntType())){
            Value result2 = expression2.eval(tabel, heap);
            if (result2.getType().equals(new IntType())){
                IntValue op1 = (IntValue) result1;
                IntValue op2 = (IntValue) result2;
                if (operant.equals("<")){
                    if (op1.getVal() < op2.getVal())
                        return new BoolValue(true);
                    return new BoolValue(false);
                } else if (operant.equals("<=")){
                    if (op1.getVal() <= op2.getVal())
                        return new BoolValue(true);
                    return new BoolValue(false);
                } else if (operant.equals("==")){
                    if (op1.getVal() == op2.getVal())
                        return new BoolValue(true);
                    return new BoolValue(false);
                } else if (operant.equals("!=")){
                    if (op1.getVal() != op2.getVal())
                        return new BoolValue(true);
                    return new BoolValue(false);
                } else if (operant.equals(">")){
                    if (op1.getVal() > op2.getVal())
                        return new BoolValue(true);
                    return new BoolValue(false);
                } else {
                    if (op1.getVal() >= op2.getVal())
                        return new BoolValue(true);
                    return new BoolValue(false);
                }
            } else
                throw new MyException("Second operand does not have int type");
        }else
            throw new MyException("First operand does not have int type");
    }

    @Override
    public Expression deepCopy() {
        return new RelationalExpression(expression1.deepCopy(), expression2.deepCopy(), new String(operant));
    }

    @Override
    public Type typeCheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typ1, typ2;
        typ1 = expression1.typeCheck(typeEnv);
        typ2 = expression2.typeCheck(typeEnv);
        if (typ1.equals(new IntType())) {
            if (typ2.equals(new IntType())) {
                return new BoolType();
            } else {
                throw new MyException("second operand is not an integer");
            }
        }else {
            throw new MyException("first operand is not an integer");}
    }

    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append(expression1.toString());
        if (operant.equals("<")) {
            str.append(" < ");
        } else if (operant.equals("<=")) {
            str.append(" <= ");
        } else if (operant.equals("==")) {
            str.append(" == ");
        } else if (operant.equals("!=")) {
            str.append(" != ");
        } else if (operant.equals(">")) {
            str.append(" > ");
        } else {
            str.append(" >= ");
        }
        str.append(expression2.toString());
        return str.toString();
    }
}
