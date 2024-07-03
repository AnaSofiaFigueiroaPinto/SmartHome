package smarthome.domain.value;

import smarthome.domain.valueobjects.GPSCode;
import smarthome.domain.valueobjects.Reading;
import smarthome.domain.valueobjects.SensorID;
import smarthome.domain.valueobjects.ValueID;

import java.sql.Timestamp;
import java.util.UUID;

/**
 * Represents a value in instant time and location.
 */
public class InstantTimeLocationValue implements Value {

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
     * The GPS code of the reading.
     */
    private final GPSCode gpsCode;

    /**
     * Constructs an InstantTimeLocationValue object with a randomly generated valueID.
     *
     * @param sensorID           The ID of the sensor associated with this value.
     * @param reading            The value's reading.
     * @param instantTimeReading The specific instant.
     * @param gpsCode            The location associated with this value.
     * @throws IllegalArgumentException If any of the parameters are null.
     */
    protected InstantTimeLocationValue(SensorID sensorID, Reading reading, Timestamp instantTimeReading, GPSCode gpsCode)
    {
        this.valueID = new ValueID(UUID.randomUUID().toString());
        this.sensorID = sensorID;
        this.reading = reading;
        this.instantTimeReading = instantTimeReading;
        this.gpsCode = gpsCode;
    }

    /**
     * Constructs an InstantTimeLocationValue object.
     *
     * @param valueID            The ID of the value.
     * @param sensorID           The ID of the sensor associated with this value.
     * @param reading            The value's reading.
     * @param instantTimeReading The specific instant.
     * @param gpsCode            The location associated with this value.
     * @throws IllegalArgumentException If any of the parameters are null.
     */
    protected InstantTimeLocationValue(ValueID valueID, SensorID sensorID, Reading reading, Timestamp instantTimeReading, GPSCode gpsCode)
    {
        this.valueID = valueID;
        this.sensorID = sensorID;
        this.reading = reading;
        this.instantTimeReading = instantTimeReading;
        this.gpsCode = gpsCode;
    }

    /**
     * Returns the identity of this value.
     *
     * @return The ID of this value as a ValueID object.
     */
    public ValueID identity() {
        return valueID;
    }

    /**
     * Gets the value's reading.
     *
     * @return The value's reading.
     */
    public Reading getReading() {
        return reading;
    }

    /**
     * Gets the ID of the sensor that associated with this value.
     *
     * @return The ID of the sensor that associated with this value.
     */

    public SensorID getSensorID() {
        return sensorID;
    }

    /**
     * Gets the instant of the Value.
     *
     * @return The specific instant of a value.
     */
    public Timestamp getInstantTime() {
        return instantTimeReading;
    }

    /**
     * Gets the gpsCode of the Value.
     *
     * @return The gps coordinates of a value.
     */
    public GPSCode getGpsCode() {
        return gpsCode;
    }

    /**
     * Checks if this InstantTimeLocationValue is the same as another object.
     *
     * @param object The object to compare.
     * @return True if the objects are equal, false otherwise.
     */
    public boolean isSameAs(Object object) {
        if (object instanceof InstantTimeLocationValue) {
            InstantTimeLocationValue instantTimeLocationValue = (InstantTimeLocationValue) object;
            return this.valueID.equals(instantTimeLocationValue.valueID);
        }
        return false;
    }
}
