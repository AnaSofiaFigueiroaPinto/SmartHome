package smarthome.service.internaldto;

import smarthome.domain.valueobjects.HouseID;
import smarthome.domain.valueobjects.RoomDimensions;
import smarthome.domain.valueobjects.RoomFloor;
import smarthome.domain.valueobjects.RoomID;

public class InternalRoomDTO {

    /**
     * The unique identifier of the room.
     */
    public RoomID roomID;

    /**
     * The floor location of the room within the house.
     */
    public RoomFloor roomFloor;

    /**
     * The dimensions of the room.
     */
    public RoomDimensions roomDimensions;

    /**
     * The unique identification of the house to which the room belongs.
     */
    public HouseID houseID;

    /**
     * Constructs a new InternalRoomDTO with the specified room ID, room floor, room dimensions, and house ID.
     *
     * @param roomID the ID of the room
     * @param roomFloor the floor location of the room
     * @param roomDimensions the dimensions of the room
     * @param houseID the ID of the house
     */

    public InternalRoomDTO(RoomID roomID, RoomFloor roomFloor, RoomDimensions roomDimensions, HouseID houseID) {
        this.roomID = roomID;
        this.roomFloor = roomFloor;
        this.roomDimensions = roomDimensions;
        this.houseID = houseID;
    }
}
