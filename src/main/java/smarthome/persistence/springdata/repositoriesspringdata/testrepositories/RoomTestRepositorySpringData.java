package smarthome.persistence.springdata.repositoriesspringdata.testrepositories;

import smarthome.domain.repository.RoomRepository;
import smarthome.domain.room.ImpFactoryRoom;
import smarthome.domain.room.Room;
import smarthome.domain.valueobjects.HouseID;
import smarthome.domain.valueobjects.RoomDimensions;
import smarthome.domain.valueobjects.RoomFloor;
import smarthome.domain.valueobjects.RoomID;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * A test implementation of the RoomRepository interface for Spring Data testing purposes.
 * This class provides a set of pre-defined Room objects for testing various repository methods.
 * Note: This repository does not interact with an actual database.
 */
public class RoomTestRepositorySpringData implements RoomRepository {

    /**
     * Simulated list of room entities for testing purposes.
     */

    private final List<Room> rooms;

    private Room roomReserved;

    /**
     * Constructs a new RoomTestRepositorySpringData with the specified FactoryRoom.
     */
    public RoomTestRepositorySpringData() {
        this.rooms = new ArrayList<>();
        ImpFactoryRoom factoryRoom = new ImpFactoryRoom();
        populateRooms(factoryRoom);
    }

    private void populateRooms(ImpFactoryRoom factoryRoom) {
        rooms.add(factoryRoom.createRoom(new RoomID("Room001"), new RoomFloor(1), new RoomDimensions(2.5, 3.0, 4.0), new HouseID("House001")));
        rooms.add(factoryRoom.createRoom(new RoomID("Room002"), new RoomFloor(2), new RoomDimensions(2.7, 3.5, 4.5), new HouseID("House001")));
        rooms.add(factoryRoom.createRoom(new RoomID("Room003"), new RoomFloor(0), new RoomDimensions(2.4, 2.8, 3.8), new HouseID("House001")));
        rooms.add(factoryRoom.createRoom(new RoomID("Room004"), new RoomFloor(0), new RoomDimensions(5.0, 4.0, 0.0), new HouseID("House001")));
        rooms.add(factoryRoom.createRoom(new RoomID("Room005"), new RoomFloor(-1), new RoomDimensions(2.2, 3.2, 4.2), new HouseID("House001")));
    }

    /**
     * Saves a Room entity in the repository.
     * @param room The Room entity to save.
     * @return The saved Room entity.
     */

    @Override
    public Room save(Room room) {
        rooms.add(room);
        return room;
    }

    /**
     * Updates a Room entity.
     * @param roomEdited The updated Room entity if successful, null otherwise.
     */
    @Override
    public Room update(Room roomEdited) {
        for (Room room : rooms) {
            if (room.identity().equals(roomEdited.identity())) {
                rooms.remove(room);
                rooms.add(roomEdited);
                return roomEdited;
            }
        }
        return null;
    }

    /**
     * Updates a reserved Room entity.
     * @param roomEdited The Room entity to be updated.
     * @return The updated Room entity if successful, null otherwise.
     */
    @Override
    public Room updateReserved(Room roomEdited) {
        if (roomReserved != null && roomReserved.identity().equals(roomEdited.identity())) {
            rooms.remove(roomReserved);
            rooms.add(roomEdited);
            return roomEdited;
        }
        return null;
    }

    /**
     * Finds a Room instance by ID and reserves it.
     *
     * @param id The ID of the room to be found and reserved.
     * @return An Optional containing the found and reserved room, or empty if not found.
     */

    @Override
    public Optional<Room> findEntityByIDAndReserve(RoomID id) {
        Optional<Room> optionalRoom = findEntityByID(id);
        if (optionalRoom.isPresent()) {
            this.roomReserved = optionalRoom.get();
            return Optional.of(this.roomReserved);
        }
        return Optional.empty();
    }

    /**
     * Finds all rooms associated with a HouseID.
     *
     * @param houseID The ID of the house used to find rooms.
     * @return An Iterable containing the found rooms.
     */

    @Override
    public Iterable<Room> findByHouseID(HouseID houseID) {
        List<Room> roomsByHouse = new ArrayList<>();
        for (Room room : rooms) {
            if (room.getHouseID().equals(houseID)) {
                roomsByHouse.add(room);
            }
        }
        return roomsByHouse;
    }

    /**
     * Finds a Room instance by ID.
     *
     * @param id The ID of the room to be found.
     * @return An Optional containing the found room, or empty if not found.
     */

    @Override
    public Optional<Room> findEntityByID(RoomID id) {
        for (Room room : rooms) {
            if (room.identity().equals(id)) {
                return Optional.of(room);
            }
        }
        return Optional.empty();
    }

    /**
     * Finds all Room instances in the repository.
     *
     * @return An Iterable containing all rooms.
     */
    @Override
    public Iterable<Room> findAllEntities() {
        return rooms;
    }

    /**
     * Checks if a Room instance with a given ID exists in the repository.
     *
     * @param id The ID of the room to be checked.
     * @return true if the room exists, false otherwise.
     */

    @Override
    public boolean containsEntityByID(RoomID id) {
        for (Room room : rooms) {
            if (room.identity().equals(id)) {
                return true;
            }
        }
        return false;
    }
}
