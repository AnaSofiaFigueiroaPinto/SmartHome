package smarthome.util.exceptions;

/**
 * An exception class representing the scenario where a sensor already exists in the Repository.
 */
public class SensorAlreadyExistsException extends RuntimeException {

    /**
     * Constructs a new SensorAlreadyExistsException with a default message.
     */
    public SensorAlreadyExistsException() {
        super("Sensor already exists");
    }
}