package smarthome.util.exceptions;

public class SingleHouseViolationException extends RuntimeException{

    public SingleHouseViolationException() {
        super("A house already exists. System only allows one house.");
    }

}
