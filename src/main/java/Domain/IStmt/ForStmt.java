package Domain.IStmt;

import Domain.ADT.MyIDictionary;
import Domain.Exception.MyException;
import Domain.Expression.Expression;
import Domain.Expression.RelationalExpression;
import Domain.Expression.VariableExpression;
import Domain.Type.IntType;
import Domain.Type.Type;
import PrgState.PrgState;

public class ForStmt implements IStmt{
    private Expression exp1, exp2, exp3;
    private IStmt stmt;
    private String v;

    public ForStmt(Expression exp1, Expression exp2, Expression exp3, IStmt stmt, String v){
        this.exp1 = exp1;
        this.exp2 = exp2;
        this.exp3 = exp3;
        this.stmt = stmt;
        this.v = v;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        IStmt newfor = new CompStmt(new VarDeclStmt(v, new IntType()), new CompStmt (new AssignStmt(
            v, exp1), new WhileStmt(new RelationalExpression(new VariableExpression(v), exp2, "<"),
            new CompStmt(stmt, new AssignStmt(v, exp3)))));
        state.getStk().push(newfor);
        return null;
        /*if (!exp1.eval(state.getSymTable(), state.getHeap()).getType().equals(new IntType()))
            throw new MyException("FOR STMT - expresion 1 does not have int type");
        else {
            v = exp1.eval(state.getSymTable(), state.getHeap())*/
    }

    @Override
    public IStmt deepCopy() {
        return new ForStmt(exp1.deepCopy(), exp2.deepCopy(), exp3.deepCopy(), stmt.deepCopy(), new String(v));
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        //if ()
        return typeEnv;
    }

    public String toString(){
        return "for (" + v + " = " + exp1.toString() + "; " + v + " < " + exp2.toString()  + "; " + v + " = " + exp3.toString() + "{ /n" +
                stmt.toString() + "}";
    }
}
