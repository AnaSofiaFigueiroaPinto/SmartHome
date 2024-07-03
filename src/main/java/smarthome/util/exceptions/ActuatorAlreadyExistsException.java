package smarthome.util.exceptions;

/**
 * An exception class representing the scenario where an actuator already exists in the Repository.
 */
public class ActuatorAlreadyExistsException extends RuntimeException {

	/**
	 * Constructs a new ActuatorAlreadyExistsException with a default message.
	 */
	public ActuatorAlreadyExistsException() {
		super("Actuator already exists");
	}
}
