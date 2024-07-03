package smarthome.domain.room;

import org.springframework.stereotype.Component;
import smarthome.domain.valueobjects.HouseID;
import smarthome.domain.valueobjects.RoomDimensions;
import smarthome.domain.valueobjects.RoomFloor;
import smarthome.domain.valueobjects.RoomID;

@Component
public class ImpFactoryRoom implements FactoryRoom {

    /**
     * Creates a new Room object with the provided parameters.
     * <p>
     * This method constructs a new Room object with the specified parameters:
     * {@code roomName}, {@code floorNumber}, {@code length}, {@code width}, {@code height}, and {@code houseId}.
     * It first constructs a RoomID, RoomFloor, and RoomDimensions objects based on the provided parameters.
     * If the construction of these objects is successful, it initializes a new Room object with these objects
     * and returns it. If any of the provided parameters are invalid and result in an IllegalArgumentException,
     * the method returns null, indicating a failure to create the Room object.
     *
     * @param roomID The unique identifier of the room.
     * @param roomFloor The floor number where the room is located.
     * @param roomDimensions The dimensions of the room (length, width, height).
     * @param houseID The unique identifier of the house associated with the room.
     * @return A new Room object initialized with the provided parameters, or null if the parameters are invalid.
     */
    public Room createRoom(RoomID roomID, RoomFloor roomFloor, RoomDimensions roomDimensions, HouseID houseID) {
        try {
            return new Room(roomID, roomFloor, roomDimensions, houseID);
        } catch (IllegalArgumentException exception) {
            return null;
        }
    }
}
