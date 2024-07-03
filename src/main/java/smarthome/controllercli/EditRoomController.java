package smarthome.controllercli;

import smarthome.domain.valueobjects.RoomDimensions;
import smarthome.domain.valueobjects.RoomFloor;
import smarthome.domain.valueobjects.RoomID;
import smarthome.mapper.RoomDTO;
import smarthome.service.RoomService;

/**
 * Controller class responsible for listing and editing rooms.
 */
public class EditRoomController {
    private final RoomService roomService;

    /**
     * Constructs a new controller with the specified
     * RoomServices.
     *
     * @param roomServices The RoomServices instance to interact
     *                     with for room-related operations.
     */
    public EditRoomController(RoomService roomServices) {
        this.roomService = roomServices;
    }

    /**
     * Edits the configuration of a selected room based on
     * the information provided in the given RoomDTO object.
     * @param roomDTO The RoomDTO object containing the new
     *                configuration information for the selected room.
     * @return The updated RoomDTO object if the room
     * configuration was successfully edited; {@code null} otherwise.
     */
    public RoomDTO editSelectedRoomAndSaveInRepository(RoomDTO roomDTO) {
        try {
            //Instantiation of value objects needed to create/edit a room.
            RoomID roomID = new RoomID(roomDTO.roomName);
            RoomFloor roomFloor = new RoomFloor(roomDTO.floorNumber);
            RoomDimensions roomDimensions = new RoomDimensions(
                    roomDTO.roomLength,
                    roomDTO.roomWidth,
                    roomDTO.roomHeight);

            //Edit Room. If creation fails, an exception will be thrown.
            RoomID editedRoomID = roomService.editRoomAndSave(roomID, roomFloor, roomDimensions);
            return new RoomDTO(
                    editedRoomID.toString(),
                    roomDTO.floorNumber,
                    roomDTO.roomLength,
                    roomDTO.roomWidth,
                    roomDTO.roomHeight
            );
        } catch (RuntimeException e) {
            return null;
        }
    }
}