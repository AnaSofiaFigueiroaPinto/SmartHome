package smarthome.util.exceptions;

public class NoHouseInRepositoryException extends RuntimeException {
    /**
     * Constructs a new NoHouseInRepositoryException with a default message.
     */
    public NoHouseInRepositoryException() {
        super("No House in Repository");
    }
}
