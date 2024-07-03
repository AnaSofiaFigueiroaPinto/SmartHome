package smarthome.controllercli;

import smarthome.domain.valueobjects.DeviceID;
import smarthome.domain.valueobjects.DeviceModel;
import smarthome.domain.valueobjects.RoomID;
import smarthome.mapper.DeviceDTO;
import smarthome.mapper.RoomDTO;
import smarthome.service.DeviceService;

/**
 * Controller class responsible for handling the addition of a new device to a room.
 */
public class AddNewDeviceToRoomController {
    /**
     * DeviceService instance to interact with device-related operations.
     */
    private final DeviceService deviceService;

    /**
     * Constructs a new AddNewDeviceToRoomController with the specified DeviceService.
     * @param deviceService The DeviceService instance to interact with for device-related operations.
     */
    public AddNewDeviceToRoomController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    /**
     * Method to create a new device with the given name and adds it to the specified room.
     * @param roomDTO    The DTO representing the room to which the device should be added.
     * @param deviceName The name of the device to create and add.
     * @param model The model of the device to create and add.
     * @return {@code DeviceDTO} representing the created device if successful, {@code null} otherwise.
     */

    public DeviceDTO createDeviceAndSaveToRepository(RoomDTO roomDTO, String deviceName, String model) {
        try {
            DeviceID deviceID = new DeviceID(deviceName);
            DeviceModel deviceModel = new DeviceModel(model);
            RoomID roomID = new RoomID(roomDTO.roomName);

            //Create Device. If creation fails, an exception will then be thrown.
            DeviceID deviceCreatedID = deviceService.createDeviceAndSaveToRepository(deviceID, deviceModel, roomID);

            //Return DeviceDTO of the created device.
            return new DeviceDTO(deviceCreatedID.toString(), model, "ACTIVE", roomDTO.roomName);
        } catch (RuntimeException e) {
            return null;
        }
    }

}
