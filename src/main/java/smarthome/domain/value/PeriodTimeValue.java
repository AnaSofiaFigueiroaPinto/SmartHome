package smarthome.domain.value;

import smarthome.domain.valueobjects.Reading;
import smarthome.domain.valueobjects.SensorID;
import smarthome.domain.valueobjects.ValueID;

import java.sql.Timestamp;
import java.util.UUID;

/**
 * Represents a value over a period of time.
 */
public class PeriodTimeValue implements Value
{

    /**
     * The ID of this value.
     */
    private final ValueID valueID;

    /** The Sensor ID associated with this value. */
    private final SensorID sensorID;

    /**
     * The reading over a period of time.
     */
    private final Reading reading;

    /**
     * The timestamp when the period started.
     */
    private final Timestamp startTimeReading;

    /**
     * The timestamp when the period ended.
     */
    private final Timestamp endTimeReading;

    /**
     * Constructs a PeriodValue object with a randomly generated valueID.
     * @param sensorID           The ID of the sensor associated with this value.
     * @param reading            The reading over a period of time.
     * @param startTimeReading   The timestamp when the period started.
     * @param endTimeReading     The timestamp when the period ended.
     * @throws IllegalArgumentException If any of the parameters are null.
     */
    protected PeriodTimeValue(SensorID sensorID, Reading reading, Timestamp startTimeReading, Timestamp endTimeReading)
    {
        this.valueID = new ValueID(UUID.randomUUID().toString());
        this.sensorID = sensorID;
        this.reading = reading;
        this.startTimeReading = startTimeReading;
        this.endTimeReading = endTimeReading;
    }

    /**
     * Constructs a PeriodValue object.
     * @param valueID        The ID of the value.
     * @param sensorID       The ID of the sensor that associated with this value.
     * @param reading        The reading over a period of time.
     * @param startTimeReading  The timestamp when the period started.
     * @param endTimeReading The timestamp when the period ended.
     * @throws IllegalArgumentException If any of the parameters are null.
     */
    protected PeriodTimeValue(ValueID valueID, SensorID sensorID, Reading reading, Timestamp startTimeReading, Timestamp endTimeReading)
    {
        this.valueID = valueID;
        this.sensorID = sensorID;
        this.reading = reading;
        this.startTimeReading = startTimeReading;
        this.endTimeReading = endTimeReading;
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
     * Gets the reading over a period of time.
     * @return The reading over a period of time.
     */
    public Reading getReading()
    {
        return reading;
    }

    /**
     * Gets the ID of the sensor that produced this value.
     * @return The ID of the sensor that associated with this value.
     */
    public SensorID getSensorID()
    {
        return sensorID;
    }

    /**
     * Gets the start time of the reading.
     * @return The start time of the reading.
     */
    public Timestamp getStartTimeReading()
    {
        return startTimeReading;
    }

    /**
     * Gets the end time of the reading.
     * @return The end time of the reading.
     */
    public Timestamp getEndTimeReading()
    {
        return endTimeReading;
    }

    /**
     * Checks if this PeriodValue is the same as another object.
     * @param object The object to compare.
     * @return True if the objects are equal, false otherwise.
     */
    public boolean isSameAs(Object object)
    {
        if (object instanceof PeriodTimeValue)
        {
            PeriodTimeValue periodTimeValue = (PeriodTimeValue) object;
            return this.valueID.equals(periodTimeValue.valueID);
        }
        return false;
    }
}
