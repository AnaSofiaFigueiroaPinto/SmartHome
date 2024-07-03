package smarthome.controllercli;

import smarthome.domain.valueobjects.ActuatorFunctionalityID;
import smarthome.mapper.ActuatorFunctionalityDTO;
import smarthome.mapper.ActuatorFunctionalityMapperDTO;
import smarthome.service.ActuatorFunctionalityService;

import java.util.List;

public class ListActuatorFunctionalitiesController {

    /**
     * ActuatorService instance to interact with for actuator-related operations.
     */
    private final ActuatorFunctionalityService actuatorFunctionalityService;

    /**
     * Constructs a new ListActuatorFunctionalitiesController with the specified services.
     * @param actuatorFunctionalityService The ActuatorFunctionalityService instance to interact with for actuator functionality operations.
     */
    public ListActuatorFunctionalitiesController(ActuatorFunctionalityService actuatorFunctionalityService) {
        this.actuatorFunctionalityService = actuatorFunctionalityService;
    }

    /**
     * Retrieves a list of actuator functionalities.
     * @return A list of strings representing the actuator functionalities.
     */
    public List<ActuatorFunctionalityDTO> getListOfActuatorFunctionalities() {
        try {
            ActuatorFunctionalityMapperDTO actuatorFunctionalityMapperDTO = new ActuatorFunctionalityMapperDTO();
            List<ActuatorFunctionalityID> listOfActuatorFunctionalitiesIDs = actuatorFunctionalityService.getListOfActuatorFunctionalities();
            return actuatorFunctionalityMapperDTO.actuatorFunctionalityToDTOList(listOfActuatorFunctionalitiesIDs);
        } catch (NullPointerException e) {
            return null;
        }
    }
}
