package Domain.IStmt;

import Domain.Exception.MyException;
import PrgState.PrgState;

public interface IStmt {
    PrgState execute(PrgState state) throws MyException;
    IStmt deepCopy();
}
