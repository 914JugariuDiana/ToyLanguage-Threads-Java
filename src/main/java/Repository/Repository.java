package Repository;
import Domain.Exception.MyException;
import Domain.Value.StringValue;
import PrgState.PrgState;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;

public class Repository implements IRepository{
    List<PrgState> repo;
    StringValue logFilePath;

    public Repository(StringValue logFP){
        repo = new LinkedList<PrgState>();
        logFilePath = logFP;
    }

    @Override
    public void addPrgState(PrgState state) {
        repo.add(state);
    }

    @Override
    public void logPrgStateExec(PrgState prg) throws MyException {
        try{
            PrintWriter logFile = new PrintWriter(new BufferedWriter(new FileWriter(String.valueOf(logFilePath), true)));
            logFile.write(prg.toString());
            logFile.flush();
        }catch (IOException ioe){
            throw new MyException("error - " + ioe);
        }
    }

    @Override
    public List<PrgState> getPrgList() {
        return repo;
    }

    @Override
    public void setPrgList(List<PrgState> list) {
        repo = list;
    }

    @Override
    public PrgState getPrgById(int id) {
        for (PrgState p : repo)
            if (p.getId() == id)
                return p;
        return null;
    }
}
