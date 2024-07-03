package smarthome.domain.device;

import smarthome.domain.valueobjects.DeviceID;
import smarthome.domain.valueobjects.DeviceModel;
import smarthome.domain.valueobjects.DeviceStatus;
import smarthome.domain.valueobjects.RoomID;

/**
 * Interface to represent a factory to create a new device.
 */
public interface FactoryDevice {

    /**
     * Method to create a new device with the given name, model and roomID.
     * @param deviceID The unique identifier of the device.
     * @param deviceModel The model of the device.
     * @param roomID The roomID of the room where the device is located.
     * @return A new device object.
     */
  Device createDevice(DeviceID deviceID, DeviceModel deviceModel, RoomID roomID);

    /**
     * Method to create a new device with the given name, model, roomID and status.
     * @param deviceID The unique identifier of the device.
     * @param deviceModel The model of the device.
     * @param roomID The roomID of the room where the device is located.
     * @param deviceStatus The status of the device.
     * @return A new device object.
     */
  Device createDevice(DeviceID deviceID, DeviceModel deviceModel, RoomID roomID, DeviceStatus deviceStatus);

}