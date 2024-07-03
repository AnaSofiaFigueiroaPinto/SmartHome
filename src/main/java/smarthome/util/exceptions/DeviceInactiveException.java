package smarthome.util.exceptions;

/**
 * An exception class representing the scenario where a device is inactive.
 */
public class DeviceInactiveException extends RuntimeException {
    /**
     * Constructs a new DeviceInactiveException with a default message.
     */
    public DeviceInactiveException() {
        super("Device is Inactive");
    }
}
