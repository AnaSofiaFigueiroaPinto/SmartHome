package smarthome.domain.repository;

import smarthome.ddd.Repository;
import smarthome.domain.device.Device;
import smarthome.domain.valueobjects.DeviceID;
import smarthome.domain.valueobjects.RoomID;

import java.util.Optional;

/**
 * Represents an interface {@code DeviceRepository} for persistence in JPA.
 */
public interface DeviceRepository extends Repository<DeviceID, Device> {
    /**
     * Saves the given entity in the repository.
     *
     * @param device The entity to save or update.
     * @return The saved or updated entity.
     */
    Device save(Device device);

    /**
     * Updates the given entity in the repository.
     *
     * @param device The entity to update.
     * @return The updated entity.
     */
    Device update(Device device);

    /**
     * Updates the given entity in the repository.
     *
     * @param device The entity to update.
     * @return The updated entity.
     */
    Device updateReserved(Device device);

    /**
     * Retrieves a reserved Device entity by its ID from the repository.
     *
     * @param deviceID The unique identifier of the entity to find.
     * @return An Optional containing the found entity, or empty if not found.
     */
    Optional<Device> findEntityByIDAndReserve(DeviceID deviceID);

    /**
     * Retrieves a list of Devices entities by their room ID.
     *
     * @param roomID The room ID to search for.
     * @return An Iterable containing all device with the given room ID.
     */
    Iterable<Device> findByRoomID(RoomID roomID);
}
