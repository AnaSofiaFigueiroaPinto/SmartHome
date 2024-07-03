package smarthome.domain.sensor;

import org.springframework.boot.test.context.SpringBootTest;
import smarthome.domain.valueobjects.DeviceID;
import smarthome.domain.valueobjects.SensorFunctionalityID;
import smarthome.domain.valueobjects.SensorID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = {FactorySensor.class})
class FactorySensorTest {
    private FactorySensor factorySensor;

    /**
     * Setting up the sensor Catalogue under test.
     */
    @BeforeEach
    void setUp() {
        factorySensor = new FactorySensor();
    }


    /**
     * Test creation of a sensor using a functionality ID that is null
     */
    @Test
    void failCreateSensorInvalidSensorFunctionalityID() {
        // Define test data
        String sensorName = "Temperature_BOSCH_1";
        String validSensorClass = "smarthome.domain.sensor.TemperatureCelsiusSensor";

        // Create mocks for necessary entities
        Sensor sensorDouble = mock(Sensor.class);
        SensorID sensorIDDouble = mock(SensorID.class);
        DeviceID validDeviceIDDouble = mock(DeviceID.class);

        // Set up behavior for mocked entities
        when(sensorDouble.identity()).thenReturn(sensorIDDouble);
        when(sensorIDDouble.toString()).thenReturn(sensorName);

        // Call the method under test
        assertNull(factorySensor.createSensor(sensorIDDouble, validDeviceIDDouble, null, validSensorClass)
                , "Creation of sensor with null SensorFunctionalityID should return null");
    }

    /**
     * Test creation of a sensor using a null string for sensorName
     */
    @Test
    void failCreateSensorUsingNullSensorID() {
        // Define test data
        String validSensorClass = "smarthome.domain.sensor.TemperatureCelsiusSensor";

        // Create mocks for necessary entities
        DeviceID validDeviceIDDouble = mock(DeviceID.class);
        SensorFunctionalityID validSensorFuncIDDouble = mock(SensorFunctionalityID.class);

        // Call the method under test
        assertNull(factorySensor.createSensor(null, validDeviceIDDouble, validSensorFuncIDDouble, validSensorClass)
                , "Creation of sensor with null sensorName should return null");
    }

    /**
     * Test creation of a sensor using a null deviceID.
     */
    @Test
    void failCreateSensorNullDeviceID() {
        // Define test data
        String sensorName = "Temperature_BOSCH_1";
        String validSensorClass = "smarthome.domain.sensor.TemperatureCelsiusSensor";

        // Create mocks for necessary entities
        Sensor sensorDouble = mock(Sensor.class);
        SensorID sensorIDDouble = mock(SensorID.class);
        SensorFunctionalityID validSensorFuncIDDouble = mock(SensorFunctionalityID.class);

        // Set up behavior for mocked entities
        when(sensorDouble.identity()).thenReturn(sensorIDDouble);
        when(sensorIDDouble.toString()).thenReturn(sensorName);

        // Call the method under test
        assertNull(factorySensor.createSensor(sensorIDDouble, null, validSensorFuncIDDouble, validSensorClass)
                , "Creation of sensor with null deviceID should return null");
    }

    @Test
    void failCreateSensorClassStringNull() {
        // Define test data
        String sensorName = "Temperature_BOSCH_1";

        // Create mocks for necessary entities
        Sensor sensorDouble = mock(Sensor.class);
        SensorID sensorIDDouble = mock(SensorID.class);
        SensorFunctionalityID validSensorFuncIDDouble = mock(SensorFunctionalityID.class);
        DeviceID validDeviceIDDouble = mock(DeviceID.class);

        // Set up behavior for mocked entities
        when(sensorDouble.identity()).thenReturn(sensorIDDouble);
        when(sensorIDDouble.toString()).thenReturn(sensorName);

        // Call the method under test
        assertNull(factorySensor.createSensor(sensorIDDouble, validDeviceIDDouble, validSensorFuncIDDouble, null)
                , "Creation of sensor with null sensor class should return null");
    }

    /**
     * Test successful creation of Sensor object using valid parameters.
     * Behaviour must be defined for sensorFunctionality double. When toString() is called on this class instance a string that represents
     * a real name of a SensorFunctionalityID class must be returned in order to create a Sensor object or else a ClassNotFoundException is encountered
     * when Class.forName() is called.
     */
    @Test
    void createSensorWithValidParameters() {
        // Define test data
        String sensorName = "TemperatureCelsiusSensor";
        String validSensorClass = "smarthome.domain.sensor.TemperatureCelsiusSensor";

        // Create mocks for necessary entities
        Sensor sensorDouble = mock(Sensor.class);
        SensorID sensorIDDouble = mock(SensorID.class);
        SensorFunctionalityID validSensorFuncIDDouble = mock(SensorFunctionalityID.class);
        DeviceID validDeviceIDDouble = mock(DeviceID.class);

        // Set up behavior for mocked entities
        when(sensorDouble.identity()).thenReturn(sensorIDDouble);
        when(sensorIDDouble.toString()).thenReturn(sensorName);

        // Call the method under test
        assertNotNull(factorySensor.createSensor(sensorIDDouble, validDeviceIDDouble, validSensorFuncIDDouble, validSensorClass)
                , "Creation of sensor with valid parameters should return a non-null sensor instance");
    }

}