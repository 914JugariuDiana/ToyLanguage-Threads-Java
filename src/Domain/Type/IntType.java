package Domain.Type;

import Domain.Value.IntValue;
import Domain.Value.Value;

public class IntType implements Type {
    public boolean equals(Object another) {
        if (another instanceof IntType)
            return true;
        else
            return false;
    }
    public String toString(){
        return "int";
    }

    @Override
    public Value getDefaultValue() {
        return new IntValue();
    }

    @Override
    public Type deepCopy() {
        return new IntType();
    }
}
