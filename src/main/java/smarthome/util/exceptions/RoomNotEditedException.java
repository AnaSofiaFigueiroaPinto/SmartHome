package smarthome.util.exceptions;
/**
 * An exception class representing the scenario where a Room is not edited.
 */
public class RoomNotEditedException extends RuntimeException {
    /**
     * Constructs a new RoomNotEditedException with a default message.
     */
    public RoomNotEditedException() {
        super("Failed to edit Room");
    }
}
