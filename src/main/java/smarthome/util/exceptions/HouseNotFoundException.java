package smarthome.util.exceptions;

/**
 * An exception class representing the scenario where a House does not exist in repository.
 */
public class HouseNotFoundException extends RuntimeException {
    /**
     * Constructs a new HouseNotFoundException with a default message.
     */
    public HouseNotFoundException() {
        super("House not found in Repository");
    }
}
