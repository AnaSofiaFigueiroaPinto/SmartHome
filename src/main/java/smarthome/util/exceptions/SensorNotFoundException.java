package smarthome.util.exceptions;

public class SensorNotFoundException extends RuntimeException{
    /**
     * Constructs a new SensorNotFoundException with a default message.
     */
    public SensorNotFoundException() {
        super("Sensor not found in Repository");
    }
}
