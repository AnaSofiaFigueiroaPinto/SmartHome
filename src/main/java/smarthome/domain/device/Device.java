package smarthome.domain.device;

import smarthome.ddd.AggregateRoot;
import smarthome.domain.valueobjects.DeviceID;
import smarthome.domain.valueobjects.DeviceModel;
import smarthome.domain.valueobjects.DeviceStatus;
import smarthome.domain.valueobjects.RoomID;

/**
 * Class to represent a device in the SmartHome system.
 */
public class Device implements AggregateRoot<DeviceID> {

    /**
     * The name of the device.
     */
    private final DeviceID deviceID;
    /**
     * The model of the device.
     */
    private final DeviceModel deviceModel;
    /**
     * The status of the device.
     */
    private DeviceStatus deviceStatus;
    /**
     * The roomID of the room where the device is located.
     */
    private final RoomID roomID;

    /**
     * Constructor to initialize a new device with the given name, model and roomID.
     *
     * @param deviceID    The name of the device.
     * @param deviceModel The model of the device.
     * @param roomID      The roomID of the room where the device is located.
     * @throws IllegalArgumentException if parameters are null or empty.
     */
    protected Device(DeviceID deviceID, DeviceModel deviceModel, RoomID roomID) {
        if (!validConstructorArguments(deviceID, deviceModel, roomID)) {
            throw new IllegalArgumentException("Device name, model and room ID cannot be null or empty");
        }
        this.deviceID = deviceID;
        this.deviceModel = deviceModel;
        this.deviceStatus = DeviceStatus.ACTIVE;
        this.roomID = roomID;
    }

    /**
     * Constructor to initialize a new device with the given name, model, roomID and status.
     *
     * @param deviceID     The name of the device.
     * @param deviceModel  The model of the device.
     * @param roomID       The roomID of the room where the device is located.
     * @param deviceStatus The status of the device.
     * @throws IllegalArgumentException if parameters are null or empty.
     */

    protected Device(DeviceID deviceID, DeviceModel deviceModel, RoomID roomID, DeviceStatus deviceStatus) {
        if (!validConstructorArguments(deviceID, deviceModel, roomID) || deviceStatus == null) {
            throw new IllegalArgumentException("Device name, model, room ID and deviceStatus cannot be null or empty");
        }
        this.deviceID = deviceID;
        this.deviceModel = deviceModel;
        this.deviceStatus = deviceStatus;
        this.roomID = roomID;
    }


    /**
     * Method to validate the parameters for Device instantiation.
     *
     * @param deviceID    Name of Device. Cannot be null.
     * @param deviceModel Model of Device. Cannot be null.
     * @param roomID      The roomID of the room where the device is located. Cannot be null.
     * @return true if parameters are valid, false otherwise.
     */

    private boolean validConstructorArguments(DeviceID deviceID, DeviceModel deviceModel, RoomID roomID) {
        return deviceID != null && deviceModel != null && roomID != null;
    }

    /**
     * Method to get the name of the device.
     *
     * @return The name of the device.
     */
    public String getDeviceName() {
        return deviceID.toString();
    }

    /**
     * Method to get the roomID of the room where the device is located.
     *
     * @return The roomID of the room where the device is located.
     */
    public RoomID getRoomID() {
        return roomID;
    }

    /**
     * Method to get the model of the device.
     *
     * @return The model of the device.
     */
    public DeviceModel getDeviceModel() {
        return deviceModel;
    }

    /**
     * Method to get the status of the device.
     *
     * @return The status of the device.
     */
    public DeviceStatus getDeviceStatus() {
        return deviceStatus;
    }

    /**
     * Method to verify if a device is active.
     *
     * @return true if the device is active.
     */
    public boolean isActive() {
        return getDeviceStatus() == DeviceStatus.ACTIVE;
    }

    /**
     * Method to change the status of a device to deactivated, if the device is active.
     *
     * @return true if a device was deactivated.
     */
    public boolean deactivate() {
        if (isActive()) {
            deviceStatus = DeviceStatus.DEACTIVATED;
            return true;
        }
        return false;
    }

    /**
     * Method to get the identity of the device.
     *
     * @return deviceID The name of the device.
     */
    @Override
    public DeviceID identity() {
        return deviceID;
    }

    /**
     * Method to check if two devices are the same.
     *
     * @param object The object to compare with.
     * @return true if the devices are the same.
     */
    @Override
    public boolean isSameAs(Object object) {
        if (object instanceof Device) {
            Device objectDevice = (Device) object;
            if (this.deviceID.equals(objectDevice.deviceID) &&
                    this.deviceModel.equals(objectDevice.deviceModel) &&
                    this.deviceStatus.equals(objectDevice.deviceStatus) &&
                    this.roomID.equals(objectDevice.roomID)) {
                return true;
            }
        }
        return false;
    }

}

