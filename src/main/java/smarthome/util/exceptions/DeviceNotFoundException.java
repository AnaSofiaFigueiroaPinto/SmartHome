package smarthome.util.exceptions;

/**
 * An exception class representing the scenario where a device is not found in the repository.
 */
public class DeviceNotFoundException extends RuntimeException {

    /**
     * Constructs a new DeviceNotFoundException with a default message.
     */
    public DeviceNotFoundException() {
        super("Device not found in Repository");
    }
}
