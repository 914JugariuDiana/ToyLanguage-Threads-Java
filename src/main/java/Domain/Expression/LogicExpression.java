package Domain.Expression;

import Domain.ADT.MyIDictionary;
import Domain.ADT.MyIHeap;
import Domain.Exception.MyException;
import Domain.Type.BoolType;
import Domain.Type.IntType;
import Domain.Type.Type;
import Domain.Value.BoolValue;
import Domain.Value.Value;

public class LogicExpression implements Expression {
    private Expression e1, e2;
    private int operant;

    public LogicExpression(Expression ee1, Expression ee2, int o){
        e1 = ee1;
        e2 = ee2;
        operant = o; // 1 - and, 2 -or
    }
    public Value eval(MyIDictionary<String,Value> tabel, MyIHeap<Integer, Value> heap)  throws MyException {
        Value res1 = e1.eval(tabel, heap);
        if (res1.getType().equals(new BoolType())){
            Value res2 = e2.eval(tabel, heap);
            if (res2.getType().equals(new BoolType())){
                BoolValue rs1 = (BoolValue) res1;
                BoolValue rs2 = (BoolValue) res2;
                if (operant == 1){
                    return new BoolValue(rs1.getVal() && rs2.getVal());
                }
                else {
                    return new BoolValue(rs1.getVal() || rs2.getVal());
                }
            }else{
                throw new MyException("Operand2 is not a boolean");
            }
        }else{
            throw new MyException("Operand1 is not a boolean");
        }
    }
    @Override
    public String toString(){
        if (operant == 1)
            return e1.toString() + " AND " + e2.toString();
        return e1.toString() + " OR " + e2.toString();
    }

    @Override
    public Expression deepCopy() {
        return new LogicExpression(e1.deepCopy(), e2.deepCopy(), operant);
    }

    @Override
    public Type typeCheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typ1, typ2;
        typ1 = e1.typeCheck(typeEnv);
        typ2 = e2.typeCheck(typeEnv);
        if (typ1.equals(new BoolType())) {
            if (typ2.equals(new BoolType())) {
                return new BoolType();
            } else {
                throw new MyException("second operand is not an integer");
            }
        }else {
            throw new MyException("first operand is not an integer");}
    }
}
