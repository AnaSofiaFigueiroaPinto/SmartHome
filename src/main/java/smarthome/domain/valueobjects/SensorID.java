package smarthome.domain.valueobjects;

import smarthome.ddd.DomainID;

import java.util.Objects;

/**
 * Represents a {@code SensorID} class within the smart home system.
 */
public class SensorID implements DomainID
{

    /**
     * Declaring the attributes that will be used in the constructors.
     */
    private final String sensorName;

    /**
     * Constructor for DeviceID
     *
     * @param sensorName String representing the name of the sensor that is used as a unique identifier.
     */
    public SensorID(String sensorName)
    {
        if (sensorName == null || sensorName.isEmpty() || sensorName.isBlank()) {
            throw new IllegalArgumentException("Sensor name (string) is an ID and cannot be null or empty");
        }
        this.sensorName = sensorName;
    }

    /**
     * Method that checks if two SensorID are equal.
     * @param object Object that is compared to the SensorID.
     * @return True if the SensorID is equal to the object. False if it is not.
     */
    @Override
    public boolean equals(Object object)
    {
        if (this == object)
            return true;
        if (object == null || getClass() != object.getClass())
            return false;
        SensorID sensorID = (SensorID) object;
        return Objects.equals(sensorName, sensorID.sensorName);
    }

    /**
     * Method that returns the SensorID as a string.
     * @return SensorID as a string.
     */
    @Override
    public String toString()
    {
        return sensorName;
    }

    /**
     * Method that returns the hash code of the SensorID.
     * @return Hash code of the SensorID.
     */
    @Override
    public int hashCode()
    {
        return Objects.hash(sensorName);
    }

}
