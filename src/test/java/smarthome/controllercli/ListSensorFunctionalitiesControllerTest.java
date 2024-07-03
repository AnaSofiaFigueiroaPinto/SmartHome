package smarthome.controllercli;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import smarthome.domain.sensorfunctionality.FactorySensorFunctionality;
import smarthome.domain.sensorfunctionality.ImpFactorySensorFunctionality;
import smarthome.mapper.SensorFunctionalityDTO;
import smarthome.persistence.repositoriesmem.SensorFunctionalityRepositoryMem;
import smarthome.service.SensorFunctionalityService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ListSensorFunctionalitiesControllerTest {

    private SensorFunctionalityService sensorFunctionalityService;

    @BeforeEach
    void setUp() {
        FactorySensorFunctionality factorySensorFunctionality = new ImpFactorySensorFunctionality();
        SensorFunctionalityRepositoryMem sensorFunctionalityRepositoryMem = new SensorFunctionalityRepositoryMem(factorySensorFunctionality);
        sensorFunctionalityService = new SensorFunctionalityService(sensorFunctionalityRepositoryMem);
    }

    @Test
    void successGetListOfSensorFunctionalities() {
        ListSensorFunctionalitiesController controller = new ListSensorFunctionalitiesController(sensorFunctionalityService);
        List<SensorFunctionalityDTO> listOfSensorFunctionalities = controller.getListOfSensorFunctionalities();

        assertFalse(listOfSensorFunctionalities.isEmpty());
        assertFalse(listOfSensorFunctionalities.get(0).sensorFunctionalityName.isBlank());
    }

    @Test
    void failGetListOfSensorFunctionalitiesWhenServicesNull() {
        ListSensorFunctionalitiesController controller = new ListSensorFunctionalitiesController(null);
        List<SensorFunctionalityDTO> listOfSensorFunctionalities = controller.getListOfSensorFunctionalities();
        assertNull(listOfSensorFunctionalities);
    }
}