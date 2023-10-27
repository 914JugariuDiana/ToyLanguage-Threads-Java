package Domain.Type;

import Domain.Value.RefValue;
import Domain.Value.Value;

public class RefType implements Type{
    private Type inner;

    public RefType (Type i){
        inner = i;
    }

    public Type getInner(){
        return inner;
    }

    public boolean equals(Object another){
        if (another instanceof RefType)
            return inner.equals(((RefType) another).getInner());
        else
            return false;
    }

    @Override
    public String toString() {
        return "Ref " + inner.toString();
    }

    @Override
    public Value getDefaultValue() {
        return new  RefValue(0, inner);
    }

    @Override
    public Type deepCopy() {
        return new RefType(inner.deepCopy());
    }
}
