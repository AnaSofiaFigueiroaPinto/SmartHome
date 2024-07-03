package smarthome.util.exceptions;

public class ValueNotFoundException extends RuntimeException{
    /**
     * Constructs a new ValueNotFoundException with a default message.
     */
    public ValueNotFoundException() {
        super("Value not found in Repository");
    }
}
