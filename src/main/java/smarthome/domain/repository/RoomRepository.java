package smarthome.domain.repository;

import org.springframework.stereotype.Component;
import smarthome.ddd.Repository;
import smarthome.domain.room.Room;
import smarthome.domain.valueobjects.HouseID;
import smarthome.domain.valueobjects.RoomID;

import java.util.Optional;

@Component

/**
 * Interface to represent a repository for rooms.
 */
public interface RoomRepository extends Repository<RoomID, Room> {

    /**
     * Saves a room object to the repository.
     * @param room the room object to save
     * @return the saved room object
     */
    Room save(Room room);

    /**
     * Method that updated a Room object
     * @param room the room object to update
     * @return the updated Room object
     */
    Room update(Room room);

    /**
     * Method that updates the last edited Room object that was in cache to prevent another request to the database
     * @param room the room object to update
     * @return the updated Room object
     */
    Room updateReserved(Room room);

    /**
     * Method that returns the Room of the last consulted Room Object.
     * @param roomID The unique identifier of the room to check.
     * @return Room object.
     */
    Optional<Room> findEntityByIDAndReserve(RoomID roomID);

    /**
     * Retrieves a list of Rooms entities by their house ID.
     *
     * @param houseID The house ID to search for.
     * @return An Iterable containing all rooms with the given house ID.
     */

    Iterable<Room> findByHouseID(HouseID houseID);
}
