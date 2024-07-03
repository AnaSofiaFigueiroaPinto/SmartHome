package smarthome.domain.sensor;

import smarthome.ddd.AggregateRoot;
import smarthome.domain.valueobjects.DeviceID;
import smarthome.domain.valueobjects.SensorFunctionalityID;
import smarthome.domain.valueobjects.SensorID;

/**
 * Abstract class for Sensor.
 * Defines constructor of Sensor implementation classes and the following methods:
 * - getSensorFunctionalityID -> SensorFunctionalityID
 * - identity -> SensorID
 * - getDeviceID -> DeviceID
 */
public abstract class Sensor implements AggregateRoot<SensorID> {

    private final SensorID sensorID;
    private final DeviceID deviceID;
    private final SensorFunctionalityID functionalityID;

    /**
     * Constructs a Sensor with the given sensor name and factory value.
     *
     * @param sensorID String with the name of the sensor.
     * @param deviceID DeviceID object with the name of the device.
     * @param functionalityID ID of Functionality for sensor.
     * @throws IllegalArgumentException If any of the parameters are null.
     */

    protected Sensor (
            SensorID sensorID,
            DeviceID deviceID,
            SensorFunctionalityID functionalityID
    ) {
        if (!validConstructorArguments(sensorID, deviceID, functionalityID)) {
            throw new IllegalArgumentException("Invalid device name or functionality or factory value.");
        }
        this.sensorID = sensorID;
        this.deviceID = deviceID;
        this.functionalityID = functionalityID;
    }

    /**
     * Check if parameters used in object constructor are valid
     * @param deviceID deviceID that sensor will be added to. Mustn't be null
     * @param functionalityID functionalityID of the sensor. Mustn't be null
     * @return true if all parameters are valid and false if one or more are invalid
     */
    private boolean validConstructorArguments (
            SensorID sensorID,
            DeviceID deviceID,
            SensorFunctionalityID functionalityID
    ) {
        if (sensorID == null || deviceID == null || functionalityID == null) {
            return false;
        }
        return true;
    }

    /**
     * Retrieves the functionality of the sensor.
     *
     * @return The functionality of the sensor.
     */
    public SensorFunctionalityID getSensorFunctionalityID() {
        return functionalityID;
    }

    /**
     * Method to get the identifier attribute (ID) of the object
     * @return SensorID object which is the name of the sensor instance
     */
    public SensorID identity () {
        return sensorID;
    }

    /**
     * Method to get the identifier attribute (ID) of the associated Device object
     * @return DeviceID object associated with this instance of Sensor
     */
    public DeviceID getDeviceID() {
        return deviceID;
    }

    /**
     * Method to check if two objects of this type are the same
     * @param object instance of ScaleSensor
     * @return True if the objects are the same, false if they are not
     */
    public boolean isSameAs (Object object) {
        if(object instanceof Sensor) {
            Sensor sensor = (Sensor) object;
            return this.sensorID.equals(sensor.identity());
        }
        return false;
    }
}
