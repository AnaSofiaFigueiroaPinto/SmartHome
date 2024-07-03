package smarthome.persistence.repositoriesmem;

import smarthome.domain.repository.RoomRepository;
import smarthome.domain.room.Room;
import smarthome.domain.valueobjects.HouseID;
import smarthome.domain.valueobjects.RoomID;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * An implementation of a repository for managing Room entities.
 */
public class RoomRepositoryMem implements RoomRepository {
    /**
     * Map of RoomID and Room objects.
     */
    private Map<RoomID, Room> roomData;

    /**
     * The room entity used for updating a reserved device.
     */
    private Room roomForUpdate;

    /**
     * Constructor for RoomRepository object.
     *
     * @param roomData Map of RoomID and Room objects.
     */
    public RoomRepositoryMem(Map<RoomID, Room> roomData) {
        this.roomData = roomData;
    }

    /**
     * Saves the given entity in the repository.
     *
     * @param entity The entity to save.
     * @return The saved entity. Or null if the given entity is null.
     */
    public Room save(Room entity) {
        if (entity == null) {
            return null;
        }
        roomData.put(entity.identity(), entity);
        return entity;
    }


    /**
     * Updates the provided room entity in the repository.
     *
     * @param room The room entity to be updated.
     * @return The updated room entity if it exists in the repository, otherwise returns null.
     */
    @Override
    public Room update(Room room) {
        if (roomData.containsKey(room.identity())) {
            roomData.put(room.identity(), room);
            return room;
        }
        return null;
    }

    /**
     * Updates a reserved room with new information.
     *
     * @param room The room containing updated information.
     * @return The updated room entity if the update is successful,
     * null if the room is not reserved or null if the identity doesn't match.
     */
    @Override
    public Room updateReserved(Room room) {
        // Check if the repository contains the reserved room
        if (roomForUpdate != null) {
            // Check if they have the same identification
            if (roomForUpdate.identity() == room.identity()) {
                // Update the reserved room entity with the new information
                roomData.put(roomForUpdate.identity(), room);
                // Return the updated room
                return room;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    /**
     * @return all Room entities in RoomRepository
     */
    public Iterable<Room> findAllEntities() {
        return roomData.values();
    }

    /**
     * @param id The unique identifier of the entity to find.
     * @return An Optional containing the found entity, or empty if not found.
     */

    public Optional<Room> findEntityByID(RoomID id) {
        if (!containsEntityByID(id))
            return Optional.empty();
        else return Optional.of(roomData.get(id));
    }

    /**
     * @param id The unique identifier of the entity to check.
     * @return true if the entity exists in the repository, false otherwise.
     */
    public boolean containsEntityByID(RoomID id) {
        return roomData.containsKey(id);
    }

    /**
     * Finds a Room entity by its ID and reserves it for further processing.
     *
     * @param roomID The ID of the Room entity to find and reserve.
     * @return An Optional containing the reserved device entity if found, or empty if not found.
     */
    @Override
    public Optional<Room> findEntityByIDAndReserve(RoomID roomID) {
        if (!containsEntityByID(roomID)) {
            return Optional.empty();
        }
        this.roomForUpdate = roomData.get(roomID);
        return Optional.of(roomForUpdate);
    }

    /**
     * Finds all Room entities in a specified house.
     *
     * @param houseID The house ID to search for.
     * @return An Iterable containing all Room entities of the specified house.
     */
    @Override
    public Iterable<Room> findByHouseID(HouseID houseID) {
        List<Room> houseRooms = new ArrayList<>();
        for (Room room : roomData.values()) {
            if (room.getHouseID().equals(houseID)) {
                houseRooms.add(room);
            }
        }
        return houseRooms;
    }
}
