package smarthome.persistence.springdata.repositoriesspringdata.testrepositories;

import smarthome.domain.device.Device;
import smarthome.domain.device.FactoryDevice;
import smarthome.domain.device.ImpFactoryDevice;
import smarthome.domain.repository.DeviceRepository;
import smarthome.domain.valueobjects.DeviceID;
import smarthome.domain.valueobjects.DeviceModel;
import smarthome.domain.valueobjects.DeviceStatus;
import smarthome.domain.valueobjects.RoomID;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * A test implementation of the DeviceRepository interface for Spring Data testing purposes.
 * This class provides a set of pre-defined Device objects for testing various repository methods.
 * Note: This repository does not interact with an actual database.
 */
public class DeviceTestRepositorySpringData implements DeviceRepository {

    /**
     * Simulated list of device entities for testing purposes.
     */
    private final List<Device> devices;

    private Device deviceReserved;

    /**
     * Constructs a new DeviceTestRepositorySpringData with the specified FactoryDevice.
     */
    public DeviceTestRepositorySpringData() {
        // Initialize Device objects, simulating data for testing purposes.
        // Note: This class is designed for testing purposes and does not interact with an actual database.
        this.devices = new ArrayList<>();
        FactoryDevice factoryDevice = new ImpFactoryDevice();
        populateListWithDevices(factoryDevice);
    }

    private void populateListWithDevices(FactoryDevice factoryDevice) {
        devices.add(factoryDevice.createDevice(new DeviceID("Device001"), new DeviceModel("T8115"), new RoomID("Room001"), DeviceStatus.ACTIVE));
        devices.add(factoryDevice.createDevice(new DeviceID("Device002"), new DeviceModel("SL8115"), new RoomID("Room001"), DeviceStatus.ACTIVE));
        devices.add(factoryDevice.createDevice(new DeviceID("Device003"), new DeviceModel("B8115"), new RoomID("Room002"), DeviceStatus.ACTIVE));
        devices.add(factoryDevice.createDevice(new DeviceID("Device004"), new DeviceModel("B8115"), new RoomID("Room002"), DeviceStatus.ACTIVE));
        devices.add(factoryDevice.createDevice(new DeviceID("Device005"), new DeviceModel("A8115"), new RoomID("Room003"), DeviceStatus.ACTIVE));
        devices.add(factoryDevice.createDevice(new DeviceID("Device006"), new DeviceModel("S8115"), new RoomID("Room003"), DeviceStatus.ACTIVE));
        devices.add(factoryDevice.createDevice(new DeviceID("Device007"), new DeviceModel("T8115"), new RoomID("Room004"), DeviceStatus.ACTIVE));
        devices.add(factoryDevice.createDevice(new DeviceID("Device008"), new DeviceModel("SL8115"), new RoomID("Room004"), DeviceStatus.ACTIVE));
        devices.add(factoryDevice.createDevice(new DeviceID("Device009"), new DeviceModel("S8115"), new RoomID("Room005"), DeviceStatus.ACTIVE));
        devices.add(factoryDevice.createDevice(new DeviceID("Device010"), new DeviceModel("A8115"), new RoomID("Room005"), DeviceStatus.ACTIVE));
        devices.add(factoryDevice.createDevice(new DeviceID("Grid Power Meter"), new DeviceModel("G8115"), new RoomID("Room002"), DeviceStatus.ACTIVE)); //Grid Power Meter
        devices.add(factoryDevice.createDevice(new DeviceID("Power Source 1"), new DeviceModel("P8115"), new RoomID("Room005"), DeviceStatus.ACTIVE)); //Power Source 1
        devices.add(factoryDevice.createDevice(new DeviceID("Power Source 2"), new DeviceModel("P8115"), new RoomID("Room004"), DeviceStatus.ACTIVE)); //Power Source 2
    }

    /**
     * Saves a Device entity in the repository.
     * @param device The Device entity to save.
     * @return The saved Device entity.
     */
    @Override
    public Device save(Device device) {
        devices.add(device);
        return device;
    }

    /**
     * Updates a Device entity.
     * @param device The updated Device entity if successful, null otherwise.
     */
    @Override
    public Device update(Device device) {
        Optional<Device> optionalDevice = findEntityByID(device.identity());
        if (optionalDevice.isPresent()) {
            devices.remove(optionalDevice.get());
            devices.add(device);
            return device;
        }
        return null;
    }

    /**
     * Updates a reserved a Device entity.
     * @param device The Device entity to be updated.
     * @return The updated Device entity if successful, null otherwise.
     */
    @Override
    public Device updateReserved(Device device) {
        if (deviceReserved != null && deviceReserved.identity().equals(device.identity())) {
            devices.remove(deviceReserved);
            devices.add(device);
            return device;
        }
        return null;
    }

    /**
     * Finds all devices in the repository that belong to the specified room.
     * @param roomID The ID of the room to search for.
     * @return An Iterable collection of devices that belong to the specified room.
     */
    @Override
    public Iterable<Device> findByRoomID(RoomID roomID) {
        return devices.stream()
                .filter(device -> (device.getRoomID()).equals(roomID))
                .toList();
    }

    /**
     * Retrieves a Device entity by its identity.
     * @param deviceID The identity of the Device entity.
     * @return An Optional containing the Device entity if found, otherwise an empty Optional.
     */
    @Override
    public Optional<Device> findEntityByID(DeviceID deviceID) {
        return devices.stream()
                .filter(device -> (device.identity()).equals(deviceID))
                .findFirst();
    }

    /**
     * Retrieves a Device entity by its identity and reserves it for further operations.
     * @param deviceID The Device entity identity to search for.
     */
    @Override
    public Optional<Device> findEntityByIDAndReserve(DeviceID deviceID) {
        Optional<Device> optionalDevice = findEntityByID(deviceID);
        if (optionalDevice.isPresent()) {
            this.deviceReserved = optionalDevice.get();
            return Optional.of(this.deviceReserved);
        }
        return Optional.empty();
    }

    /**
     * Retrieves all Device entities from the repository.
     * @return An Iterable containing all Device entities.
     */
    @Override
    public Iterable<Device> findAllEntities() {
        return devices;
    }

    /**
     * Checks if a Device entity exists based on its identity.
     * @param deviceID The identity of the Device entity.
     * @return true if the Device entity exists, otherwise false.
     */
    @Override
    public boolean containsEntityByID(DeviceID deviceID) {
        return devices.stream()
                .anyMatch(device -> (device.identity()).equals(deviceID));
    }
}
