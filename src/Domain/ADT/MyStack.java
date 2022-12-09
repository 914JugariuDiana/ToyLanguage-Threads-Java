package Domain.ADT;
import Domain.Exception.MyException;

import java.util.*;

public class MyStack<T> implements MyIStack<T> {
    private Stack<T> stack;

    public MyStack(){
        stack = new Stack<T>();
    }

    @Override
    public T pop() throws MyException{
        if (stack.isEmpty())
            throw new MyException("Empty stack");
        return stack.pop();
    }

    @Override
    public void push(T v) {
        stack.push(v);
    }

    @Override
    public boolean isEmpty() {
        return stack.isEmpty();
    }

    @Override
    public List<T> getReverse() {
        List<T> l = Arrays.asList((T[]) stack.toArray());
        Collections.reverse((l));
        return l;
    }

    public String toString(){
        StringBuilder str = new StringBuilder();
        List<T> list = new ArrayList<>();
        for (T elem : stack)
            list.add(elem);
        Collections.reverse(list);
        for (T elem : list)
            str.append("    " + elem.toString() + "\n");
        return str.toString();
    }

    @Override
    public Stack<T> clone() {
        return (Stack<T>) stack.clone();
    }
}
