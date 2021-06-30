package utils.exceptions;

public class MemberUnableToRentException extends Exception {

    private String valueName;

    public MemberUnableToRentException(String valueName){
        this.valueName = valueName;
    }

    public String getValueName(){
        return this.valueName;
    }
}