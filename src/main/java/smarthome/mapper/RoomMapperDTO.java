package smarthome.mapper;

import org.springframework.stereotype.Component;
import smarthome.domain.room.Room;
import smarthome.domain.valueobjects.RoomID;

import java.util.ArrayList;
import java.util.List;

/**
 * A mapper class for the Room entity.
 */
@Component
public class RoomMapperDTO {

    /**
     * Converts a Room domain objects into a Room DTO.
     * @param room Room object to be converted.
     * @return RoomDTO representing the converted RoomID.
     */
    public RoomDTO roomToDTO(Room room) {
        if (room == null) {
            return null;
        }
        return new RoomDTO(room.identity().toString(), room.getRoomFloor().getRoomFloor(), room.getRoomDimensions().getLength(), room.getRoomDimensions().getWidth(), room.getRoomDimensions().getHeight());
    }

    /** Converts a RoomID object into a RoomDTO object.
     * @param roomID The RoomID object to be convert.
     * @return RoomDTO representing the converted RoomID.
     */
    public RoomDTO roomToDTO(RoomID roomID) {
        if (roomID == null) {
            return null;
        }
        String roomName = roomID.toString();
        return new RoomDTO(roomName);
    }

    /**
     * Converts a collection of RoomIDs to a list of RoomDTO objects.
     * @param roomsIDs The collection of RoomIDs to be converted.
     * @return List of RoomDTO objects.
     */
    public List<RoomDTO> roomIDsToDTOList(Iterable<RoomID> roomsIDs) {
        List<RoomDTO> listOfRoomsDTO = new ArrayList<>();
        for (RoomID roomID : roomsIDs) {
            RoomDTO roomDTO = roomToDTO(roomID);
            listOfRoomsDTO.add(roomDTO);
        }
        return listOfRoomsDTO;
    }
}
