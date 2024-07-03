package smarthome.persistence.springdata.repositoriesspringdata;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import smarthome.domain.sensor.FactorySensor;
import smarthome.domain.sensor.Sensor;
import smarthome.domain.valueobjects.DeviceID;
import smarthome.domain.valueobjects.SensorFunctionalityID;
import smarthome.domain.valueobjects.SensorID;
import smarthome.persistence.jpa.datamodel.MapperSensorDataModel;
import smarthome.persistence.jpa.datamodel.SensorDataModel;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Test class for the implementation of SensorRepositorySpringDataImp.
 */
@SpringBootTest (classes = {SensorRepositorySpringDataImp.class})
class SensorRepositorySpringDataImpTest {

    /**
     * Mocked bean of SensorRepositorySpringData
     * to be injected into the class under test (class with @InjectMocks annotation).
     */
    @MockBean
    private SensorRepositorySpringData sensorRepositorySpringData;

    /**
     * Mocked bean of FactorySensor
     * to be injected into the class under test (class with @InjectMocks annotation).
     */
    @MockBean
    private FactorySensor factorySensor;

    /**
     * Mocked bean of MapperSensorDataModel
     * to be injected into the class under test (class with @InjectMocks annotation).
     */
    @MockBean
    private MapperSensorDataModel mapperSensorDataModel;

    /**
     * Class under test where mocked beans (attributes of the class) should be injected by Spring into.
     */
    @InjectMocks
    private SensorRepositorySpringDataImp sensorRepository;

    /**
     * Method to initialize class with InjectMocks annotation before each test is run.
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Tests the behavior of the {@code save} method when provided with a valid sensor entity.
     */
    @Test
    void testSaveSensor() {
        //Create doubles to be used by method under test
        Sensor sensorDouble = mock(Sensor.class);
        SensorID sensorIDDouble = mock(SensorID.class);
        SensorFunctionalityID sensorFunctionalityIDDouble = mock(SensorFunctionalityID.class);
        DeviceID deviceIDDouble = mock(DeviceID.class);

        //Set behaviour of mocked entities
        when(sensorDouble.identity()).thenReturn(sensorIDDouble);
        when(sensorDouble.getSensorFunctionalityID()).thenReturn(sensorFunctionalityIDDouble);
        when(sensorDouble.getDeviceID()).thenReturn(deviceIDDouble);

        // Invoke the method under test
        Sensor result = sensorRepository.save(sensorDouble);
        assertNotNull(result);
    }

    /**
     * Tests the behavior of the {@code save} method when provided with a null sensor entity.
     */
    @Test
    void testFailSaveNullSensor() {
        assertThrows(IllegalArgumentException.class, () -> sensorRepository.save(null));
    }

    /**
     * Tests the {@code findAllEntities} method to ensure it retrieves all sensor entities.
     */
    @Test
    void testFindAllEntities() {
        //Create doubles to be used by method under test
        SensorDataModel sensorDataModelDouble = mock(SensorDataModel.class);
        Sensor sensorDouble = mock(Sensor.class);

        //Set behaviour of mocked beans
        when(sensorRepositorySpringData.findAll()).thenReturn(List.of(sensorDataModelDouble));
        when(mapperSensorDataModel.toDomainList(factorySensor,List.of(sensorDataModelDouble))).thenReturn(List.of(sensorDouble));

        // Invoke the method under test
        Iterable<Sensor> result = sensorRepository.findAllEntities();
        assertEquals(1, ((List<Sensor>) result).size());
    }

    /**
     * Tests the {@code findEntityByID} method to ensure it retrieves a sensor entity by its ID.
     */
    @Test
    void testFindSensorBySensorId() {
        //Create doubles to be used by method under test
        SensorID sensorIDDouble = mock(SensorID.class);
        SensorDataModel sensorDataModelDouble = mock(SensorDataModel.class);
        Sensor sensorDouble = mock(Sensor.class);

        //Set behaviour of mocked entity
        when(sensorIDDouble.toString()).thenReturn("test");

        //Set behaviour of mocked beans
        when(sensorRepositorySpringData.findById(sensorIDDouble.toString())).thenReturn(Optional.ofNullable(sensorDataModelDouble));
        when(mapperSensorDataModel.toDomain(factorySensor, sensorDataModelDouble)).thenReturn(sensorDouble);

        // Invoke the method under test
        Optional<Sensor> result = sensorRepository.findEntityByID(sensorIDDouble);
        assertTrue(result.isPresent());
    }

    /**
     * Tests the {@code findEntityByID} method when failing to find a sensor entity by its ID.
     */
    @Test
    void testFailToFindSensorBySensorId() {
        //Create double to be used by method under test
        SensorID sensorIDDouble = mock(SensorID.class);

        //Set behaviour of mocked entity
        when(sensorIDDouble.toString()).thenReturn("fail");

        //Set behaviour of mocked beans
        when(sensorRepositorySpringData.findById(sensorIDDouble.toString())).thenReturn(Optional.empty());

        // Invoke the method under test
        Optional<Sensor> result = sensorRepository.findEntityByID(sensorIDDouble);
        assertTrue(result.isEmpty());
    }

    /**
     * Tests the {@code containsEntityByID} method to ensure it correctly identifies if a sensor entity exists by its ID.
     */
    @Test
    void testContainsEntityByID() {
        //Create double to be used by method under test
        SensorID sensorIDDouble = mock(SensorID.class);

        //Set behaviour of mocked entity
        when(sensorIDDouble.toString()).thenReturn("test");

        //Set behaviour of mocked beans
        when(sensorRepositorySpringData.existsById(sensorIDDouble.toString())).thenReturn(true);

        // Invoke the method under test
        assertTrue(sensorRepository.containsEntityByID(sensorIDDouble));
    }

    /**
     * Tests the {@code containsEntityByID} method to ensure it correctly identifies if a sensor entity does not exist by its ID.
     */
    @Test
    void testDoesntContainEntityByID() {
        //Create double to be used by method under test
        SensorID sensorID = mock(SensorID.class);

        //Set behaviour of mocked entity
        when(sensorID.toString()).thenReturn("fail");

        //Set behaviour of mocked beans
        when(sensorRepositorySpringData.existsById(sensorID.toString())).thenReturn(false);

        // Invoke the method under test
        assertFalse(sensorRepository.containsEntityByID(sensorID));
    }

    /**
     * Tests the {@code findByDeviceID} method to find sensors by their device ID.
     */
    @Test
    void testFindSensorByDeviceId() {
        //Create doubles to be used by method under test
        DeviceID deviceID = mock(DeviceID.class);
        SensorDataModel sensorDataModel = mock(SensorDataModel.class);
        Sensor sensor = mock(Sensor.class);

        //Set behaviour of mocked entities
        when(deviceID.toString()).thenReturn("test");
        when(sensorDataModel.getDeviceID()).thenReturn("test");

        //Set behaviour of mocked beans
        when(sensorRepositorySpringData.findByDeviceID(deviceID.toString())).thenReturn(List.of(sensorDataModel));
        when(mapperSensorDataModel.toDomainList(factorySensor,List.of(sensorDataModel))).thenReturn(List.of(sensor));

        // Invoke the method under test
        Iterable<Sensor> result = sensorRepository.findByDeviceID(deviceID);
        assertEquals(1, ((List<Sensor>) result).size());
    }

    /**
     * Tests the {@code findBySensorFunctionalityID} method to find sensors by their Sensor Functionality.
     */
    @Test
    void testFindSensorFunctionalityID() {
        //Create doubles to be used by method under test
        SensorFunctionalityID sensorFunctionalityID = mock(SensorFunctionalityID.class);
        SensorDataModel sensorDataModel = mock(SensorDataModel.class);
        Sensor sensor = mock(Sensor.class);

        //Set behaviour of mocked entities
        when(sensorFunctionalityID.toString()).thenReturn("TemperatureCelsius");
        when(sensorDataModel.getSensorFunctionalityID()).thenReturn("TemperatureCelsius");

        //Set behaviour of mocked beans
        when(sensorRepositorySpringData.findBySensorFunctionalityID(sensorFunctionalityID.toString())).thenReturn(List.of(sensorDataModel));
        when(mapperSensorDataModel.toDomainList(factorySensor,List.of(sensorDataModel))).thenReturn(List.of(sensor));

        // Invoke the method under test
        Iterable<Sensor> result = sensorRepository.findBySensorFunctionality(sensorFunctionalityID);
        assertEquals(1, ((List<Sensor>) result).size());
    }

    /**
     * Tests the {@code findByDeviceIDAndSensorFunctionality} method to find sensors by their Device ID and Sensor Functionality.
     */
    @Test
    void testFindByDeviceIDAndSensorFunctionality() {
        // Create doubles to be used by method under test
        DeviceID deviceID = mock(DeviceID.class);
        SensorFunctionalityID sensorFunctionalityID = mock(SensorFunctionalityID.class);
        SensorDataModel sensorDataModel = mock(SensorDataModel.class);
        Sensor sensor = mock(Sensor.class);

        // Set behavior of mocked entities
        when(deviceID.toString()).thenReturn("Device1");
        when(sensorFunctionalityID.toString()).thenReturn("TemperatureCelsius");
        when(sensorDataModel.getDeviceID()).thenReturn("Device1");
        when(sensorDataModel.getSensorFunctionalityID()).thenReturn("TemperatureCelsius");

        // Set behavior of mocked beans
        when(sensorRepositorySpringData.findByDeviceIDAndSensorFunctionalityID(deviceID.toString(), sensorFunctionalityID.toString())).thenReturn(List.of(sensorDataModel));
        when(mapperSensorDataModel.toDomainList(factorySensor, List.of(sensorDataModel))).thenReturn(List.of(sensor));

        // Invoke the method under test
        Iterable<Sensor> result = sensorRepository.findByDeviceIDAndSensorFunctionality(deviceID, sensorFunctionalityID);
        assertEquals(1, ((List<Sensor>) result).size());
    }
}