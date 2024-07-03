package smarthome.domain.valueobjects;

import smarthome.ddd.ValueObject;

import java.util.Objects;

/**
 * Represents a {@code DeviceModel} class within the smart home system.
 */
public class DeviceModel implements ValueObject {

    /**
     * Declaring the attributes that will be used in the constructors.
     */
    private final String deviceModel;

    /**
     * Constructor for DeviceModel
     * @param deviceModel Model of Device object (String). Cannot be null or empty.
     */
    public DeviceModel (String deviceModel) {
        if (deviceModel == null || deviceModel.isEmpty() || deviceModel.isBlank()) {
            throw new IllegalArgumentException("Device Model cannot be null or empty");
        }
        this.deviceModel = deviceModel;
    }

    /**
     * Method that checks if two DeviceModels are equal.
     *
     * @param o Object that is compared to the DeviceModel.
     * @return True if the DeviceModel is equal to the object. False if it is not.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeviceModel that = (DeviceModel) o;
        return Objects.equals(deviceModel, that.deviceModel);
    }

    /**
     * Method that returns the hash code of the DeviceModel.
     *
     * @return Integer representing the hash code of the DeviceModel.
     */
    @Override
    public int hashCode() {
        return Objects.hash(deviceModel);
    }

    /**
     * Method that returns the model of a device.
     *
     * @return String representing the model of a room.
     */
    @Override
    public String toString() {
        return deviceModel;
    }
}
