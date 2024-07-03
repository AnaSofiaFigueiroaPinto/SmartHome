package smarthome.domain.room;

import smarthome.ddd.AggregateRoot;
import smarthome.domain.valueobjects.HouseID;
import smarthome.domain.valueobjects.RoomDimensions;
import smarthome.domain.valueobjects.RoomFloor;
import smarthome.domain.valueobjects.RoomID;

/**
 * Represents a room in a house.
 */
public class Room implements AggregateRoot<RoomID> {
    /**
     * The unique identification of the house to which the room belongs.
     */
    private final HouseID houseID;

    /**
     * The unique identification of the room.
     */
    private final RoomID roomID;

    /**
     * The floor location of the room within the house.
     */
    private RoomFloor roomFloor;

    /**
     * The dimensions of the room.
     */
    private RoomDimensions roomDimensions;

    /**
     * Constructor of room object using parameters
     *
     * @param roomID         Name of Room object. Cannot be null.
     * @param roomFloor      Location of Room object (floor) in House object.
     * @param roomDimensions Dimensions of Room object.
     * @param houseID        Unique identification of the House to which a
     *                       Room belongs to.
     * @throws IllegalArgumentException when given attributes don't respect
     * conditions to create a valid Room object.
     */
    protected Room(RoomID roomID, RoomFloor roomFloor,
                   RoomDimensions roomDimensions, HouseID houseID) {
      if (!validConstructorArguments(roomID, roomFloor, roomDimensions, houseID)) {
            throw new IllegalArgumentException("Room name, floor, dimensions or House ID cannot be null or empty");
      }
      this.roomID = roomID;
      this.roomFloor = roomFloor;
      this.roomDimensions = roomDimensions;
      this.houseID = houseID;
    }

    /**
     * Method to validate the parameters for Room instantiation.
     *
     * @param roomID    Name of Room. Cannot be null.
     * @param roomFloor  Floor of Room. Cannot be null.
     * @param roomDimensions Dimensions of Room. Cannot be null.
     * @param roomID      The roomID of the room where the device is located. Cannot be null.
     * @return true if parameters are valid, false otherwise.
     */

    private boolean validConstructorArguments(RoomID roomID, RoomFloor roomFloor, RoomDimensions roomDimensions, HouseID houseID) {
        return roomID != null && roomFloor != null && roomDimensions != null && houseID != null;
    }

    /**
     * Edits the configuration of the room by updating its
     * floor and dimensions.
     * <p>
     * If any argument is invalid (e.g., null),
     * it catches the IllegalArgumentException and
     * returns null.
     * @param roomFloor      The new floor configuration
     *                       for the room.
     * @param roomDimensions The new dimensions configuration
     *                       for the room.
     * @return The Room object if the room configuration was
     * successfully updated, Null otherwise.
     */
    public Room editRoom (RoomFloor roomFloor,
                         RoomDimensions roomDimensions) {
    if (roomFloor == null || roomDimensions == null) {
            return null;
        }
    this.roomFloor = roomFloor;
    this.roomDimensions = roomDimensions;
    return this;
    }

    /**
     * Method that returns the floor of a Room object.
     * @return RoomFloor attribute of a Room object.
     */
    public RoomFloor getRoomFloor() {
    return roomFloor;
    }

    /**
     * Method that returns the Dimensions of a Room object.
     * @return RoomDimensions attribute of a Room object.
     */
    public RoomDimensions getRoomDimensions() {
    return roomDimensions;
    }

    /**
     * Method that returns the ID of a Room object.
     * @return RoomName attribute of a Room object.
     */
    @Override
    public RoomID identity() {
    return roomID;
    }

    /**
     * Method that returns the HouseID of a Room object.
     * @return HouseID attribute of a Room object.
     */
    public HouseID getHouseID() {
    return houseID;
    }

    /**
     * Method to check if two objects of this type are the same.
     * @param object instance of Room.
     * @return True if the objects are the same, false if they are not.
     */
    @Override
    public boolean isSameAs(Object object) {

    if (object instanceof Room objectRoom) {

      return this.roomID.equals(objectRoom.roomID) &&
              this.roomFloor.equals(objectRoom.
                            roomFloor) &&
              this.roomDimensions.equals(objectRoom.
                            roomDimensions) &&
              this.houseID.equals(objectRoom.houseID);
        }
      return false;
    }
}

