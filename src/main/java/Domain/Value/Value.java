package Domain.Value;

import Domain.Type.Type;

public interface Value {
    Type getType();
    Value deepCopy();
}


