package smarthome.util.exceptions;

/**
 * An exception class indicating that the selected sensor functionality is not present in the list of available functionalities.
 */
public class SensorFunctionalityNotListedException extends RuntimeException {

    /**
     * Constructs a new SensorFunctionalityNotListedException with a default message.
     */
    public SensorFunctionalityNotListedException() {
        super("Sensor functionality not present in the list");
    }
}