package smarthome.controllercli;

import smarthome.domain.valueobjects.HouseID;
import smarthome.domain.valueobjects.RoomDimensions;
import smarthome.domain.valueobjects.RoomFloor;
import smarthome.domain.valueobjects.RoomID;
import smarthome.mapper.RoomDTO;
import smarthome.service.RoomService;

/**
 * This controller manages the addition of a new room to a house
 * by interacting with the {@code RoomService}.
 */
public class AddNewRoomToHouseController {
  /**
   * RoomService instance to interact with room-related operations.
   */
  private final RoomService roomService;
  /**
   * HouseID representing the house to which the rooms belong.
   */
  private final HouseID houseID;

    /**
     * Constructor for the AddNewRoomToHouseController class.
     * @param roomService The {@code RoomService} responsible for
     *                 updating and saving all rooms.
     */
  public AddNewRoomToHouseController(RoomService roomService, HouseID houseID) {
    this.roomService = roomService;
    this.houseID = houseID;
    }

    /**
     * This method creates a new room using the details provided in
     * the {@code roomDTO} object.
     * @param roomDTO The object representing the details of the room
     *                to be created and saved.
     * @return The {@code RoomDTO} object representing the details of the
     * newly created room if the creation and saving process is
     * successful; otherwise, {@code null}.
     */
    public RoomDTO createRoomAndSaveInRepository(RoomDTO roomDTO) {
      try {
        RoomID roomID = new RoomID(roomDTO.roomName);
        RoomFloor roomFloor = new RoomFloor(roomDTO.floorNumber);
        RoomDimensions roomDimensions = new RoomDimensions(
                roomDTO.roomLength,
                roomDTO.roomWidth,
                roomDTO.roomHeight);

        //Create Room. If creation fails, an exception will be thrown.
        RoomID createdRoomID = roomService.createRoomAndSave(roomID, roomFloor, roomDimensions, houseID);
        return new RoomDTO(
                createdRoomID.toString(),
                roomDTO.floorNumber,
                roomDTO.roomLength,
                roomDTO.roomWidth,
                roomDTO.roomHeight);
      } catch (RuntimeException e) {
        return null;
      }
    }
}

