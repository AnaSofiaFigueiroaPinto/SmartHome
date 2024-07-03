package smarthome.util.exceptions;

public class ActuatorNotFoundException extends RuntimeException{
/**
 * Constructs a new ActuatorNotFoundException with a default message.
 */
public ActuatorNotFoundException() {
	super("Actuator not found in Repository");
}
}
