package Domain.ADT;


import Domain.Exception.MyException;

import java.util.*;
public interface MyIStack<T> {
    T pop() throws MyException;
    void push(T v);
    boolean isEmpty();
    List<T> getReverse();
    String toString();
    Stack<T> clone();
    Stack<T> getStack();
}
