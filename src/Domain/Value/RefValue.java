package Domain.Value;

import Domain.Type.RefType;
import Domain.Type.Type;

public class RefValue implements Value{
    private int addr;
    private Type type;
    public RefValue(int h, Type t){
        addr = h;
        type = t;
    }

    public int getAddr(){
        return addr;
    }

    @Override
    public Type getType() {
        return new RefType(type);
    }

    public String toString(){
        return "(" + Integer.toString(addr) + ", " + type.toString() + ")";
    }

    @Override
    public Value deepCopy() {
        return new RefValue(addr, type.deepCopy());
    }
}
