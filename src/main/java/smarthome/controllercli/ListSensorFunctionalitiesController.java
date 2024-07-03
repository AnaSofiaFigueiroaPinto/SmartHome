package smarthome.controllercli;

import smarthome.domain.valueobjects.SensorFunctionalityID;
import smarthome.mapper.SensorFunctionalityDTO;
import smarthome.mapper.SensorFunctionalityMapperDTO;
import smarthome.service.SensorFunctionalityService;

import java.util.List;

/**
 * Controller class responsible for listing all available sensor functionalities.
 */
public class ListSensorFunctionalitiesController {

    /**
     * SensorFunctionalityService instance to interact with for sensor functionality operations.
     */
    private final SensorFunctionalityService sensorFunctionalityService;

    /**
     * Constructs a new ListSensorFunctionalities with the specified services.
     * @param sensorFunctionalityService The SensorFunctionalityService instance to interact with for sensor functionality operations.
     */
    public ListSensorFunctionalitiesController(SensorFunctionalityService sensorFunctionalityService) {
        this.sensorFunctionalityService = sensorFunctionalityService;
    }

    /**
     * Method to retrieve a list of available sensor functionalities.
     * @return List of SensorFunctionalityDTO objects.
     */
    public List<SensorFunctionalityDTO> getListOfSensorFunctionalities() {
        try {
            SensorFunctionalityMapperDTO sensorFunctionalityMapperDTO = new SensorFunctionalityMapperDTO();
            List<SensorFunctionalityID> sensorFunctionalitiesIDs = sensorFunctionalityService.getListOfSensorFunctionalities();
            return sensorFunctionalityMapperDTO.sensorFunctionalityToDTOList(sensorFunctionalitiesIDs);
        } catch (NullPointerException e) {
            return null;
        }
    }

}
