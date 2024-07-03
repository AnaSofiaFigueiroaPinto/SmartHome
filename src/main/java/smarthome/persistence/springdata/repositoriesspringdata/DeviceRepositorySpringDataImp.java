package smarthome.persistence.springdata.repositoriesspringdata;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import smarthome.domain.device.Device;
import smarthome.domain.device.FactoryDevice;
import smarthome.domain.repository.DeviceRepository;
import smarthome.domain.valueobjects.DeviceID;
import smarthome.domain.valueobjects.RoomID;
import smarthome.persistence.jpa.datamodel.DeviceDataModel;
import smarthome.persistence.jpa.datamodel.MapperDeviceDataModel;

import java.util.List;
import java.util.Optional;

/**
 * The DeviceRepositorySpringData class represents a repository for managing Device entities using Spring Data.
 * It provides methods for saving, updating, finding, and checking the existence of Device entities.
 */
@Repository
@Profile("!test")
public class DeviceRepositorySpringDataImp implements DeviceRepository {

    /**
     * The repository interface for managing DeviceDataModel entities using Spring Data.
     */
    DeviceRepositorySpringData deviceRepositorySpringData;

    /**
     * The factory responsible for creating Device instances.
     */
    FactoryDevice factoryDevice;

    /**
     * The Device Data Model used for updating the Device.
     */
    DeviceDataModel deviceDataModelUpdated;

    /**
     * Mapper Device Data Model instance for managing persistence operations.
     */
    MapperDeviceDataModel mapperDeviceDataModel;

    /**
     * Constructs a DeviceRepositorySpringData object with the specified FactoryDevice
     * and IRepositoryDeviceSpringData instances.
     *
     * @param factoryDevice              The factory for creating Device entities.
     * @param deviceRepositorySpringData The Spring Data repository for Device entities.
     */

    public DeviceRepositorySpringDataImp(FactoryDevice factoryDevice, DeviceRepositorySpringData deviceRepositorySpringData,
                                         MapperDeviceDataModel mapperDeviceDataModel) {
        this.factoryDevice = factoryDevice;
        this.deviceRepositorySpringData = deviceRepositorySpringData;
        this.mapperDeviceDataModel = mapperDeviceDataModel;
    }

    /**
     * Saves a Device entity.
     *
     * @param device The Device entity to be saved.
     * @return The saved Device entity.
     * @throws IllegalArgumentException if the provided device is null.
     */
    @Override
    public Device save(Device device) {
        DeviceDataModel deviceDataModel = new DeviceDataModel(device);
        this.deviceRepositorySpringData.save(deviceDataModel);
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
        Optional<DeviceDataModel> optDeviceDataModelExisting = deviceRepositorySpringData.findById(device.identity().toString());
        if (optDeviceDataModelExisting.isPresent()) {
            var deviceDataModelExisting = optDeviceDataModelExisting.get();
            boolean isUpdated = deviceDataModelExisting.updatedFromDomain(device);

            if (isUpdated) {
                this.deviceRepositorySpringData.save(deviceDataModelExisting);
                return device;
            } else
                return null;
        } else
            return null;
    }

    /**
     * Updates a reserved a Device entity.
     *
     * @param device The Device entity to be updated.
     * @return The updated Device entity if successful, null otherwise.
     */
    @Override
    public Device updateReserved(Device device) {
        if (deviceDataModelUpdated != null) {
            boolean isUpdated = deviceDataModelUpdated.updatedFromDomain(device);
            if (isUpdated) {
                this.deviceRepositorySpringData.save(deviceDataModelUpdated);
                return device;
            } else
                return null;
        } else
            return null;
    }

    /**
     * Finds all Device entities.
     *
     * @return An Iterable containing all Device entities.
     */
    @Override
    public Iterable<Device> findAllEntities() {
        List<DeviceDataModel> deviceDataModelList = this.deviceRepositorySpringData.findAll();
        return mapperDeviceDataModel.toDomainList(factoryDevice, deviceDataModelList);
    }

    /**
     * Retrieves a Device entity by its identity.
     *
     * @param deviceID The identity of the Device entity.
     * @return An Optional containing the Device entity if found, otherwise an empty Optional.
     */
    @Override
    public Optional<Device> findEntityByID(DeviceID deviceID) {
        Optional<DeviceDataModel> optionalDeviceDataModel = this.deviceRepositorySpringData.findById(deviceID.toString());

        if (optionalDeviceDataModel.isPresent()) {
            Device deviceDomain = mapperDeviceDataModel.toDomain(factoryDevice, optionalDeviceDataModel.get());
            return Optional.of(deviceDomain);

        } else
            return Optional.empty();
    }

    /**
     * Retrieves a reserved Device entity by its ID from the repository.
     *
     * @param deviceID The identity of the Device entity.
     * @return An Optional containing the Device entity if found, otherwise an empty Optional.
     */
    @Override
    public Optional<Device> findEntityByIDAndReserve(DeviceID deviceID) {
        Optional<DeviceDataModel> optionalDeviceDataModel = this.deviceRepositorySpringData.findById(deviceID.toString());

        if (optionalDeviceDataModel.isPresent()) {
            Device deviceDomain = mapperDeviceDataModel.toDomain(factoryDevice, optionalDeviceDataModel.get());
            this.deviceDataModelUpdated = optionalDeviceDataModel.get();
            return Optional.of(deviceDomain);
        } else
            return Optional.empty();
    }

    /**
     * Checks if a Device entity exists based on its identity.
     *
     * @param deviceID The identity of the Device entity.
     * @return true if the Device entity exists, otherwise false.
     */
    @Override
    public boolean containsEntityByID(DeviceID deviceID) {
        return deviceRepositorySpringData.existsById(deviceID.toString());
    }

    /**
     * Finds all Device entities in a specified room ID.
     *
     * @param roomID The room ID to search for.
     * @return An Iterable containing all Device entities.
     */
    @Override
    public Iterable<Device> findByRoomID(RoomID roomID) {
        Iterable<DeviceDataModel> deviceDataModelList = deviceRepositorySpringData.findAllByRoomID(roomID.toString());
        return mapperDeviceDataModel.toDomainList(factoryDevice, deviceDataModelList);
    }
}