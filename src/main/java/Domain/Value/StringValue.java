package Domain.Value;
import Domain.Type.StringType;
import Domain.Type.Type;

public class StringValue implements Value{
    String val;

    public StringValue(String v){
        val = v;
    }
    public StringValue(){
        val = "";
    }

    public String getVal(){
        return val;
    }

    public String toString(){
        return val.toString();
    }

    @Override
    public Type getType() {
        return new StringType();
    }

    @Override
    public Value deepCopy() {
        return new StringValue(new String(val));
    }
}


