package smarthome.persistence.jpa.repositoriesjpa;

import jakarta.persistence.*;
import smarthome.domain.device.Device;
import smarthome.domain.device.FactoryDevice;
import smarthome.domain.repository.DeviceRepository;
import smarthome.domain.valueobjects.DeviceID;
import smarthome.domain.valueobjects.RoomID;
import smarthome.persistence.jpa.datamodel.DeviceDataModel;
import smarthome.persistence.jpa.datamodel.MapperDeviceDataModel;
import smarthome.util.EntityUpdater;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Represents a {@code DeviceRepositoryJPAImp} for persistence in JPA.
 */
public class DeviceRepositoryJPAImp implements DeviceRepository {
    /**
     * Instantiating attributes that will be used in the constructors.
     */
    FactoryDevice factoryDevice;

    /**
     * The Device Data Model used for updating the Device.
     */
    DeviceDataModel deviceDataModelForUpdate;

    /**
     * Entity Manager instance for managing persistence operations.
     */
    EntityManager entityManager;

    /**
     * Constructs a new DeviceRepositoryJPAImp with the specified factory for creating device objects.
     *
     * @param factoryDevice the factory for creating device objects.
     */
    public DeviceRepositoryJPAImp(FactoryDevice factoryDevice, EntityManager entityManager) {
        this.factoryDevice = factoryDevice;
        this.entityManager = entityManager;
    }

    /**
     * Saves a device object to the database.
     *
     * @param device the device object to save or update
     * @return the saved device object
     * @throws IllegalArgumentException if the device object is null
     */
    @Override
    public Device save(Device device) {
        DeviceDataModel deviceDataModel = new DeviceDataModel(device);

        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.persist(deviceDataModel);
        transaction.commit();
        entityManager.close();

        return device;
    }

    /**
     * Updates a Device entity.
     *
     * @param device The Device entity to be updated.
     * @return The updated Device entity if successful, null otherwise.
     */
    @Override
    public Device update(Device device) {
        DeviceDataModel deviceDataModel = entityManager.find(DeviceDataModel.class, device.getDeviceName());
        return deviceUpdater(device, deviceDataModel);

    }

    private Device deviceUpdater(Device device, DeviceDataModel deviceDataModel) {
        if (deviceDataModel != null) {
            boolean isUpdated = deviceDataModel.updatedFromDomain(device);

            if (isUpdated) {
                // Utilize EntityUpdater for persistence
                EntityUpdater entityUpdater = new EntityUpdater();
                if (entityUpdater.persistEntity(deviceDataModel, entityManager)) {
                    return device;
                } else {
                    // Handle persistence failure if needed
                    return null;
                }
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    /**
     * Updates a reserved a Device entity.
     *
     * @param device The Device entity to be updated.
     * @return The updated Device entity if successful, null otherwise.
     */
    @Override
    public Device updateReserved(Device device) {
        return deviceUpdater(device, deviceDataModelForUpdate);
    }

    /**
     * Finds all device objects stored in the database.
     *
     * @return an Iterable containing all device objects stored in the database
     */
    @Override
    public Iterable<Device> findAllEntities() {
        Query query = entityManager.createQuery(
                "SELECT e FROM DeviceDataModel e"
        );
        List<DeviceDataModel> listDataModel = query.getResultList();
        MapperDeviceDataModel mapperDeviceDataModel = new MapperDeviceDataModel();
        return mapperDeviceDataModel.toDomainList(factoryDevice, listDataModel);
    }

    /**
     * Finds a device object in the database by its identity.
     *
     * @param deviceID the identity of the device object to find
     * @return an Optional containing the device object if found, or empty otherwise
     */
    @Override
    public Optional<Device> findEntityByID(DeviceID deviceID) {
        DeviceDataModel deviceDataModel = entityManager.find(DeviceDataModel.class, deviceID.toString());
        if (deviceDataModel == null)
            return Optional.empty();
        MapperDeviceDataModel mapperDeviceDataModel = new MapperDeviceDataModel();
        Device deviceDomain = mapperDeviceDataModel.toDomain(factoryDevice, deviceDataModel);
        return Optional.of(deviceDomain);
    }

    /**
     * Retrieves a reserved Device entity by its ID from the repository.
     *
     * @param deviceID The identity of the Device entity.
     * @return An Optional containing the Device entity if found, otherwise an empty Optional.
     */
    @Override
    public Optional<Device> findEntityByIDAndReserve(DeviceID deviceID) {
        DeviceDataModel deviceDataModel = entityManager.find(DeviceDataModel.class, deviceID.toString());
        if (deviceDataModel == null)
            return Optional.empty();
        MapperDeviceDataModel mapperDeviceDataModel = new MapperDeviceDataModel();
        Device deviceDomain = mapperDeviceDataModel.toDomain(factoryDevice, deviceDataModel);
        deviceDataModelForUpdate = deviceDataModel;
        return Optional.of(deviceDomain);
    }

    /**
     * Checks if a device object with the specified identity exists in the database.
     *
     * @param deviceID the identity of the device object to check
     * @return true if a device object with the specified identity exists, false otherwise
     */
    @Override
    public boolean containsEntityByID(DeviceID deviceID) {
        Optional<Device> optionalDevice = findEntityByID(deviceID);
        return optionalDevice.isPresent();
    }

    /**
     * Finds all Device entities in a specified roomID.
     *
     * @param roomID The room ID to search for.
     * @return An Iterable containing all Device entities.
     */
    @Override
    public Iterable<Device> findByRoomID(RoomID roomID) {
        Query query = Objects.requireNonNull(entityManager).createQuery("SELECT e FROM DeviceDataModel e WHERE e.roomID = :roomID");
        query.setParameter("roomID", roomID);
        List<DeviceDataModel> deviceDataModelList = query.getResultList();

        MapperDeviceDataModel mapperDeviceDataModel = new MapperDeviceDataModel();
        return mapperDeviceDataModel.toDomainList(factoryDevice, deviceDataModelList);
    }
}
