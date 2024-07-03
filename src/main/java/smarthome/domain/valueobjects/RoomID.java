package smarthome.domain.valueobjects;

import smarthome.ddd.DomainID;

/**
 * Represents a {@code RoomID} class within the smart home system.
 */
public class RoomID implements DomainID {
    /**
     * Declaring the attributes that will be used in the constructors.
     */
    private final String roomName;

    /**
     * Constructor that receives a string and creates a new RoomID.
     * @param roomName String that represents the name of the room.
     * @throws IllegalArgumentException in case the roomName is not valid.
     */
    public RoomID(String roomName) {
        if (roomName != null && !roomName.isEmpty() && !roomName.isBlank())
            this.roomName = roomName;
        else
            throw new IllegalArgumentException("This parameter 'roomName' must be a non-empty string.");

    }

    /**
     * Method that returns the hash code of the RoomID.
     * @return Integer representing the hash code of the RoomID.
     */
    @Override
    public int hashCode() {
        return roomName.hashCode();
    }

    /**
     * Method that checks if two RoomID are equal.
     * @param object Object that is compared to the RoomID.
     * @return True if the RoomID is equal to the object. False if it is not.
     */
    @Override
    public boolean equals(Object object) {
        if (this == object)
            return true;
        if (object == null || getClass() != object
                .getClass())
            return false;
        RoomID roomID = (RoomID) object;
        return roomName.equals(roomID.roomName);
    }

    /**
     * Method that returns the name of the room.
     * @return String representing the name of the room.
     */
    @Override
    public String toString() {
        return roomName;
    }

}
