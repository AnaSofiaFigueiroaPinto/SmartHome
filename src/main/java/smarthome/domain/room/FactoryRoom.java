package smarthome.domain.room;

import smarthome.domain.valueobjects.HouseID;
import smarthome.domain.valueobjects.RoomDimensions;
import smarthome.domain.valueobjects.RoomFloor;
import smarthome.domain.valueobjects.RoomID;

public interface FactoryRoom {
    /**
     * Interface to represent a factory to create a new room.
     * <p>
     * {@code roomName}, {@code floorNumber}, {@code length}, {@code width}, {@code height}, and {@code houseID}.
     * @param roomID The unique identifier of the room.
     * @param roomFloor The floor number where the room is located.
     * @param roomDimensions The dimensions of the room (length, width, height).
     * @param houseID The unique identifier of the house associated with the room.
     * @return A new Room object initialized with the provided parameters, or null if the parameters are invalid.
     */
    Room createRoom(RoomID roomID, RoomFloor roomFloor, RoomDimensions roomDimensions, HouseID houseID);
}
