package utils.exceptions;

public class MissingValueException extends Exception {

    private String valueName;

    public MissingValueException(String valueName){
        this.valueName = valueName;
    }

    public String getValueName(){
        return this.valueName;
    }
}
