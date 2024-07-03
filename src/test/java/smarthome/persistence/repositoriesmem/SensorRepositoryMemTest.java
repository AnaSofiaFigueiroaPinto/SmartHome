package smarthome.persistence.repositoriesmem;

import smarthome.domain.sensor.Sensor;
import smarthome.domain.valueobjects.DeviceID;
import smarthome.domain.valueobjects.SensorFunctionalityID;
import smarthome.domain.valueobjects.SensorID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SensorRepositoryMemTest {
    private SensorID sensorID1 = mock(SensorID.class);
    private SensorID sensorID2 = mock(SensorID.class);
    private SensorID sensorID3 = mock(SensorID.class);
    private Sensor sensorEnt1 = mock(Sensor.class);
    private Sensor sensorEnt2 = mock(Sensor.class);
    private Sensor sensorEnt3 = mock(Sensor.class);

    private Map<SensorID, Sensor> map = new HashMap<>();

    /**
     * Create map filled with data to be used as data source for repository
     */
    void fillMapData () {
        map.put(sensorID1, sensorEnt1);
        map.put(sensorID2, sensorEnt2);
    }

    /**
     * Fill up the map to be used as double of data loaded into repository before each test
     */
    @BeforeEach
    void setUP () {
        fillMapData();
    }

    /**
     * Test the successful addition of a Sensor entity to the repository
     */
    @Test
    void successAddEntityToMap () {
        when(sensorEnt3.identity()).thenReturn(sensorID3);
        SensorRepositoryMem sensorRepositoryMem = new SensorRepositoryMem(map);

        sensorRepositoryMem.save(sensorEnt3);

        assertTrue(sensorRepositoryMem.containsEntityByID(sensorID3));
    }

    /**
     * Successful retrieval of all entities in the repository
     */
    @Test
    void successRetrieveAllEntitiesInData () {
        SensorRepositoryMem sensorRepositoryMem = new SensorRepositoryMem(map);

        assertEquals(sensorRepositoryMem.findAllEntities(), map.values());
    }

    /**
     * Successfully retrieve a Sensor entity that based on its ID
     */
    @Test
    void successFindSensorWithSpecificId () {
        SensorRepositoryMem sensorRepositoryMem = new SensorRepositoryMem(map);

        Optional<Sensor> retrievedSensor = sensorRepositoryMem.findEntityByID(sensorID1);
        Sensor sensor = retrievedSensor.get();

        assertEquals(sensor, sensorEnt1);
    }

    /**
     * Fail to retrieve a Sensor entity with an ID not present in the repository
     */
    @Test
    void failFindSensorWithSpecificId () {
        SensorRepositoryMem sensorRepositoryMem = new SensorRepositoryMem(map);
        SensorID nonExistentID = mock(SensorID.class);

        Optional<Sensor> retrievedSensor = sensorRepositoryMem.findEntityByID(nonExistentID);

        assertThrows(NoSuchElementException.class, retrievedSensor::get);
    }

    /**
     * Successful find of a Sensor entity in the repo based in a given ID
     */
    @Test
    void successContainsEntity () {
        SensorRepositoryMem sensorRepositoryMem = new SensorRepositoryMem(map);

        assertTrue(sensorRepositoryMem.containsEntityByID(sensorID1));
    }

    /**
     * Fail to find a Sensor entity in the repository based on a given ID
     */
    @Test
    void failContainsEntity () {
        SensorRepositoryMem sensorRepositoryMem = new SensorRepositoryMem(map);
        SensorID nonExistentID = mock(SensorID.class);
        assertFalse(sensorRepositoryMem.containsEntityByID(nonExistentID));
    }

    /**
     * Successful findByDeviceID method to successfully retrieve all sensors of a given device
     */
    @Test
    void successFindByDeviceID () {
        //Mocking the deviceID
        DeviceID deviceID = mock(DeviceID.class);
        when(deviceID.toString()).thenReturn("deviceID");
        when(sensorEnt1.getDeviceID()).thenReturn(deviceID);
        when(sensorEnt2.getDeviceID()).thenReturn(deviceID);

        //Repository instantiation and expected sensors
        SensorRepositoryMem sensorRepositoryMem = new SensorRepositoryMem(map);
        List<Sensor> expectedSensors = new ArrayList<>(map.values());

        //Call to the method to be tested and actual sensors
        Iterable<Sensor> sensors = sensorRepositoryMem.findByDeviceID(deviceID);

        List<Sensor> actualSensors = new ArrayList<>();
        sensors.forEach(actualSensors::add);

        assertEquals(expectedSensors, actualSensors, "The lists should be equal.");
    }

    /**
     * Failed findByDeviceID method to retrieve all sensors of a given device
     */
    @Test
    void failFindByDeviceID () {
        //Mocking the deviceID
        DeviceID deviceID = mock(DeviceID.class);
        when(deviceID.toString()).thenReturn("deviceID");
        DeviceID deviceID1 = mock(DeviceID.class);
        when(deviceID1.toString()).thenReturn("deviceID1");
        when(sensorEnt1.getDeviceID()).thenReturn(deviceID1);
        when(sensorEnt2.getDeviceID()).thenReturn(deviceID1);

        //Repository instantiation
        SensorRepositoryMem sensorRepositoryMem = new SensorRepositoryMem(map);

        //Call to the method to be tested and actual sensors
        Iterable<Sensor> sensors = sensorRepositoryMem.findByDeviceID(deviceID);

        List<Sensor> actualSensors = new ArrayList<>();
        sensors.forEach(actualSensors::add);

        assertTrue(actualSensors.isEmpty(), "The actual sensors list should be empty.");
    }

    /**
     * Successful findBySensorFunctionalityID method to successfully retrieve all sensors of a given device
     */
    @Test
    void successFindBySensorFunctionalityID () {
        //Mocking the SensorFunctionalityID
        SensorFunctionalityID sensorFunctionalityID = mock(SensorFunctionalityID.class);
        when(sensorFunctionalityID.toString()).thenReturn("TemperatureCelsius");
        when(sensorEnt1.getSensorFunctionalityID()).thenReturn(sensorFunctionalityID);
        when(sensorEnt2.getSensorFunctionalityID()).thenReturn(sensorFunctionalityID);

        //Repository instantiation and expected sensors
        SensorRepositoryMem sensorRepositoryMem = new SensorRepositoryMem(map);
        List<Sensor> expectedSensors = new ArrayList<>(map.values());

        //Call to the method to be tested and actual sensors
        Iterable<Sensor> sensors = sensorRepositoryMem.findBySensorFunctionality(sensorFunctionalityID);

        List<Sensor> actualSensors = new ArrayList<>();
        sensors.forEach(actualSensors::add);

        assertEquals(expectedSensors, actualSensors, "The lists should be equal.");
    }

    /**
     * Failed findBySensorFunctionalityID method to retrieve all sensors of a given device
     */
    @Test
    void failFindBySensorFunctionalityID () {
        //Mocking the SensorFunctionalityID
        SensorFunctionalityID sensorFunctionalityID = mock(SensorFunctionalityID.class);
        SensorFunctionalityID sensorFunctionalityIDNotPresent = mock(SensorFunctionalityID.class);
        when(sensorFunctionalityID.toString()).thenReturn("TemperatureCelsius");
        when(sensorFunctionalityIDNotPresent.toString()).thenReturn("Sunrise");
        when(sensorEnt1.getSensorFunctionalityID()).thenReturn(sensorFunctionalityID);
        when(sensorEnt2.getSensorFunctionalityID()).thenReturn(sensorFunctionalityID);

        //Repository instantiation
        SensorRepositoryMem sensorRepositoryMem = new SensorRepositoryMem(map);

        //Call to the method to be tested and actual sensors
        Iterable<Sensor> sensors = sensorRepositoryMem.findBySensorFunctionality(sensorFunctionalityIDNotPresent);

        List<Sensor> actualSensors = new ArrayList<>();
        sensors.forEach(actualSensors::add);

        assertTrue(actualSensors.isEmpty(), "The actual sensors list should be empty.");
    }

    /**
     * Successful findByDeviceIDAndSensorFunctionality method to successfully retrieve all sensors of a given device and functionality
     */
    @Test
    void successFindByDeviceIDAndSensorFunctionality() {
        // Mocking the DeviceID and SensorFunctionalityID
        DeviceID deviceID = mock(DeviceID.class);
        SensorFunctionalityID sensorFunctionalityID = mock(SensorFunctionalityID.class);
        when(deviceID.toString()).thenReturn("Device1");
        when(sensorFunctionalityID.toString()).thenReturn("TemperatureCelsius");

        // Mocking sensor entities to return the expected DeviceID and SensorFunctionalityID
        when(sensorEnt1.getDeviceID()).thenReturn(deviceID);
        when(sensorEnt1.getSensorFunctionalityID()).thenReturn(sensorFunctionalityID);
        when(sensorEnt2.getDeviceID()).thenReturn(deviceID);
        when(sensorEnt2.getSensorFunctionalityID()).thenReturn(sensorFunctionalityID);

        // Repository instantiation and expected sensors
        SensorRepositoryMem sensorRepositoryMem = new SensorRepositoryMem(map);
        List<Sensor> expectedSensors = new ArrayList<>(map.values());

        // Call to the method to be tested and actual sensors
        Iterable<Sensor> sensors = sensorRepositoryMem.findByDeviceIDAndSensorFunctionality(deviceID, sensorFunctionalityID);

        List<Sensor> actualSensors = new ArrayList<>();
        sensors.forEach(actualSensors::add);

        assertEquals(expectedSensors, actualSensors, "The lists should be equal.");
    }

    /**
     * Failed findByDeviceIDAndSensorFunctionality method to retrieve all sensors of a given device and functionality
     */
    @Test
    void failFindByDeviceIDAndSensorFunctionality() {
        // Mocking the DeviceID and SensorFunctionalityID
        DeviceID deviceID = mock(DeviceID.class);
        DeviceID deviceIDNotPresent = mock(DeviceID.class);
        SensorFunctionalityID sensorFunctionalityID = mock(SensorFunctionalityID.class);
        when(deviceID.toString()).thenReturn("Device1");
        when(deviceIDNotPresent.toString()).thenReturn("Device2");
        when(sensorFunctionalityID.toString()).thenReturn("TemperatureCelsius");

        // Mocking sensor entities to return the expected DeviceID and SensorFunctionalityID
        when(sensorEnt1.getDeviceID()).thenReturn(deviceID);
        when(sensorEnt1.getSensorFunctionalityID()).thenReturn(sensorFunctionalityID);
        when(sensorEnt2.getDeviceID()).thenReturn(deviceID);
        when(sensorEnt2.getSensorFunctionalityID()).thenReturn(sensorFunctionalityID);

        // Repository instantiation
        SensorRepositoryMem sensorRepositoryMem = new SensorRepositoryMem(map);

        // Call to the method to be tested with a DeviceID that has no matching sensors
        Iterable<Sensor> sensors = sensorRepositoryMem.findByDeviceIDAndSensorFunctionality(deviceIDNotPresent, sensorFunctionalityID);

        List<Sensor> actualSensors = new ArrayList<>();
        sensors.forEach(actualSensors::add);

        assertTrue(actualSensors.isEmpty(), "The actual sensors list should be empty.");
    }
}