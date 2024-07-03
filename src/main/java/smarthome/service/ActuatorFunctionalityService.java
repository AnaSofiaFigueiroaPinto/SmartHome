package smarthome.service;

import org.springframework.stereotype.Service;
import smarthome.domain.actuatorfunctionality.ActuatorFunctionality;
import smarthome.domain.repository.ActuatorFunctionalityRepository;
import smarthome.domain.valueobjects.ActuatorFunctionalityID;

import java.util.ArrayList;
import java.util.List;

/**
 * A service class for the ActuatorFunctionality entities.
 */
@Service
public class ActuatorFunctionalityService {
    /**
     * The repository for accessing ActuatorFunctionality entities.
     */
    private ActuatorFunctionalityRepository actuatorFunctionalityRepository;

    /**
     * Constructor of the ActuatorFunctionalityService class that receives an ActuatorFunctionalityRepositoryMem object.
     */
    public ActuatorFunctionalityService(ActuatorFunctionalityRepository actuatorFunctionalityRepository) {
        this.actuatorFunctionalityRepository = actuatorFunctionalityRepository;
    }

    /**
     * Method that retrieves a list of ActuatorFunctionality entities from the repository and converts them to DTOs.
     *
     * @return A list containing ActuatorFunctionalityID objects or an empty list
     * if no ActuatorFunctionality entities are found.
     */
    public List<ActuatorFunctionalityID> getListOfActuatorFunctionalities() {

        Iterable<ActuatorFunctionality> actuatorFunctionalities = actuatorFunctionalityRepository.findAllEntities();
        List<ActuatorFunctionalityID> actuatorFunctionalitiesIDs = new ArrayList<>();
        for (ActuatorFunctionality actuatorFunctionality : actuatorFunctionalities) {
            ActuatorFunctionalityID actuatorFunctionalityID = actuatorFunctionality.identity();
            actuatorFunctionalitiesIDs.add(actuatorFunctionalityID);
        }
        return actuatorFunctionalitiesIDs;
    }
}

