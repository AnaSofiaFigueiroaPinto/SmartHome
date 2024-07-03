package smarthome.domain.valueobjects;

import smarthome.ddd.ValueObject;

import java.util.Objects;


public class RoomFloor implements ValueObject {
    private final int roomFloor;

    /**
     * Constructs a RoomFloor object with the specified floor number.
     *
     * @param floorNumber the floor number of the room
     */
    public RoomFloor(int floorNumber) {
        this.roomFloor = floorNumber;
    }

    /**
     * Checks if this RoomFloor object is equal to another object.
     * <p>
     * Two RoomFloor objects are considered equal if they have the same floor number.
     *
     * @param object the object to compare with
     * @return true if the objects are equal, otherwise false
     */
    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        RoomFloor that = (RoomFloor) object;
        return roomFloor == that.roomFloor;
    }

    /**
     * Returns the hash code value for this RoomFloor object.
     *
     * @return the hash code value
     */
    @Override
    public int hashCode() {
        return Objects.hashCode(roomFloor);
    }

    /**
     * Returns a string representation of this RoomFloor object.
     *
     * @return a string representation including the floor number
     */
    @Override
    public String toString() {
        return "FloorNumber{" + "floorNumber=" + roomFloor + '}';
    }

    /**
     * Retrieves the floor number of the room.
     *
     * @return the floor number of the room
     */
    public int getRoomFloor() {
        return roomFloor;
    }

}
