package smarthome.service;

import org.springframework.stereotype.Service;
import smarthome.domain.repository.HouseRepository;
import smarthome.domain.repository.RoomRepository;
import smarthome.domain.room.FactoryRoom;
import smarthome.domain.room.Room;
import smarthome.domain.valueobjects.HouseID;
import smarthome.domain.valueobjects.RoomDimensions;
import smarthome.domain.valueobjects.RoomFloor;
import smarthome.domain.valueobjects.RoomID;
import smarthome.service.internaldto.InternalRoomDTO;
import smarthome.util.exceptions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


/**
 * Service class responsible for managing rooms in the SmartHome system.
 * This class provides methods for creating, editing, and filtering rooms,
 * as well as handling room-related operations.
 */
@Service
public class RoomService {
    /**
     * Repository for managing room data in memory.
     */
    private RoomRepository roomRepository;

    /**
     * Factory for creating room instances.
     */
    private FactoryRoom factoryRoom;

    /**
     * Repository for managing house data in memory.
     */
    private HouseRepository houseRepository;

    /**
     * Constructor of RoomServices that receive as parameters roomRepository,
     * FactoryRoom and HouseID.
     *
     * @param roomRepository  Repository where all rooms of a house
     *                        are saved.
     * @param factoryRoom     FactoryRoom interface. This interface is
     *                        responsible to create new Room objects.
     * @param houseRepository Repository that contains all information
     *                        about House.
     */
    public RoomService(RoomRepository roomRepository, FactoryRoom factoryRoom, HouseRepository houseRepository) {
        this.roomRepository = roomRepository;
        this.factoryRoom = factoryRoom;
        this.houseRepository = houseRepository;
    }

    /**
     * Creates a new Room object with the provided parameters and saves it
     * in the room repository.
     *
     * @param roomID         Name of Room object.
     * @param roomFloor      Location of Room object (floor) in House object.
     * @param roomDimensions Dimensions of Room object.
     * @param houseID        Unique identifier of the House to which a
     *                       Room belongs to.
     * @return RoomID object representing the newly created room
     * @throws HouseNotFoundException if the house with the provided ID does not exist in the repository
     * @throws RoomAlreadyExistsException if a room with the provided ID already exists in the repository
     */
    public RoomID createRoomAndSave(RoomID roomID, RoomFloor roomFloor,
                                    RoomDimensions roomDimensions, HouseID houseID) {
        if (!houseRepository.containsEntityByID(houseID)) {
            throw new HouseNotFoundException();
        }
        //Create room
        Room room = factoryRoom.createRoom(roomID, roomFloor, roomDimensions, houseID);
        if (room == null) {
            throw new RoomNotCreatedException();
        }
        if (roomRepository.containsEntityByID(room.identity())) {
            throw new RoomAlreadyExistsException();
        }
        //Save room to repository
        roomRepository.save(room);

        return room.identity();
    }

    /**
     * Filters rooms by the provided house identifier and returns a
     * list of RoomIDs.
     *
     * @param houseID The identifier of the house to filter the rooms.
     * @return A list of RoomID objects representing the rooms in the
     * specified house or {@code null} if the {@code houseID} does not exist.
     * @throws HouseNotFoundException if the house with the provided ID does not exist in the repository
     */
    public List<RoomID> getListOfRoomsInHouse(HouseID houseID) {
        //Check if house exists
        if (!houseRepository.containsEntityByID(houseID)) {
            throw new HouseNotFoundException();
        }
        // Get all rooms from a house from the repository
        Iterable<Room> listOfRoomsInHouse = roomRepository.findByHouseID(houseID);

        // Get list of rooms IDs of the specified house
        List<RoomID> listOfRoomsIDInHouse = new ArrayList<>();

        for (Room room : listOfRoomsInHouse) {
            listOfRoomsIDInHouse.add(room.identity());
        }

        return listOfRoomsIDInHouse;
    }

    /**
     * Edits the configuration of a room based on the information provided
     *
     * @param roomID         Name of Room object.
     * @param roomFloor      Location of Room object (floor) in House object.
     * @param roomDimensions Dimensions of Room object.
     * @return RoomID object representing the edited room
     * @throws RoomNotFoundException if the room with the provided ID does not exist in the repository
     */
    public RoomID editRoomAndSave(RoomID roomID,
                                  RoomFloor roomFloor,
                                  RoomDimensions roomDimensions) {
        // Get the room to edit from the roomID
        final Room roomToEdit = roomRepository.findEntityByID(roomID).orElseThrow(RoomNotFoundException::new);

        // Edit room
        if (roomToEdit != null && roomToEdit.editRoom(roomFloor, roomDimensions) != null) {
            roomRepository.save(roomToEdit);
            return roomToEdit.identity();
        }
        throw new RoomNotEditedException();
    }

    /**
     * Retrieves a list of RoomID objects representing the rooms located inside or outside the specified house.
     * Searches for the house ID corresponding to the provided HouseID. If the houseID exists in the house repository,
     * iterates over all rooms of that house and returns a list of Room IDs.
     *
     * @param houseID The unique identifier of the house to search for rooms in.
     * @param areRoomsInside A boolean value indicating whether to search for rooms inside or outside the house.
     * @return A list of RoomID objects representing the rooms located inside or outside the specified house
     * @throws HouseNotFoundException if the house with the provided ID does not exist in the repository
     */
    public List<RoomID> getListOfRoomsInsideOrOutsideHouse(HouseID houseID, boolean areRoomsInside) {
        //Check if room with a certain ID exists in the repository
        if (!houseRepository.containsEntityByID(houseID)) {
            throw new HouseNotFoundException();
        }

        List<RoomID> listOfRooms = new ArrayList<>();
        //Get all rooms of a specific house from the repository
        Iterable<Room> roomsInRepository = roomRepository.findByHouseID(houseID);

        //If rooms are inside the house
        if (areRoomsInside) {
            for (Room room : roomsInRepository) {
                if (room.getRoomDimensions().getHeight() != 0) {
                    listOfRooms.add(room.identity());
                }
            }
        } else {
            //If rooms are outside the house
            for (Room room : roomsInRepository) {
                if (room.getRoomDimensions().getHeight() == 0) {
                    listOfRooms.add(room.identity());
                }
            }
        }
        return listOfRooms;
    }

    /**
     * Retrieves the information of a room based on the provided room ID.
     *
     * @param roomID The unique identifier of the room to retrieve information from.
     * @return InternalRoomDTO object containing the room information
     * @throws RoomNotFoundException if the room with the provided ID does not exist in the repository
     */

    public InternalRoomDTO findRoomIDInfo(RoomID roomID) {
        Optional<Room> room = roomRepository.findEntityByID(roomID);
        if (room.isPresent()) {
            RoomFloor roomFloor = room.get().getRoomFloor();
            RoomDimensions roomDimensions = room.get().getRoomDimensions();
            HouseID houseID = room.get().getHouseID();
            return new InternalRoomDTO(roomID, roomFloor, roomDimensions, houseID);
        }
        else {
            throw new RoomNotFoundException();
        }
    }
}
