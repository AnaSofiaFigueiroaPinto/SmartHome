package smarthome.controllercli;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import smarthome.domain.actuatorfunctionality.FactoryActuatorFunctionality;
import smarthome.domain.actuatorfunctionality.ImpFactoryActuatorFunctionality;
import smarthome.mapper.ActuatorFunctionalityDTO;
import smarthome.persistence.repositoriesmem.ActuatorFunctionalityRepositoryMem;
import smarthome.service.ActuatorFunctionalityService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ListActuatorFunctionalitiesControllerTest {

    /**
     * SensorFunctionalityService instance to interact with sensor functionality-related operations.
     */
    private ActuatorFunctionalityService ActuatorFunctionalityService;

    /**
     * Sets up the test environment.
     */
    @BeforeEach
    void setUp() {
        FactoryActuatorFunctionality factoryActuatorFunctionality = new ImpFactoryActuatorFunctionality();
        ActuatorFunctionalityRepositoryMem ActuatorFunctionalityRepositoryMem = new ActuatorFunctionalityRepositoryMem(factoryActuatorFunctionality);
        ActuatorFunctionalityService = new ActuatorFunctionalityService(ActuatorFunctionalityRepositoryMem);
    }
    
    /**
     * Tests the case when the list of actuator functionalities is successfully retrieved.
     */
    @Test
    void successGetListOfActuatorFunctionalities() {
        ListActuatorFunctionalitiesController controller = new ListActuatorFunctionalitiesController(ActuatorFunctionalityService);
        List<ActuatorFunctionalityDTO> listOfActuatorFunctionalities = controller.getListOfActuatorFunctionalities();
        assertFalse(listOfActuatorFunctionalities.isEmpty());
        ActuatorFunctionalityDTO actuatorFunctionalityDTOInList = listOfActuatorFunctionalities.get(0);
        assertTrue(actuatorFunctionalityDTOInList.actuatorFunctionalityName.equals("BlindSetter") ||
                actuatorFunctionalityDTOInList.actuatorFunctionalityName.equals("DecimalSetter") ||
                actuatorFunctionalityDTOInList.actuatorFunctionalityName.equals("IntegerSetter") ||
                actuatorFunctionalityDTOInList.actuatorFunctionalityName.equals("Switch"));
    }

    /**
     * Tests the case when the services are null.
     */
    @Test
    void failGetListOfActuatorFunctionalitiesWhenServicesNull() {
        ListActuatorFunctionalitiesController controller = new ListActuatorFunctionalitiesController(null);
        List<ActuatorFunctionalityDTO> listOfActuatorFunctionalities = controller.getListOfActuatorFunctionalities();
        assertNull(listOfActuatorFunctionalities);
    }
}