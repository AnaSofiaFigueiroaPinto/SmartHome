package smarthome.service;

import org.springframework.stereotype.Service;
import smarthome.domain.repository.SensorFunctionalityRepository;
import smarthome.domain.sensorfunctionality.SensorFunctionality;
import smarthome.domain.valueobjects.SensorFunctionalityID;

import java.util.ArrayList;
import java.util.List;

/**
 * A service class for the SensorFunctionality entities.
 */
@Service
public class SensorFunctionalityService {

    /**
     * The repository for accessing SensorFunctionality entities.
     */
    private SensorFunctionalityRepository sensorFunctionalityRepository;

    /**
     * Constructs a SensorFunctionalityService with the specified repository and mapper.
     *
     * @param sensorFunctionalityRepository The repository for accessing SensorFunctionality entities.
     */
    public SensorFunctionalityService(SensorFunctionalityRepository sensorFunctionalityRepository) {
        this.sensorFunctionalityRepository = sensorFunctionalityRepository;
    }

    /**
     * Retrieves a list of SensorFunctionality entities from the repository and converts them to DTOs.
     *
     * @return A map containing SensorFunctionalityDTO objects as keys and corresponding SensorFunctionality entities as values.
     */
    public List<SensorFunctionalityID> getListOfSensorFunctionalities() {
        Iterable<SensorFunctionality> sensorFunctionalities = sensorFunctionalityRepository.findAllEntities();
        List<SensorFunctionalityID> sensorFunctionalitiesIDs = new ArrayList<>();
        for (SensorFunctionality sensorFunctionality : sensorFunctionalities) {
            SensorFunctionalityID sensorFunctionalityID = sensorFunctionality.identity();
            sensorFunctionalitiesIDs.add(sensorFunctionalityID);
        }
        return sensorFunctionalitiesIDs;
    }

}

