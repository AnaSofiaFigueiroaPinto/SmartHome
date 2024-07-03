package smarthome.persistence.repositoriesmem;

import smarthome.domain.device.Device;
import smarthome.domain.repository.DeviceRepository;
import smarthome.domain.valueobjects.DeviceID;
import smarthome.domain.valueobjects.RoomID;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Repository class for managing devices.
 */
public class DeviceRepositoryMem implements DeviceRepository {

    /**
     * Map of DeviceID and Device objects.
     */
    private final Map<DeviceID, Device> deviceData;

    /**
     * The device entity used for updating a reserved device.
     */
    private Device deviceForUpdate;

    /**
     * Constructor for DeviceRepository object.
     * Currently stands in this way as there doesn't exist a database yet.
     *
     * @param deviceData Map of DeviceID and Device objects.
     */
    public DeviceRepositoryMem(Map<DeviceID, Device> deviceData) {
        this.deviceData = deviceData;
    }

    /**
     * Saves the given entity in the repository.
     *
     * @param entity The entity to save.
     * @return The saved entity.
     */
    @Override
    public Device save(Device entity) {
        deviceData.put(entity.identity(), entity);
        return entity;
    }

    /**
     * Updates the provided device entity in the repository.
     *
     * @param device The device entity to be updated.
     * @return The updated device entity if it exists in the repository, otherwise returns null.
     */
    @Override
    public Device update(Device device) {
        if (deviceData.containsKey(device.identity())) {
            deviceData.put(device.identity(), device);
            return device;
        }
        return null;
    }

    /**
     * Updates a reserved device with new information.
     *
     * @param device The device containing updated information.
     * @return The updated device entity if the update is successful,
     * null if the device is not reserved or null if the identity doesn't match.
     */
    @Override
    public Device updateReserved(Device device) {
        // Check if the repository contains the reserved device
        if (deviceForUpdate != null) {
            // Check if they have the same identification
            if (deviceForUpdate.identity() == device.identity()) {
                // Update the reserved device entity with the new information
                deviceData.put(deviceForUpdate.identity(), device);
                // Return the updated device
                return device;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    /**
     * Retrieves all entities from the repository.
     *
     * @return An Iterable containing all entities in the repository.
     */
    @Override
    public Iterable<Device> findAllEntities() {
        return deviceData.values();
    }

    /**
     * Finds an entity in the repository by its unique identifier.
     *
     * @param id The unique identifier of the entity to find.
     * @return An Optional containing the found entity, or empty if not found.
     */
    @Override
    public Optional<Device> findEntityByID(DeviceID id) {
        return Optional.ofNullable(deviceData.get(id));
    }

    /**
     * Checks whether an entity with the given identifier exists in the repository.
     *
     * @param id The unique identifier of the entity to check.
     * @return true if the entity exists in the repository, false otherwise.
     */
    @Override
    public boolean containsEntityByID(DeviceID id) {
        return deviceData.containsKey(id);
    }

    /**
     * Finds a device entity by its ID and reserves it for further processing.
     *
     * @param deviceID The ID of the device entity to find and reserve.
     * @return An Optional containing the reserved device entity if found, or empty if not found.
     */
    @Override
    public Optional<Device> findEntityByIDAndReserve(DeviceID deviceID) {
        if (!containsEntityByID(deviceID)) {
            return Optional.empty();
        }
        this.deviceForUpdate = deviceData.get(deviceID);
        return Optional.of(deviceForUpdate);
    }

    /**
     * Finds all Device entities in a specified roomID.
     *
     * @param roomID The room ID to search for.
     * @return An Iterable containing all Device entities.
     */
    @Override
    public Iterable<Device> findByRoomID(RoomID roomID) {
        List<Device> deviceWithRoomID = new ArrayList<>();
        for (Device device : deviceData.values()) {
            if (device.getRoomID().equals(roomID)) {
                deviceWithRoomID.add(device);
            }
        }
        return deviceWithRoomID;
    }
}

