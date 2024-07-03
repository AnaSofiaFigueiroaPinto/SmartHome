package smarthome.domain.value;

import org.springframework.stereotype.Component;
import smarthome.domain.valueobjects.Reading;
import smarthome.domain.valueobjects.SensorID;
import smarthome.domain.valueobjects.ValueID;

import java.sql.Timestamp;

/**
 * Class responsible for creating a PeriodTimeValue.
 */
@Component
public class ImpFactoryPeriodTimeValue implements FactoryValue
{

    /**
     * Creates a new Value object representing a value measured over a period of time.
     * @param valueID        The ID of the value.
     * @param sensorID       The ID of the sensor that associated with this value.
     * @param reading        The reading over a period of time.
     * @param startTimeReading  The timestamp when the period started.
     * @param endTimeReading The timestamp when the period ended.
     * @return a new Value object representing the provided data.
     * @throws IllegalArgumentException if any of the parameters are invalid.
     */
    public Value createValue (ValueID valueID, SensorID sensorID, Reading reading, Timestamp startTimeReading, Timestamp endTimeReading)
    {
        if ( valueID == null || reading == null || startTimeReading == null || endTimeReading == null || sensorID == null )
        {
            throw new IllegalArgumentException("Please provide valid parameters!");
        }
        return new PeriodTimeValue(valueID, sensorID, reading, startTimeReading, endTimeReading);
    }

    /**
     * Creates a new Value object representing a value measured over a period of time without valueID.
     * @param sensorID       The ID of the sensor that associated with this value.
     * @param reading        The reading over a period of time.
     * @param startTimeReading  The timestamp when the period started.
     * @param endTimeReading The timestamp when the period ended.
     * @return a new Value object representing the provided data.
     * @throws IllegalArgumentException if any of the parameters are invalid.
     */

    public Value createValue(SensorID sensorID, Reading reading, Timestamp startTimeReading, Timestamp endTimeReading)
    {
        if (reading == null || startTimeReading == null || endTimeReading == null || sensorID == null)
        {
            throw new IllegalArgumentException("Please provide valid parameters!");
        }
        return new PeriodTimeValue(sensorID, reading, startTimeReading, endTimeReading);
    }
}
