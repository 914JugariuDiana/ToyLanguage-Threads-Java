package Domain.ADT;

import java.util.ArrayList;
import java.util.List;

public class MyList<T> implements MyIList<T>{
    List<T> out;

    public MyList(){
        out = new ArrayList<T>();
    }
    @Override
    public void add(T e) {
        out.add(e);
    }

    public String toString(){
        StringBuilder str = new StringBuilder();
        for (T elem : out)
            str.append("    " + elem.toString() + "\n");
        return str.toString();
    }

}
