package smarthome.util.exceptions;
/**
 * An exception class representing the scenario where a Room does not exist in repository.
 */
public class RoomNotFoundException extends RuntimeException{
    /**
     * Constructs a new RoomNotFoundException with a default message.
     */
    public RoomNotFoundException() {
        super("Room not found in Repository");
    }
}
