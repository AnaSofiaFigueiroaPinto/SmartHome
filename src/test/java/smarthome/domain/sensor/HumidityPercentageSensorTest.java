package smarthome.domain.sensor;

import smarthome.domain.valueobjects.DeviceID;
import smarthome.domain.valueobjects.SensorFunctionalityID;
import smarthome.domain.valueobjects.SensorID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class HumidityPercentageSensorTest {
    private DeviceID deviceNameDouble;
    private SensorFunctionalityID sensorFunctionalityIDDouble;
    private SensorID sensorName;

    /**
     * Setting up the FactoryValue to use in tests
     */
    @BeforeEach
    void setUp() {
        deviceNameDouble = mock(DeviceID.class);
        sensorFunctionalityIDDouble = mock(SensorFunctionalityID.class);
        sensorName = mock(SensorID.class);
    }

    /**
     * Test creation of HumidityPercentage sensor with valid parameters
     */
    @Test
    void createValidSensor() {
        assertDoesNotThrow(() -> new HumidityPercentageSensor(sensorName, deviceNameDouble, sensorFunctionalityIDDouble));
    }

    /**
     * Test creation of HumidityPercentage sensor with null sensorName
     */
    @Test
    void invalidSensorNullParameters() {
        assertThrows(IllegalArgumentException.class, () -> new HumidityPercentageSensor(null, deviceNameDouble, sensorFunctionalityIDDouble));
    }

    /**
     * Test if the method returns a valid name.
     */
    @Test
    void getSensorNameValidName() {
        when(sensorName.toString()).thenReturn("HumiditySensor");
        HumidityPercentageSensor humidityPercentageSensor = new HumidityPercentageSensor(sensorName, deviceNameDouble, sensorFunctionalityIDDouble);
        assertEquals("HumiditySensor", humidityPercentageSensor.identity().toString());
    }

    /**
     * Test creation of HumidityPercentage sensor with null deviceName
     */

    @Test
    void invalidDeviceID() {
        assertThrows(IllegalArgumentException.class, () -> new HumidityPercentageSensor(sensorName, null, sensorFunctionalityIDDouble));
    }

    /**
     * Test creation of HumidityPercentage sensor with null functionality
     */

    @Test
    void invalidFunctionality() {
        assertThrows(IllegalArgumentException.class, () -> new HumidityPercentageSensor(sensorName, deviceNameDouble, null));
    }

    /**
     * Test if the method returns the correct functionality.
     */
    @Test
    void getSensorFunctionality() {
        HumidityPercentageSensor humidityPercentageSensor = new HumidityPercentageSensor(sensorName, deviceNameDouble, sensorFunctionalityIDDouble);
        assertEquals(sensorFunctionalityIDDouble, humidityPercentageSensor.getSensorFunctionalityID());
    }

    /**
     * Test case to verify that the {@code identity()} method returns the correct sensor ID (sensorName).
     */
    @Test
    void getIdentityOfSensor() {
        HumidityPercentageSensor humidityPercentageSensor = new HumidityPercentageSensor(sensorName, deviceNameDouble, sensorFunctionalityIDDouble);
        assertEquals(sensorName, humidityPercentageSensor.identity());
    }

    /**
     * Checks successfully if Sensor instances are the same when they have the same ID (sensorName)
     */
    @Test
    void checkIfSensorIsSameAs() {
        HumidityPercentageSensor sensor1 = new HumidityPercentageSensor(sensorName, deviceNameDouble, sensorFunctionalityIDDouble);
        HumidityPercentageSensor sensor2 = new HumidityPercentageSensor(sensorName, deviceNameDouble, sensorFunctionalityIDDouble);
        assertTrue(sensor1.isSameAs(sensor2));
    }

    /**
     * Fails to check if Sensor instance is the same as Object instance due to Object not being a Sensor
     */
    @Test
    void failCheckIfSensorIsSameAs() {
        HumidityPercentageSensor sensor = new HumidityPercentageSensor(sensorName, deviceNameDouble, sensorFunctionalityIDDouble);
        Object object = new Object();
        assertFalse(sensor.isSameAs(object));
    }
    /**
     * Successfully returns DeviceID of the sensor
     */
    @Test
    void getDeviceID () {
        HumidityPercentageSensor humidityPercentageSensor = new HumidityPercentageSensor(sensorName, deviceNameDouble, sensorFunctionalityIDDouble);
        DeviceID deviceID = humidityPercentageSensor.getDeviceID();
        assertEquals(deviceNameDouble, deviceID);
    }
}



