package smarthome.domain.value;

import smarthome.ddd.AggregateRoot;
import smarthome.domain.valueobjects.Reading;
import smarthome.domain.valueobjects.SensorID;
import smarthome.domain.valueobjects.ValueID;

/**
 * Represents a value that is produced by a sensor.
 */
public interface Value extends AggregateRoot<ValueID> {
    /**
     * Gets the reading associated with this value.
     * @return The reading associated with this value.
     */
    Reading getReading();

    /**
     * Gets the ID of the sensor that produced this value.
     * @return The ID of the sensor that produced this value.
     */
    SensorID getSensorID();


}
