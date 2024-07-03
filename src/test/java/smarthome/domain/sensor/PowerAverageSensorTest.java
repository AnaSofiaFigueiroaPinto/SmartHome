package smarthome.domain.sensor;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import smarthome.domain.valueobjects.DeviceID;
import smarthome.domain.valueobjects.SensorFunctionalityID;
import smarthome.domain.valueobjects.SensorID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PowerAverageSensorTest {
    private DeviceID deviceNameDouble;
    private SensorFunctionalityID sensorFunctionalityIDDouble;
    private SensorID sensorName;

    @BeforeEach
    void setUp() {
        deviceNameDouble = mock(DeviceID.class);
        sensorFunctionalityIDDouble = mock(SensorFunctionalityID.class);
        sensorName = mock(SensorID.class);
    }

    /**
     * Test creation of PowerAverageSensor with valid parameters
     */
    @Test
    void validSensor() {
        assertDoesNotThrow(() -> new HumidityPercentageSensor(sensorName, deviceNameDouble, sensorFunctionalityIDDouble));
    }

    /**
     * Test case to verify that the {@code getSensorName()} method returns the correct sensor name.
     */
    @Test
    void getSensorName() {
        when(sensorName.toString()).thenReturn("EnergySensor");
        PowerAverageSensor sensor = new PowerAverageSensor(sensorName, deviceNameDouble, sensorFunctionalityIDDouble);
        assertEquals("EnergySensor", sensor.identity().toString());

    }

    /**
     * Test case to verify that an {@link IllegalArgumentException} is thrown when attempting to construct
     * a {@link PowerAverageSensor} object with a null sensor name.
     */
    @Test
    void invalidConstructionNullArguments() {
        assertThrows(IllegalArgumentException.class, () -> new PowerAverageSensor(null,deviceNameDouble, sensorFunctionalityIDDouble));
    }

    /**
     * Test case to verify that an {@link IllegalArgumentException} is thrown when attempting to construct
     * a {@link PowerAverageSensor} object with a null DeviceID.
     */

    @Test
    void invalidDeviceName() {
        assertThrows(IllegalArgumentException.class, () -> new PowerAverageSensor(sensorName, null, sensorFunctionalityIDDouble));
    }

    /**
     * Test case to verify that an {@link IllegalArgumentException} is thrown when attempting to construct
     * a {@link PowerAverageSensor} object with a null functionality.
     */

    @Test
    void invalidSensorFunctionality() {
        assertThrows(IllegalArgumentException.class, () -> new PowerAverageSensor(sensorName, deviceNameDouble, null));
    }

    /**
     * Test case to verify that the {@code getSensorFunctionality()} method returns the correct sensor functionality.
     */
    @Test
    void getSensorFunctionality() {
        PowerAverageSensor sensor = new PowerAverageSensor(sensorName, deviceNameDouble, sensorFunctionalityIDDouble);
        assertEquals(sensorFunctionalityIDDouble, sensor.getSensorFunctionalityID());
    }

    /**
     * Test case to verify that the {@code identity()} method returns the correct sensor ID (sensorName).
     */
    @Test
    void getIdentityOfSensor() {
        PowerAverageSensor powerAverageSensor = new PowerAverageSensor(sensorName, deviceNameDouble, sensorFunctionalityIDDouble);
        assertEquals(sensorName, powerAverageSensor.identity());
    }

    /**
     * Checks successfully if Sensor instances are the same when they have the same ID (sensorName)
     */
    @Test
    void checkIfSensorIsSameAs() {
        PowerAverageSensor sensor1 = new PowerAverageSensor(sensorName, deviceNameDouble, sensorFunctionalityIDDouble);
        PowerAverageSensor sensor2 = new PowerAverageSensor(sensorName, deviceNameDouble, sensorFunctionalityIDDouble);
        assertTrue(sensor1.isSameAs(sensor2));
    }

    /**
     * Fails to check if Sensor instance is the same as Object instance due to Object not being a Sensor
     */
    @Test
    void failCheckIfSensorIsSameAs() {
        PowerAverageSensor sensor = new PowerAverageSensor(sensorName, deviceNameDouble, sensorFunctionalityIDDouble);
        Object object = new Object();
        assertFalse(sensor.isSameAs(object));
    }
    /**
     * Successfully returns DeviceID of the sensor
     */
    @Test
    void getDeviceID () {
        PowerAverageSensor sensor = new PowerAverageSensor(sensorName, deviceNameDouble, sensorFunctionalityIDDouble);
        DeviceID deviceID = sensor.getDeviceID();
        assertEquals(deviceNameDouble, deviceID);
    }
}