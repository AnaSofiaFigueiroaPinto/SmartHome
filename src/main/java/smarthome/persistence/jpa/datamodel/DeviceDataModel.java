package smarthome.persistence.jpa.datamodel;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import smarthome.domain.device.Device;

@Entity
@Table(name = "DEVICE")

/**
 * The DeviceDataModel class represents a data model for a Device entity.
 */
public class DeviceDataModel {

    /**
     * The unique identifier of the device.
     */
    @Id
    private String deviceID;

    /**
     * The model of the device.
     */
    private String deviceModel;

    /**
     * The status of the device.
     */
    private String deviceStatus;

    /**
     * The ID of the room where the device is located.
     */
    private String roomID;

    /**
     * Default constructor.
     */
    public DeviceDataModel() {
    }

    /**
     * Constructor to create a DeviceDataModel object.
     *
     * @param device The device object to be converted.
     */
    public DeviceDataModel(Device device)
    {
        this.deviceID = device.getDeviceName();
        this.deviceModel = device.getDeviceModel().toString();
        this.deviceStatus = device.getDeviceStatus().toString();
        this.roomID = device.getRoomID().toString();
    }

    /**
     * Get the device ID.
     * @return The device ID.
     */
    public String getDeviceID()
    {
        return deviceID;
    }
    /**
     * Get the device model.
     * @return The device model.
     */
    public String getDeviceModel() {
        return deviceModel;
    }

    /**
     * Get the device status.
     * @return The device status.
     */
    public String getDeviceStatus()
    {
        return deviceStatus;
    }
    /**
     * Get the room ID where the device is located.
     * @return The room ID.
     */
    public String getRoomID()
    {
        return roomID;
    }

    /**
     * Updates the DeviceDataModel based on the information provided by the given Device from Domain.
     *
     * @param device The Device object containing updated information.
     * @return true if the update is successful, false otherwise.
     */
    public boolean updatedFromDomain(Device device)
    {
        if (device == null) {
            return false;
        }
        this.deviceID = device.getDeviceName();
        this.deviceModel = device.getDeviceModel().toString();
        this.deviceStatus = device.getDeviceStatus().toString();
        this.roomID= device.getRoomID().toString();
        return true;
        }
    }

