package smarthome.domain.value;

import smarthome.domain.valueobjects.Reading;
import smarthome.domain.valueobjects.SensorID;
import smarthome.domain.valueobjects.ValueID;

import java.sql.Timestamp;
import java.util.UUID;

/**
 * Represents a value in specific instant.
 */
public class InstantTimeValue implements Value
{

    /**
     * The ID of this value.
     */
    private final ValueID valueID;

    /**
     * The Sensor ID associated with this value.
     */
    private final SensorID sensorID;

    /**
     * The value's reading.
     */
    private final Reading reading;

    /**
     * The timestamp of the reading.
     */
    private final Timestamp instantTimeReading;


    /**
     * Constructs an InstantValue object with a randomly generated valueID.
     *
     * @param sensorID           The ID of the sensor associated with this value.
     * @param reading            The value's reading.
     * @param instantTimeReading The specific instant.
     * @throws IllegalArgumentException If any of the parameters are null.
     */
    protected InstantTimeValue(SensorID sensorID, Reading reading, Timestamp instantTimeReading)
    {
        this.valueID = new ValueID(UUID.randomUUID().toString());
        this.sensorID = sensorID;
        this.reading = reading;
        this.instantTimeReading = instantTimeReading;
    }

    /**
     * Constructs an InstantValue object.
     * @param valueID         The ID of the value.
     * @param sensorID        The ID of the sensor that associated with this value.
     * @param reading            The value's reading.
     * @param instantTimeReading The specific instant.
     * @throws IllegalArgumentException If any of the parameters are null.
     */
    protected InstantTimeValue(ValueID valueID, SensorID sensorID, Reading reading, Timestamp instantTimeReading)
    {
        this.valueID = valueID;
        this.sensorID = sensorID;
        this.reading = reading;
        this.instantTimeReading = instantTimeReading;
    }

    /**
     * Returns the identity of this value.
     * @return The ID of this value as a ValueID object.
     */
    public ValueID identity()
    {
        return valueID;
    }

    /**
     * Gets the value's reading.
     * @return The value's reading.
     */
    public Reading getReading()
    {
        return reading;
    }

    /**
     * Gets the ID of the sensor that associated with this value.
     * @return The ID of the sensor that associated with this value.
     */
    public SensorID getSensorID()
    {
        return sensorID;
    }

    /**
     * Gets the instant os the reading.
     * @return The instant of the reading.
     */
    public Timestamp getInstantTimeReading()
    {
        return instantTimeReading;
    }

    /**
     * Checks if this InstantValue is the same as another object.
     * @param object The object to compare.
     * @return True if the objects are equal, false otherwise.
     */
    public boolean isSameAs (Object object)
    {
        if (object instanceof InstantTimeValue)
        {
            InstantTimeValue instantTimeValue = (InstantTimeValue) object;
            return this.valueID.equals(instantTimeValue.valueID);
        }
        return false;
    }
}
