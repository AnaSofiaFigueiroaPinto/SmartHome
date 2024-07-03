package smarthome.util.exceptions;
/**
 * An exception class representing the scenario where a Room does not exist in repository.
 */
public class RoomAlreadyExistsException extends RuntimeException {
    /**
     * Constructs a new RoomNotFoundException with a default message.
     */
        public RoomAlreadyExistsException() {
            super("Room already exists in Repository");
        }
}
