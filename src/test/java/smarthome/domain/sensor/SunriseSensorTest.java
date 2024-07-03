package smarthome.domain.sensor;

import smarthome.domain.valueobjects.DeviceID;
import smarthome.domain.valueobjects.SensorFunctionalityID;
import smarthome.domain.valueobjects.SensorID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SunriseSensorTest {
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
     * Test case to verify that a {@link SunriseSensor} object is successfully constructed with valid parameters.
     */
    @Test
    void constructSunriseSensor() {
        SunriseSensor sunriseSensor = new SunriseSensor(sensorName, deviceNameDouble, sensorFunctionalityIDDouble);
        assertNotNull(sunriseSensor);
    }

    /**
     * Test case to verify that an {@link IllegalArgumentException} is thrown when attempting to construct
     * a {@link SunriseSensor} object with a null sensor name.
     */
    @Test
    void invalidSunriseSensorNullSensorName() {
        assertThrows(IllegalArgumentException.class, () -> new SunriseSensor(null, deviceNameDouble, sensorFunctionalityIDDouble));
    }

    /**
     * Test case to verify that an {@link IllegalArgumentException} is thrown when attempting to construct
     * a {@link SunriseSensor} object with a null device name.
     */
    @Test
    void invalidSunriseSensorNullDeviceName() {
        assertThrows(IllegalArgumentException.class, () -> new SunriseSensor(sensorName, null, sensorFunctionalityIDDouble));
    }

    /**
     * Test case to verify that an {@link IllegalArgumentException} is thrown when attempting to construct
     * a {@link SunriseSensor} object with a null Functionality.
     */
    @Test
    void invalidSunriseSensorNullFunctionality() {
        assertThrows(IllegalArgumentException.class, () -> new SunriseSensor(sensorName, deviceNameDouble, null));
    }


    /**
     * Test case to verify that the {@code getSensorName()} method returns the correct sensor name.
     */
    @Test
    void getSensorName() {
        when(sensorName.toString()).thenReturn("SunriseSensor");
        SunriseSensor sunriseSensor = new SunriseSensor(sensorName, deviceNameDouble, sensorFunctionalityIDDouble);
        String result = sunriseSensor.identity().toString();
        assertEquals("SunriseSensor", result);
    }

    /**
     * Test case to verify that the {@code getSensorFunctionality()} method returns the correct sensor functionality.
     */
    @Test
    void getSensorFunctionality() {
        SunriseSensor sunriseSensor = new SunriseSensor(sensorName, deviceNameDouble, sensorFunctionalityIDDouble);
        SensorFunctionalityID result = sunriseSensor.getSensorFunctionalityID();
        assertEquals(sensorFunctionalityIDDouble, result);
    }

    /**
     * Test case to verify that the {@code identity()} method returns the correct sensor ID (sensorName).
     */
    @Test
    void getIdentityOfSensor () {
        SunriseSensor sunriseSensor = new SunriseSensor(sensorName, deviceNameDouble, sensorFunctionalityIDDouble);
        assertEquals(sensorName, sunriseSensor.identity());
    }

    /**
     * Checks successfully if Sensor instances are the same when they have the same ID (sensorName)
     */
    @Test
    void checkIfSensorIsSameAs () {
        SunriseSensor sensor1 = new SunriseSensor(sensorName, deviceNameDouble, sensorFunctionalityIDDouble);
        SunriseSensor sensor2 = new SunriseSensor(sensorName, deviceNameDouble, sensorFunctionalityIDDouble);
        assertTrue(sensor1.isSameAs(sensor2));
    }

    /**
     * Fails to check if Sensor instance is the same as Object instance due to Object not being a Sensor
     */
    @Test
    void failCheckIfSensorIsSameAs () {
        SunriseSensor sensor = new SunriseSensor(sensorName, deviceNameDouble, sensorFunctionalityIDDouble);
        Object object = new Object();
        assertFalse(sensor.isSameAs(object));
    }
    /**
     * Successfully returns DeviceID of the sensor
     */
    @Test
    void getDeviceID () {
        SunriseSensor sensor = new SunriseSensor(sensorName, deviceNameDouble, sensorFunctionalityIDDouble);
        DeviceID deviceID = sensor.getDeviceID();
        assertEquals(deviceNameDouble, deviceID);
    }
}