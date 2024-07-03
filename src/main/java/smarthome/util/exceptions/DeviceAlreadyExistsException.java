package smarthome.util.exceptions;

/**
 * An exception class representing the scenario where a device already exists in the Repository.
 */
public class DeviceAlreadyExistsException extends RuntimeException {
    /**
     * Constructs a new DeviceAlreadyExistsException with a default message.
     */
    public DeviceAlreadyExistsException() {
        super("Device already exists");
    }
}
