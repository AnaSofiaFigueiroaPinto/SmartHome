package smarthome.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import smarthome.domain.device.Device;
import smarthome.domain.repository.DeviceRepository;
import smarthome.domain.repository.SensorFunctionalityRepository;
import smarthome.domain.repository.SensorRepository;
import smarthome.domain.sensor.FactorySensor;
import smarthome.domain.sensor.Sensor;
import smarthome.domain.valueobjects.DeviceID;
import smarthome.domain.valueobjects.SensorFunctionalityID;
import smarthome.domain.valueobjects.SensorID;
import smarthome.service.internaldto.InternalSensorDTO;
import smarthome.util.exceptions.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Test cases for the {@code SensorService} class.
 */
@SpringBootTest(classes = {SensorService.class})
class SensorServiceTest {

    /**
     * SensorRepository double to be used in the tests.
     */
    @MockBean
    private SensorRepository sensorRepoDouble;

    /**
     * DeviceRepository double to be used in the tests.
     */
    @MockBean
    private DeviceRepository deviceRepositoryDouble;

    /**
     * SensorFunctionalityRepository double to be used in the tests.
     */
    @MockBean
    private SensorFunctionalityRepository sensorFunctionalityRepositoryDouble;

    /**
     * FactorySensor double to be used in the tests.
     */
    @MockBean
    private FactorySensor factorySensorDouble;

    /**
     * {@code SensorService} class under test.
     */
    @InjectMocks
    private SensorService sensorService;

    /**
     * Method to initialize class with InjectMocks annotation before each test is run.
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Construct SensorService object using valid parameters
     */
    @Test
    void successConstructSensorService() {
        assertDoesNotThrow(() -> new SensorService(factorySensorDouble, sensorRepoDouble, deviceRepositoryDouble, sensorFunctionalityRepositoryDouble));
    }

    /**
     * Successfully create and save a valid Sensor to its Repository
     */
    @Test
    void successCreateSensorValidParameters() {
        // Create mocks for necessary entities
        Sensor sensorDouble = mock(Sensor.class);
        SensorID sensorIDDouble = mock(SensorID.class);
        SensorFunctionalityID sensorFunctionalityIDDouble = mock(SensorFunctionalityID.class);
        Device deviceDouble = mock(Device.class);
        DeviceID deviceIDDouble = mock(DeviceID.class);

        // Set up behavior for mocked entities
        when(deviceDouble.isActive()).thenReturn(true);
        when(sensorDouble.identity()).thenReturn(sensorIDDouble);
        when(sensorIDDouble.toString()).thenReturn("Sensor");

        // Set up behavior for mocked beans
        when(deviceRepositoryDouble.findEntityByID(deviceIDDouble)).thenReturn(Optional.of(deviceDouble));
        when(sensorRepoDouble.containsEntityByID(sensorIDDouble)).thenReturn(false);
        when(sensorRepoDouble.save(sensorDouble)).thenReturn(sensorDouble);
        when(sensorFunctionalityRepositoryDouble.getClassNameForSensorFunctionalityID(sensorFunctionalityIDDouble)).thenReturn("TemperatureSensor");
        when(factorySensorDouble.createSensor(sensorIDDouble, deviceIDDouble, sensorFunctionalityIDDouble, "TemperatureSensor")).thenReturn(sensorDouble);

        // Call the method under test
        SensorID createdSensor = sensorService.createSensorAndSave(deviceIDDouble, sensorFunctionalityIDDouble, sensorIDDouble);
        assertNotNull(createdSensor, "Sensor ID should not be null, indicating successful creation and saving");
    }

    /**
     * Fail to create and save a new Sensor instance due to Device not existing in Repository
     */
    @Test
    void failCreateSensorDeviceNotInRepository() {
        // Create mocks for necessary entities
        Sensor sensorDouble = mock(Sensor.class);
        SensorID sensorIDDouble = mock(SensorID.class);
        SensorFunctionalityID sensorFunctionalityIDDouble = mock(SensorFunctionalityID.class);
        Device deviceDouble = mock(Device.class);
        DeviceID deviceIDDouble = mock(DeviceID.class);

        // Set up behavior for mocked entities
        when(deviceDouble.isActive()).thenReturn(true);
        when(sensorDouble.identity()).thenReturn(sensorIDDouble);
        when(sensorIDDouble.toString()).thenReturn("Sensor");

        // Set up behavior for mocked beans
        when(deviceRepositoryDouble.findEntityByID(deviceIDDouble)).thenReturn(Optional.empty());
        when(sensorRepoDouble.containsEntityByID(sensorIDDouble)).thenReturn(false);
        when(sensorRepoDouble.save(sensorDouble)).thenReturn(sensorDouble);

        // Call the method under test
        assertThrows(DeviceNotFoundException.class, () -> sensorService.createSensorAndSave(deviceIDDouble, sensorFunctionalityIDDouble, sensorIDDouble));
    }

    /**
     * Fail to create and save a new Sensor instance due to Device being inactive
     */
    @Test
    void failCreateSensorDeviceInactive() {
        // Create mocks for necessary entities
        Sensor sensorDouble = mock(Sensor.class);
        SensorID sensorIDDouble = mock(SensorID.class);
        SensorFunctionalityID sensorFunctionalityIDDouble = mock(SensorFunctionalityID.class);
        Device deviceDouble = mock(Device.class);
        DeviceID deviceIDDouble = mock(DeviceID.class);

        // Set up behavior for mocked entities
        when(deviceDouble.isActive()).thenReturn(false);
        when(sensorDouble.identity()).thenReturn(sensorIDDouble);
        when(sensorIDDouble.toString()).thenReturn("Sensor");

        // Set up behavior for mocked beans
        when(deviceRepositoryDouble.findEntityByID(deviceIDDouble)).thenReturn(Optional.of(deviceDouble));
        when(sensorRepoDouble.containsEntityByID(sensorIDDouble)).thenReturn(false);
        when(sensorRepoDouble.save(sensorDouble)).thenReturn(sensorDouble);

        // Call the method under test
        assertThrows(DeviceInactiveException.class, () -> sensorService.createSensorAndSave(deviceIDDouble, sensorFunctionalityIDDouble, sensorIDDouble));
    }

    /**
     * Fail to create and save a new Sensor instance due to Sensor Functionality not existing
     */
    @Test
    void failCreateSensorSensorFunctionalityNotInList() {
        // Create mocks for necessary entities
        Sensor sensorDouble = mock(Sensor.class);
        SensorID sensorIDDouble = mock(SensorID.class);
        SensorFunctionalityID sensorFunctionalityIDDouble = mock(SensorFunctionalityID.class);
        Device deviceDouble = mock(Device.class);
        DeviceID deviceIDDouble = mock(DeviceID.class);

        // Set up behavior for mocked entities
        when(deviceDouble.isActive()).thenReturn(true);
        when(sensorDouble.identity()).thenReturn(sensorIDDouble);
        when(sensorIDDouble.toString()).thenReturn("Sensor");

        // Set up behavior for mocked beans
        when(deviceRepositoryDouble.findEntityByID(deviceIDDouble)).thenReturn(Optional.of(deviceDouble));
        when(sensorRepoDouble.containsEntityByID(sensorIDDouble)).thenReturn(false);
        when(sensorRepoDouble.save(sensorDouble)).thenReturn(sensorDouble);
        when(sensorFunctionalityRepositoryDouble.getClassNameForSensorFunctionalityID(sensorFunctionalityIDDouble)).thenReturn(null);

        // Call the method under test
        assertThrows(SensorFunctionalityNotListedException.class, () -> sensorService.createSensorAndSave(deviceIDDouble, sensorFunctionalityIDDouble, sensorIDDouble));
    }

    /**
     * Fail to create and save a new Sensor instance due to Sensor already existing in repo
     */
    @Test
    void failCreateSensorSensorAlreadyExists() {
        // Create mocks for necessary entities
        Sensor sensorDouble = mock(Sensor.class);
        SensorID sensorIDDouble = mock(SensorID.class);
        SensorFunctionalityID sensorFunctionalityIDDouble = mock(SensorFunctionalityID.class);
        Device deviceDouble = mock(Device.class);
        DeviceID deviceIDDouble = mock(DeviceID.class);

        // Set up behavior for mocked entities
        when(deviceDouble.isActive()).thenReturn(true);
        when(sensorDouble.identity()).thenReturn(sensorIDDouble);
        when(sensorIDDouble.toString()).thenReturn("Sensor");

        // Set up behavior for mocked beans
        when(deviceRepositoryDouble.findEntityByID(deviceIDDouble)).thenReturn(Optional.of(deviceDouble));
        when(sensorRepoDouble.containsEntityByID(sensorIDDouble)).thenReturn(true);
        when(sensorRepoDouble.save(sensorDouble)).thenReturn(sensorDouble);
        when(sensorFunctionalityRepositoryDouble.getClassNameForSensorFunctionalityID(sensorFunctionalityIDDouble)).thenReturn("TemperatureSensor");
        when(factorySensorDouble.createSensor(sensorIDDouble, deviceIDDouble, sensorFunctionalityIDDouble, "TemperatureSensor")).thenReturn(sensorDouble);

        // Call the method under test
        assertThrows(SensorAlreadyExistsException.class, () -> sensorService.createSensorAndSave(deviceIDDouble, sensorFunctionalityIDDouble, sensorIDDouble));
    }

    /**
     * Successful find of a list with only one sensor with temperature functionality object in a device object
     * Returns a list of SensorID objects
     */
    @Test
    void successfulFindTemperatureSensorOfADevice(){
        // Create mocks for necessary entities
        Sensor sensorDouble = mock(Sensor.class);
        SensorID sensorIDDouble = mock(SensorID.class);
        SensorFunctionalityID sensorFunctionalityIDDouble = mock(SensorFunctionalityID.class);
        SensorFunctionalityID sensorFunctionalityIDDouble2 = mock(SensorFunctionalityID.class);
        DeviceID deviceIDDouble = mock(DeviceID.class);

        // Set up behavior for mocked entities
        when(deviceIDDouble.toString()).thenReturn("Thermostat");

        when(sensorIDDouble.toString()).thenReturn("SensorTempBC253");
        when(sensorDouble.identity()).thenReturn(sensorIDDouble);
        when(sensorDouble.getDeviceID()).thenReturn(deviceIDDouble);

        when(sensorFunctionalityIDDouble.toString()).thenReturn("TemperatureCelsius");
        when(sensorFunctionalityIDDouble2.toString()).thenReturn("TemperatureCelsius");
        when(sensorDouble.getSensorFunctionalityID()).thenReturn(sensorFunctionalityIDDouble2);

        // Create a list of sensors and add the mocked sensor
        List<Sensor> sensorList = new ArrayList<>();
        sensorList.add(sensorDouble);

        // Mock the behavior of findByDeviceIDAndSensorFunctionality to return the list of sensors
        when(sensorRepoDouble.findByDeviceIDAndSensorFunctionality(deviceIDDouble, sensorFunctionalityIDDouble)).thenReturn(sensorList);

        // Invoke the method
        List<SensorID> result = sensorService.findSensorsIDOfADeviceBySensorFunctionality(deviceIDDouble, sensorFunctionalityIDDouble);

        // Assert the result
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("SensorTempBC253", result.get(0).toString());
        assertEquals("TemperatureCelsius", sensorDouble.getSensorFunctionalityID().toString());
        assertEquals("Thermostat", sensorDouble.getDeviceID().toString());
    }


    /**
     * Successful find of a list of sensors with temperature functionality objects of in a device object
     * Returns a list of SensorID objects
     */
    @Test
    void successfulFindListOfTemperatureSensorOfADevice(){
        // Create mocks for necessary entities
        Sensor sensorDouble = mock(Sensor.class);
        Sensor sensorDouble1 = mock(Sensor.class);
        SensorID sensorIDDouble = mock(SensorID.class);
        SensorID sensorIDDouble1 = mock(SensorID.class);
        SensorFunctionalityID sensorFunctionalityIDDouble = mock(SensorFunctionalityID.class);
        SensorFunctionalityID sensorFunctionalityIDDouble1 = mock(SensorFunctionalityID.class);
        DeviceID deviceIDDouble = mock(DeviceID.class);

        // Set up behavior for mocked entities
        when(deviceIDDouble.toString()).thenReturn("Thermostat");

        when(sensorIDDouble.toString()).thenReturn("SensorTempBC253");
        when(sensorIDDouble1.toString()).thenReturn("SensorTempJF455");

        when(sensorDouble.identity()).thenReturn(sensorIDDouble);
        when(sensorDouble.getDeviceID()).thenReturn(deviceIDDouble);

        when(sensorDouble1.identity()).thenReturn(sensorIDDouble1);
        when(sensorDouble1.getDeviceID()).thenReturn(deviceIDDouble);

        when(sensorFunctionalityIDDouble.toString()).thenReturn("TemperatureCelsius");
        when(sensorFunctionalityIDDouble1.toString()).thenReturn("TemperatureCelsius");
        when(sensorDouble.getSensorFunctionalityID()).thenReturn(sensorFunctionalityIDDouble);
        when(sensorDouble1.getSensorFunctionalityID()).thenReturn(sensorFunctionalityIDDouble1);

        // Create a list of sensors and add the mocked sensor
        List<Sensor> sensorList = new ArrayList<>();
        sensorList.add(sensorDouble);
        sensorList.add(sensorDouble1);

        // Mock the behavior of findByDeviceIDAndSensorFunctionality to return the list of sensors
        when(sensorRepoDouble.findByDeviceIDAndSensorFunctionality(deviceIDDouble, sensorFunctionalityIDDouble)).thenReturn(sensorList);

        // Invoke the method
        List<SensorID> result = sensorService.findSensorsIDOfADeviceBySensorFunctionality(deviceIDDouble, sensorFunctionalityIDDouble);

        // Assert the result
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("SensorTempBC253", result.get(0).toString());
        assertEquals("TemperatureCelsius", sensorDouble.getSensorFunctionalityID().toString());
        assertEquals("Thermostat", sensorDouble.getDeviceID().toString());
        assertEquals("SensorTempJF455", result.get(1).toString());
        assertEquals("TemperatureCelsius", sensorDouble1.getSensorFunctionalityID().toString());
        assertEquals("Thermostat", sensorDouble1.getDeviceID().toString());
    }

    /**
     * Fail to find a list of sensors with temperature functionality objects of in a device object
     * Returns an empty list of SensorID objects, since the test adds a sensor with a different functionality
     */
    @Test
    void failedFindTemperatureSensorOfADevice(){
        // Create mocks for necessary entities
        Sensor sensorDouble = mock(Sensor.class);
        SensorID sensorIDDouble = mock(SensorID.class);
        SensorFunctionalityID sensorFunctionalityIDDouble = mock(SensorFunctionalityID.class);
        SensorFunctionalityID sensorFunctionalityIDDouble1 = mock(SensorFunctionalityID.class);
        Device deviceDouble = mock(Device.class);
        DeviceID deviceIDDouble = mock(DeviceID.class);

        // Set up behavior for mocked entities
        when(deviceDouble.toString()).thenReturn("Thermostat");

        when(sensorIDDouble.toString()).thenReturn("SensorHumidityBC253");
        when(sensorDouble.identity()).thenReturn(sensorIDDouble);
        when(sensorDouble.getDeviceID()).thenReturn(deviceIDDouble);

        when(sensorFunctionalityIDDouble.toString()).thenReturn("HumidityPercentage");
        when(sensorFunctionalityIDDouble1.toString()).thenReturn("TemperatureCelsius");
        when(sensorDouble.getSensorFunctionalityID()).thenReturn(sensorFunctionalityIDDouble);

        // Create a list of sensors and add the mocked sensor
        List<Sensor> sensorList = new ArrayList<>();
        sensorList.add(sensorDouble);

        // Mock the behavior of findByDeviceID to return the list of sensors
        when(sensorRepoDouble.findByDeviceID(deviceIDDouble)).thenReturn(sensorList);

        // Invoke the method
        List<SensorID> result = sensorService.findSensorsIDOfADeviceBySensorFunctionality(deviceIDDouble, sensorFunctionalityIDDouble1);

        // Assert the result
        assertNotNull(result);
        assertEquals(0, result.size());
    }

    /**
     * Successful find with multiple sensors in a device object.
     * Returns a list of SensorID objects.
     */
    @Test
    void successfulFindMultipleSensorsOfADevice() {
        // Create mocks for necessary entities
        DeviceID deviceID = mock(DeviceID.class);
        Sensor sensor1 = mock(Sensor.class);
        SensorID sensorID1 = mock(SensorID.class);
        Sensor sensor2 = mock(Sensor.class);
        SensorID sensorID2 = mock(SensorID.class);

        // Set up behavior for mocked entities
        when(sensor1.identity()).thenReturn(sensorID1);
        when(sensor2.identity()).thenReturn(sensorID2);
        when(sensorRepoDouble.findByDeviceID(deviceID)).thenReturn(List.of(sensor1, sensor2));

        // Invoke the method
        List<SensorID> result = sensorService.findSensorsIDOfADevice(deviceID);

        // Assert the result
        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.contains(sensorID1));
        assertTrue(result.contains(sensorID2));
    }

    /**
     * No sensors found for a device object.
     * Returns an empty list.
     * Same test conditions for non existent device.
     */
    @Test
    void failedToFindSensorsInADevice() {
        // Create mocks for necessary entities
        DeviceID deviceID = mock(DeviceID.class);

        // Set up behavior for mocked entities
        when(sensorRepoDouble.findByDeviceID(deviceID)).thenReturn(Collections.emptyList());

        // Invoke the method
        List<SensorID> result = sensorService.findSensorsIDOfADevice(deviceID);

        // Assert the result
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    /**
     * Tests the successful retrieval of SensorID information.
     * This test mocks the SensorID and Sensor objects, and verifies that the findSensorIDInfo method
     * returns a non-null InternalSensorDTO when the sensor is found in the repository.
     */
    @Test
    void successfullyFindSensorIDInfo() {
        SensorID sensorID = mock(SensorID.class);
        Sensor sensor = mock(Sensor.class);

        when(sensorRepoDouble.findEntityByID(sensorID)).thenReturn(Optional.of(sensor));

        InternalSensorDTO result = sensorService.findSensorIDInfo(sensorID);

        assertNotNull(result);
    }

    /**
     * Tests the scenario where the sensor is not found in the repository.
     * This test verifies that the findSensorIDInfo method throws a SensorNotFoundException when the sensor is not found.
     */
    @Test
    void failToFindSensorIDInfoSensorNotFound() {
        SensorID sensorID = new SensorID("NonExistentSensor");
        when(sensorRepoDouble.findEntityByID(sensorID)).thenReturn(Optional.empty());

        assertThrows(SensorNotFoundException.class, () -> sensorService.findSensorIDInfo(sensorID));
    }
}