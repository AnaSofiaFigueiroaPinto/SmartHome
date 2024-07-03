package smarthome.util.exceptions;

/**
 * An exception class indicating that the selected actuator functionality is not present in the list of available functionalities.
 */
public class ActuatorFunctionalityNotListedException extends RuntimeException {

    /**
     * Constructs a new SensorFunctionalityNotListedException with a default message.
     */
    public ActuatorFunctionalityNotListedException() {
        super("Actuator functionality not present in the list");
    }
}