package Repository;

import Domain.Exception.MyException;
import PrgState.PrgState;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public interface IRepository {
    void addPrgState(PrgState state);
    void logPrgStateExec(PrgState prg) throws MyException;
    List<PrgState> getPrgList();
    void setPrgList(List<PrgState> list);
}
