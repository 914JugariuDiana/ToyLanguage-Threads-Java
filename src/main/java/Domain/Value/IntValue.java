package Domain.Value;

import Domain.Type.IntType;
import Domain.Type.Type;

public class IntValue implements Value{
    int val;
    public IntValue(int v){
        val = v;
    }
    public IntValue(){
        val = 0;
    }
    public int getVal() {
        return val;
    }

    public String toString(){
        return Integer.toString(val);
    }

    public Type getType(){
        return new IntType();
    }

    @Override
    public Value deepCopy() {
        return new IntValue(val);
    }
}
