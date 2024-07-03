package smarthome.domain.value;

import org.springframework.stereotype.Component;
import smarthome.domain.valueobjects.Reading;
import smarthome.domain.valueobjects.SensorID;
import smarthome.domain.valueobjects.ValueID;

import java.sql.Timestamp;

/**
 * Class responsible for creating an InstantTimeValue.
 */
@Component
public class ImpFactoryInstantTimeValue implements FactoryValue {

    /**
     * Creates a new Value object representing an instantaneous value measured at a specific timestamp.
     *
     * @param valueID            the ID associated with the value.
     * @param sensorID           the ID of the sensor associated with the value.
     * @param reading            the actual value being measured.
     * @param instantTimeReading the instant of the reading.
     * @return a new Value object representing the provided data.
     * @throws IllegalArgumentException if any of the parameters are invalid.
     */
    public Value createValue(ValueID valueID, SensorID sensorID, Reading reading, Timestamp instantTimeReading) {
        if (valueID == null || reading == null || instantTimeReading == null || sensorID == null) {
            throw new IllegalArgumentException("Please provide valid parameters!");
        }
        return new InstantTimeValue(valueID, sensorID, reading, instantTimeReading);
    }

    /**
     * Creates a new Value object representing an instantaneous value measured at a specific timestamp without valueID.
     *
     * @param sensorID           the ID of the sensor associated with the value.
     * @param reading            the actual value being measured.
     * @param instantTimeReading the instant of the reading.
     * @return a new Value object representing the provided data.
     * @throws IllegalArgumentException if any of the parameters are invalid.
     */
    public Value createValue(SensorID sensorID, Reading reading, Timestamp instantTimeReading)
    {
        if (reading == null || instantTimeReading == null || sensorID == null)
        {
            throw new IllegalArgumentException("Please provide valid parameters!");
        }
        return new InstantTimeValue(sensorID, reading, instantTimeReading);
    }
}

