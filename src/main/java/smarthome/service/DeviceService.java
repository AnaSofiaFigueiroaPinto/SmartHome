package smarthome.service;

import org.springframework.stereotype.Service;
import smarthome.domain.device.Device;
import smarthome.domain.device.FactoryDevice;
import smarthome.domain.repository.DeviceRepository;
import smarthome.domain.repository.HouseRepository;
import smarthome.domain.repository.RoomRepository;
import smarthome.domain.room.Room;
import smarthome.domain.valueobjects.DeviceID;
import smarthome.domain.valueobjects.DeviceModel;
import smarthome.domain.valueobjects.HouseID;
import smarthome.domain.valueobjects.RoomID;
import smarthome.service.internaldto.InternalDeviceDTO;
import smarthome.util.exceptions.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Service class for managing devices.
 */
@Service
public class DeviceService {

    /**
     * Factory used for creating device objects.
     */
    private final FactoryDevice factoryDevice;

    /**
     * Repository used for storing device objects.
     */
    private final DeviceRepository deviceRepository;

    /**
     * Repository used for storing room objects.
     */
    private final RoomRepository roomRepository;

    /**
     * Repository used for storing house objects.
     */
    private final HouseRepository houseRepository;

    /**
     * Constructor of DeviceService objects with the given factory, device repository and room repository.
     *
     * @param factoryDevice    valid instance of class used for creating devices.
     * @param deviceRepository valid instance of class used for persisting instances of devices.
     * @param roomRepository   valid instance of class used for persisting instances of rooms.
     * @param houseRepository  valid instance of class used for persisting the instance of house.
     */
    public DeviceService(
            FactoryDevice factoryDevice,
            DeviceRepository deviceRepository,
            RoomRepository roomRepository,
            HouseRepository houseRepository
    ) {
        this.factoryDevice = factoryDevice;
        this.deviceRepository = deviceRepository;
        this.roomRepository = roomRepository;
        this.houseRepository = houseRepository;
    }

    /**
     * Method to create a device and save it to the repository.
     *
     * @param roomID      The unique identifier of the room to which the device should be added.
     * @param deviceID    The unique identifier of the device to create and add.
     * @param deviceModel The model of the device to create and add.
     * @return True if the device is created and added successfully, otherwise false.
     */
    public DeviceID createDeviceAndSaveToRepository(DeviceID deviceID, DeviceModel deviceModel, RoomID roomID) {
        //Check if room with that ID exists
        if (!roomRepository.containsEntityByID(roomID))
            throw new RoomNotFoundException();
        //Check if a device with that deviceID (deviceName) already exists
        if (deviceRepository.containsEntityByID(deviceID))
            throw new DeviceAlreadyExistsException();
        //Create device
        Device device = factoryDevice.createDevice(deviceID, deviceModel, roomID);
        if (device == null)
            throw new DeviceNotCreatedException();
        //Save device to repository
        deviceRepository.save(device);
        return device.identity();
    }

    /**
     * Deactivates the device specified by the provided DeviceID.
     * If the deviceID is found in the device repository, it is deactivated, and the repository is updated.
     *
     * @param deviceID The unique identifier of the device to deactivate.
     * @return {@code true} if the device was successfully deactivated and updated in the repository,
     * {@code false} otherwise.
     */
    public boolean deactivateDevice(DeviceID deviceID) {
        //Find device with that ID
        Device device = deviceRepository.findEntityByID(deviceID).orElse(null);
        if (device == null)
            throw new DeviceNotFoundException();
        //Deactivate device
        if (device.deactivate()) {
            deviceRepository.update(device);
            return true;
        }
        return false;
    }

    /**
     * Retrieves a list of DeviceID objects representing the devices located in the specified room.
     * Searches for the room ID corresponding to the provided RoomDTO. If the roomID exists in the room repository, iterates over
     * all devices in the device repository and adds devices with matching room IDs to the list of devices
     *
     * @param roomID The unique identifier of the room to search for devices in.
     * @return A list of DeviceID objects representing the devices located in the specified room or {@code null}
     * if the room does not exist.
     */
    public List<DeviceID> getListOfDevicesInRoom(RoomID roomID) {
        //Check if room with that ID exists
        if (!roomRepository.containsEntityByID(roomID))
            throw new RoomNotFoundException();

        //Get iterable of devices in the specified room
        Iterable<Device> listOfDevicesInRoom = deviceRepository.findByRoomID(roomID);

        //Convert to list of devices ID in the specified room
        List<DeviceID> listOfDevicesIDInRoom = new ArrayList<>();

        for (Device device : listOfDevicesInRoom) {
            listOfDevicesIDInRoom.add(device.identity());
        }
        return listOfDevicesIDInRoom;
    }

    /**
     * Get the device from repository based on its {@code DeviceID}.
     *
     * @param deviceID the ID of the device to be checked.
     * @return a internalDeviceDTO if the device is found.
     * @throws DeviceNotFoundException if the device is not found in the repository.
     */
    public InternalDeviceDTO getDeviceByID(DeviceID deviceID) {
        Device device = deviceRepository.findEntityByID(deviceID).orElse(null);
        if (device == null)
            throw new DeviceNotFoundException();
        return new InternalDeviceDTO(device.identity(), device.getDeviceModel(), device.getDeviceStatus(), device.getRoomID());
    }

    /**
     * Get the list of devices in a house.
     *
     * @param houseID the ID of the house to get the list of devices from.
     * @return a list of devices DTO in the house.
     */
    public List<InternalDeviceDTO> getListOfDevicesInHouse(HouseID houseID) {
        if(!houseRepository.containsEntityByID(houseID))
            throw new HouseNotFoundException();

        List<InternalDeviceDTO> listOfDevicesInHouse = new ArrayList<>();
        // Iterate over all rooms of a house
        for(Room room : roomRepository.findByHouseID(houseID)) {
            // For each room, get all devices and add them to the list
            for(Device device : deviceRepository.findByRoomID(room.identity())) {
                InternalDeviceDTO internalDeviceDTO = new InternalDeviceDTO(device.identity(), device.getDeviceModel(),
                        device.getDeviceStatus(), device.getRoomID());
                listOfDevicesInHouse.add(internalDeviceDTO);
            }
        }
        return listOfDevicesInHouse;
    }
}