package smarthome.controllercli;

import smarthome.domain.valueobjects.ActuatorFunctionalityID;
import smarthome.domain.valueobjects.ActuatorID;
import smarthome.domain.valueobjects.ActuatorProperties;
import smarthome.domain.valueobjects.DeviceID;
import smarthome.mapper.ActuatorDTO;
import smarthome.mapper.AssemblerActuatorProperties;
import smarthome.service.ActuatorService;

/**
 * Controller class responsible for handling the addition of a new actuator to a device.
 */
public class AddNewActuatorToDeviceController {

    /**
     * ActuatorService instance to interact with for actuator-related operations.
     */
    private final ActuatorService actuatorService;

    /**
     * Constructs a new AddNewActuatorToDeviceController with the specified services, repositories, and house ID.
     * @param actuatorService              The ActuatorService instance to interact with for actuator-related operations.
     */
    public AddNewActuatorToDeviceController(ActuatorService actuatorService) {
        this.actuatorService = actuatorService;
    }

    /**
     * Method to add a new Actuator and save it in the repository.
     *
     * @param actuatorDTO ActuatorDTO with information to create a new Actuator.
     * @return ActuatorDTO if Actuator was added successfully and saved in the repository.
     */
    public ActuatorDTO createActuatorAndSaveToRepository(
            ActuatorDTO actuatorDTO
    ) {
        try {
            ActuatorFunctionalityID actuatorFunctionalityID = new ActuatorFunctionalityID(actuatorDTO.actuatorFunctionalityName);
            ActuatorID actuatorID = new ActuatorID(actuatorDTO.actuatorName);
            DeviceID deviceID = new DeviceID(actuatorDTO.deviceName);

            AssemblerActuatorProperties propertiesMapperDTO = new AssemblerActuatorProperties();
            ActuatorProperties actuatorProperties = propertiesMapperDTO.createActuatorPropertiesFromActuatorDTO(actuatorDTO);

            //If this method does not successfully create an actuator and save in the repository, an exception is thrown
            ActuatorID createdActuatorID = actuatorService.createActuatorAndSave(actuatorFunctionalityID, actuatorID, actuatorProperties, deviceID);

            return new ActuatorDTO(createdActuatorID.toString(), actuatorDTO.actuatorFunctionalityName, actuatorDTO.deviceName, actuatorDTO.upperLimitInt, actuatorDTO.lowerLimitInt, actuatorDTO.upperLimitDecimal, actuatorDTO.lowerLimitDecimal, actuatorDTO.precision);
        } catch (NullPointerException | IllegalArgumentException e) {
            return null;
        }
    }

}
