package Controller;
import Domain.ADT.MyIHeap;
import Domain.Exception.MyException;
import Domain.Value.RefValue;
import Domain.Value.Value;
import PrgState.PrgState;
import Repository.IRepository;
import Repository.Repository;
import javafx.scene.control.Alert;

import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class Controller {
    private IRepository repo;
    private ExecutorService executor;
    public Controller(IRepository r){
        repo = r;
    }

    public List<PrgState> removeCompletedPrg(List<PrgState> inPrgList){
        return inPrgList.stream()
                .filter(e -> e.isNotCompleted())
                .collect(Collectors.toList());
    }

    public IRepository getRepo(){
        return repo;
    }
    public void oneStepForAllPrg(List<PrgState> prgList) throws MyException {
        prgList.forEach(prg -> {
            try {
                repo.logPrgStateExec(prg);
            } catch (MyException e) {
                Alert error = new Alert(Alert.AlertType.ERROR, e.toString());
                error.show();
            }
        });

        List<Callable<PrgState>> callList = prgList.stream()
                .map((PrgState p) -> (Callable<PrgState>) (p::oneStep))
                .collect(Collectors.toList());
        try {
            List<PrgState> newPrgList = executor.invokeAll(callList).stream()
                    .map(future -> {
                        try {
                            return future.get();
                        } catch (InterruptedException | ExecutionException e) {
                            Alert error = new Alert(Alert.AlertType.ERROR, e.toString());
                            error.show();
                        }
                        return null;
                    })
                    .filter(Objects::nonNull).toList();
            prgList.addAll(newPrgList);
            repo.setPrgList(prgList);
        } catch (InterruptedException ii) {
            throw new MyException("error - " + ii);
        }

        prgList.forEach(prg -> {
            try {
                repo.logPrgStateExec(prg);
            } catch (MyException e) {
                System.out.println(e);
            }
        });
    }

    public void executeOneStep() throws MyException {
        executor = Executors.newFixedThreadPool(2);
        if (repo.getPrgList().size() > 0)
            try{
                oneStepForAllPrg(repo.getPrgList());
            } catch (MyException e) {
                throw new MyException(e.toString());
            }

        repo.setPrgList(removeCompletedPrg(repo.getPrgList()));
        Map<String, Value> res = new HashMap<>();
        for (int i = 0; i < repo.getPrgList().size(); i++)
            res.putAll(repo.getPrgList().get(i).getSymTable().getContent());

        List<PrgState> prgList = repo.getPrgList();
        if (prgList.size() > 0) {
            prgList.get(0).getHeap().setContent(
                    safeGarbageCollector(getAllAddresses(res.values(), prgList.get(0).getHeap()), prgList.get(0).getHeap().getContent()));
        }

        repo.setPrgList(prgList);
        executor.shutdown();
    }

    public void allStep() throws MyException {
        executor = Executors.newFixedThreadPool(2);
        List<PrgState> prgList = removeCompletedPrg(repo.getPrgList());
        while (prgList.size() > 0) {
            Map <String, Value> res = prgList.get(0).getSymTable().getContent();
            for (int i = 0; i < prgList.size() - 1; i++)
                res.putAll(prgList.get(i).getSymTable().getContent());

            prgList.get(prgList.size()-1).getHeap().setContent(
                    safeGarbageCollector(getAllAddresses(res.values(), prgList.get(prgList.size()-1).getHeap()), prgList.get(prgList.size()-1).getHeap().getContent()));
            oneStepForAllPrg(prgList);
            prgList = removeCompletedPrg(repo.getPrgList());
        }
        executor.shutdownNow();
        repo.setPrgList(prgList);
    }
    public void displayCurrentProgramState(PrgState state){
        System.out.println(state.toString());
    }

    public Map<Integer, Value> safeGarbageCollector(List<Integer> symTableAddr, Map<Integer, Value> heap){
        return heap.entrySet().stream()
                .filter(e->symTableAddr.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public List<Integer> getAllAddresses(Collection<Value> symTableValues, MyIHeap<Integer, Value> heapTableValues){
        ConcurrentLinkedDeque<Integer> result = symTableValues.stream()
                        .filter(v -> v instanceof RefValue)
                        .map(v -> {
                            RefValue v1 = (RefValue) v;
                            return v1.getAddr();
                        })
                        .collect(Collectors.toCollection(ConcurrentLinkedDeque::new));

                result.stream()
                        .forEach(a ->{
                            Value v = heapTableValues.getContent().get(a);
                            if (v instanceof RefValue)
                                if (!result.contains(((RefValue) v).getAddr()))
                                    result.add(((RefValue) v).getAddr());});
        return result.stream().toList();
    }
}