package smarthome.util.exceptions;

/**
 * An exception class representing the scenario where a House is called without a location.
 */
public class NoHouseLocationDefined extends RuntimeException {

	/**
	 * Constructs a new NoHouseLocationDefined with a default message.
	 */
	public NoHouseLocationDefined() {
		super("No House Location Defined");
	}
}
