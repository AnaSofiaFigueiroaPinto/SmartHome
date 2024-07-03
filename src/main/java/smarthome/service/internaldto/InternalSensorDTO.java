package smarthome.service.internaldto;

import smarthome.domain.valueobjects.DeviceID;
import smarthome.domain.valueobjects.SensorFunctionalityID;
import smarthome.domain.valueobjects.SensorID;

/**
 * InternalSensorDTO is a Data Transfer Object (DTO) used for internal communication
 * within the application. It encapsulates the information related to a sensor, including
 * its ID, functionality ID, and the device ID to which it is associated.
 */
public class InternalSensorDTO {

    /**
     * The unique identifier of the sensor.
     */
    public SensorID sensorID;

    /**
     * The unique identifier of the sensor functionality.
     */
    public SensorFunctionalityID sensorFunctionalityID;

    /**
     * The unique identifier of the device associated with the sensor.
     */
    public DeviceID deviceID;

    /**
     * Constructs a new InternalSensorDTO with the specified sensor ID, sensor functionality ID,
     * and device ID.
     *
     * @param sensorID the ID of the sensor
     * @param sensorFunctionalityID the ID of the sensor functionality«
     * @param deviceID the ID of the device
     */
    public InternalSensorDTO(SensorID sensorID, SensorFunctionalityID sensorFunctionalityID, DeviceID deviceID) {
        this.sensorID = sensorID;
        this.sensorFunctionalityID = sensorFunctionalityID;
        this.deviceID = deviceID;
    }
}

