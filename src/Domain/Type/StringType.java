package Domain.Type;

import Domain.Value.IntValue;
import Domain.Value.StringValue;
import Domain.Value.Value;

public class StringType implements Type{
    @Override
    public Value getDefaultValue() {
        return new StringValue();
    }

    @Override
    public Type deepCopy() {
        return new StringType();
    }

    public String toString(){ return  "string";}

    public boolean equals(Object another) {
        if (another instanceof StringType)
            return true;
        else
            return false;
    }

}
