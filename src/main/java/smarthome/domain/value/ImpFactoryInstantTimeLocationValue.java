package smarthome.domain.value;

import org.springframework.stereotype.Component;
import smarthome.domain.valueobjects.GPSCode;
import smarthome.domain.valueobjects.Reading;
import smarthome.domain.valueobjects.SensorID;
import smarthome.domain.valueobjects.ValueID;

import java.sql.Timestamp;

/**
 * Class responsible for creating an InstantTimeLocationValue.
 */
@Component
public class ImpFactoryInstantTimeLocationValue implements FactoryValue {

    /**
     * Creates a new Value object representing an instantaneous value measured at a specific timestamp and location.
     *
     * @param valueID     the ID associated with the value.
     * @param sensorID    the ID of the sensor associated with the value.
     * @param reading     the actual value being measured.
     * @param instantTime the specific instant.
     * @param gpsCode     the location associated with this value.
     * @return a new Value object representing the provided data.
     * @throws IllegalArgumentException if any of the parameters are invalid.
     */
    public Value createValue(ValueID valueID, SensorID sensorID, Reading reading, Timestamp instantTime, GPSCode gpsCode) {
        if (valueID == null || sensorID == null || reading == null || instantTime == null || gpsCode == null) {
            throw new IllegalArgumentException("Please provide valid parameters!");
        }
        return new InstantTimeLocationValue(valueID, sensorID, reading, instantTime, gpsCode);
    }

    /**
     * Creates a new Value object representing an instantaneous value measured at a specific timestamp and location without valueID.
     *
     * @param sensorID    the ID of the sensor associated with the value.
     * @param reading     the actual value being measured.
     * @param instantTime the specific instant.
     * @param gpsCode     the location associated with this value.
     * @return a new Value object representing the provided data.
     * @throws IllegalArgumentException if any of the parameters are invalid.
     */
    public Value createValue(SensorID sensorID, Reading reading, Timestamp instantTime, GPSCode gpsCode)
    {
        if (sensorID == null || reading == null || instantTime == null || gpsCode == null)
        {
            throw new IllegalArgumentException("Please provide valid parameters!");
        }
        return new InstantTimeLocationValue(sensorID, reading, instantTime, gpsCode);
    }
}