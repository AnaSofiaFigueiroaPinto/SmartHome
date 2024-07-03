package smarthome.service;


import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import smarthome.domain.sensorfunctionality.SensorFunctionality;
import smarthome.domain.valueobjects.SensorFunctionalityID;

import org.junit.jupiter.api.Test;
import smarthome.persistence.repositoriesmem.SensorFunctionalityRepositoryMem;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Test cases for the {@code SensorFunctionalityService} class.
 */
@SpringBootTest(classes = {SensorFunctionalityService.class})
class SensorFunctionalityServiceTest {

    /**
     * The SensorFunctionalityRepository attribute.
     */
    @MockBean
    private SensorFunctionalityRepositoryMem sensorFunctionalityRepository;

    /**
     * The SensorFunctionalityService attribute.
     */
    @InjectMocks
    private SensorFunctionalityService sensorFunctionalityService;

    /**
     * Set up needed before each test.
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    /**
     * Successful test case for the {@code getSensorFunctionalityList} method of the {@code SensorFunctionalityService} class.
     * The test case is for the scenario where the repository returns a list of sensor functionalities.
     */
    @Test
    void successfulGetSensorFunctionalityListIsolation() {
        // Create List for the mocked repository response
        List<SensorFunctionality> mockedSensorFunctionalities = new ArrayList<>();
        //Instantiate the sensor functionalities
        SensorFunctionality sensorFunctionality = mock(SensorFunctionality.class);
        SensorFunctionality sensorFunctionality1 = mock(SensorFunctionality.class);

        //Instantiate the sensor functionality IDs and set up their behavior
        SensorFunctionalityID sensorFunctionalityID = mock(SensorFunctionalityID.class);
        SensorFunctionalityID sensorFunctionalityID1 = mock(SensorFunctionalityID.class);
        when(sensorFunctionalityID1.toString()).thenReturn("TemperatureSensor");
        when(sensorFunctionalityID.toString()).thenReturn("HumiditySensor");

        when(sensorFunctionality.identity()).thenReturn(sensorFunctionalityID);
        when(sensorFunctionality1.identity()).thenReturn(sensorFunctionalityID1);

        //addition of the sensor functionalities to the list
        mockedSensorFunctionalities.add(sensorFunctionality);
        mockedSensorFunctionalities.add(sensorFunctionality1);

        // Mock repository response
        when(sensorFunctionalityRepository.findAllEntities()).thenReturn(mockedSensorFunctionalities);

        // Call the method under test
        List<SensorFunctionalityID> result = sensorFunctionalityService.getListOfSensorFunctionalities();

        // Assert the result
        assertEquals(2, result.size());
        assertEquals(sensorFunctionalityID, result.get(0));
    }

    /**
     * Failed test case for the {@code getSensorFunctionalityList} method of the {@code SensorFunctionalityService} class.
     * The test case is for the scenario where the repository returns an empty list.
     */
    @Test
    void failedGetSensorFunctionalityListEmpty() {
        // Mock repository response
        when(sensorFunctionalityRepository.findAllEntities()).thenReturn(new ArrayList<>());

        // Assert the result
        assertEquals(0, sensorFunctionalityService.getListOfSensorFunctionalities().size());
    }

}


