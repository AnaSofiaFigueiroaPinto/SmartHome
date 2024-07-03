package smarthome.domain.sensor;

import smarthome.domain.valueobjects.DeviceID;
import smarthome.domain.valueobjects.SensorFunctionalityID;
import smarthome.domain.valueobjects.SensorID;

/**
 * Class for Sunset sensor
 */
public class SunsetSensor extends Sensor {

    /**
     * Constructs a Sensor with the given sensor name and factory value.
     *
     * @param sensorName      String with the name of the sensor.
     * @param deviceID        DeviceID object with the name of the device.
     * @param functionalityID ID of Functionality for sensor.
     * @throws IllegalArgumentException If any of the parameters are null.
     */
    protected SunsetSensor(
            SensorID sensorName,
            DeviceID deviceID,
            SensorFunctionalityID functionalityID
    ) {
        super(sensorName, deviceID, functionalityID);
    }
}

