package smarthome.service.internaldto;

import smarthome.domain.valueobjects.DeviceID;
import smarthome.domain.valueobjects.DeviceModel;
import smarthome.domain.valueobjects.DeviceStatus;
import smarthome.domain.valueobjects.RoomID;

/**
 * InternalDeviceDTO is a Data Transfer Object (DTO) used for internal communication
 * within the application. It encapsulates the information related to a device.
 */
public class InternalDeviceDTO {
    /**
     * The unique identifier of the device.
     */
    public DeviceID deviceID;
    /**
     * The model of the device.
     */
    public DeviceModel deviceModel;
    /**
     * The status of the device.
     */
    public DeviceStatus deviceStatus;
    /**
     * The unique identifier of the room associated with the device.
     */
    public RoomID roomID;

    /**
     * Constructs a new InternalDeviceDTO.
     *
     * @param deviceID     the ID of the device
     * @param deviceModel  the model of the device
     * @param deviceStatus the status of the device
     * @param roomID       the ID of the room that contains the device
     */
    public InternalDeviceDTO(DeviceID deviceID, DeviceModel deviceModel, DeviceStatus deviceStatus, RoomID roomID) {
        this.deviceID = deviceID;
        this.deviceModel = deviceModel;
        this.deviceStatus = deviceStatus;
        this.roomID = roomID;
    }
}
