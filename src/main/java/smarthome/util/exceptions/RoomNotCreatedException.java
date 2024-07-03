package smarthome.util.exceptions;
/**
 * An exception class representing the scenario where a Room is not created.
 */
public class RoomNotCreatedException extends RuntimeException {
    /**
     * Constructs a new RoomNotCreatedException with a default message.
     */
    public RoomNotCreatedException() {
        super("Room not created");
    }
}
