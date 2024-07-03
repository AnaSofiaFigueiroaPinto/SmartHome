package smarthome.controllercli;

import smarthome.domain.valueobjects.DeviceID;
import smarthome.domain.valueobjects.SensorFunctionalityID;
import smarthome.domain.valueobjects.SensorID;
import smarthome.mapper.DeviceDTO;
import smarthome.mapper.SensorDTO;
import smarthome.service.SensorService;

/**
 * Controller class responsible for handling the addition of a new sensor to a device.
 */
public class AddNewSensorToDeviceController {
    /**
     * SensorService instance to interact with for sensor-related operations.
     */
    private final SensorService sensorService;

    /**
     * Constructs a new AddNewSensorToDeviceController with the specified services.
     * @param sensorService The SensorService instance to interact with for sensor-related operations.
     */
    public AddNewSensorToDeviceController(SensorService sensorService) {
        this.sensorService = sensorService;
    }

    /**
     * Method to add a new Sensor object, selected from the list of available sensor classes, to a Device object.
     * @param deviceDTO           DeviceDTO object to identify the device to which the sensor will be added.
     * @param sensorName          String sensorName of new Sensor instance.
     * @param sensorFunctionality String sensorFunctionality to identify the functionality of the new actuator.
     * @return {@code SensorDTO} representing the created sensor if successful, {@code null} otherwise.
     */
    public SensorDTO createSensorAndSaveToRepository(
            DeviceDTO deviceDTO, String sensorName,
            String sensorFunctionality
    ) {
        try {
            SensorFunctionalityID sensorFunctionalityID = new SensorFunctionalityID(sensorFunctionality);
            SensorID sensorID = new SensorID(sensorName);
            DeviceID deviceID = new DeviceID(deviceDTO.deviceName);

            //If this method does not successfully create an actuator and save in the repository, an exception is thrown
            SensorID createdSensorID = sensorService.createSensorAndSave(deviceID, sensorFunctionalityID, sensorID);

            return new SensorDTO(createdSensorID.toString(), deviceID.toString(), sensorFunctionalityID.toString());
        } catch (RuntimeException e) {
            return null;
        }
    }

}

