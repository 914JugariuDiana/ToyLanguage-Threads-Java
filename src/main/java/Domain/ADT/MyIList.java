package Domain.ADT;

import java.util.List;

public interface MyIList<T> {
    void add(T e);
    String toString();
    public List<T> getContent();
}
