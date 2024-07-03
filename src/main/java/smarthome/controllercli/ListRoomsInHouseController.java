package smarthome.controllercli;

import smarthome.domain.valueobjects.HouseID;
import smarthome.domain.valueobjects.RoomID;
import smarthome.mapper.RoomDTO;
import smarthome.mapper.RoomMapperDTO;
import smarthome.service.RoomService;

import java.util.List;

/**
 * Controller class responsible to list all room in the house.
 */
public class ListRoomsInHouseController {
    /**
     * RoomService instance to interact with room-related operations.
     */
    private final RoomService roomService;
    /**
     * HouseID representing the house to which the rooms belong.
     */
    private final HouseID houseID;

        /**
         * Constructs a new ListAllDevicesInRoomController with the specified services and house ID.
         * @param roomService   The RoomService instance to interact with for room-related operations.
         * @param houseID       The HouseID representing the house to which the devices and rooms belong.
         */
        public ListRoomsInHouseController(RoomService roomService, HouseID houseID) {
            this.roomService = roomService;
            this.houseID = houseID;
        }

        /**
         * Retrieves a list of RoomDTO objects belonging to the specified house.
         * @return A list of RoomDTO objects associated with the specified house.
         */
        public List<RoomDTO> getListOfRoomsDTOInHouse() {
            try {
                RoomMapperDTO roomMapperDTO = new RoomMapperDTO();
                List<RoomID> listOfRoomsIDs = roomService.getListOfRoomsInHouse(houseID);
                return roomMapperDTO.roomIDsToDTOList(listOfRoomsIDs);
            } catch (RuntimeException e) {
                return null;
            }
        }

}
