package smarthome.util.exceptions;

public class DeviceNotCreatedException extends RuntimeException {
    /**
     * Constructs a new DeviceNotCreatedException with a default message.
     */
    public DeviceNotCreatedException() {
        super("Device was not successfully created");
    }
}
