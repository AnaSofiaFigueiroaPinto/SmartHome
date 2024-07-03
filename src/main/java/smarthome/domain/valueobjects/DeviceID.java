package smarthome.domain.valueobjects;

import smarthome.ddd.DomainID;

/**
 * Represents a {@code DeviceID} class within the smart home system.
 */
public class DeviceID implements DomainID {

    /**
     * Declaring the attributes that will be used in the constructors.
     */
    private String deviceName;

    /**
     * Constructor for DeviceID
     * @param deviceName String representing the name of the device that is used as unique identifier.
     */
    public DeviceID (String deviceName) {

        if (deviceName == null || deviceName.isEmpty() || deviceName.isBlank())
            throw new IllegalArgumentException("Device name (string) is an ID and cannot be null or empty");
        this.deviceName = deviceName;
    }

    /**
     * Method that checks if two DeviceID are equal.
     * @param o Object that is compared to the DeviceID.
     * @return True if the DeviceID is equal to the object. False if it is not.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeviceID deviceID = (DeviceID) o;
        return deviceName.equals(deviceID.deviceName);
    }

    /**
     * Method that returns the hash code of the DeviceID.
     * @return Integer representing the hash code of the DeviceID.
     */
    @Override
    public int hashCode() {
        return deviceName.hashCode();
    }

    /**
     * Method that returns the ID of a device (deviceName).
     * @return String representing the ID of a device.
     */
    @Override
    public String toString() {
        return deviceName;
    }

}
