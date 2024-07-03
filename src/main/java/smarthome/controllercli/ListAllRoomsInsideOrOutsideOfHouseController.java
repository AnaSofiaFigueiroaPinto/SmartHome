package smarthome.controllercli;

import smarthome.domain.valueobjects.HouseID;
import smarthome.mapper.HouseDTO;
import smarthome.mapper.RoomDTO;
import smarthome.mapper.RoomMapperDTO;
import smarthome.service.RoomService;

import java.util.List;

public class ListAllRoomsInsideOrOutsideOfHouseController {

    private final RoomService roomService;

    public ListAllRoomsInsideOrOutsideOfHouseController(RoomService roomService) {
        this.roomService = roomService;
    }

    public List<RoomDTO> getListOfRoomsDTOInsideOrOutsideHouse(HouseDTO houseDTO, boolean areRoomsInside) {
        try {
            HouseID houseID = new HouseID(houseDTO.houseID);
            RoomMapperDTO roomMapperDTO = new RoomMapperDTO();
                return roomMapperDTO.roomIDsToDTOList(roomService.getListOfRoomsInsideOrOutsideHouse(houseID, areRoomsInside));
        } catch (NullPointerException e) {
            return null;
        }
    }

}
