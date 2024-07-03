package smarthome.domain.sensor;


import smarthome.domain.valueobjects.DeviceID;
import smarthome.domain.valueobjects.SensorFunctionalityID;
import smarthome.domain.valueobjects.SensorID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SunsetSensorTest {
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
     * Test case to verify that a {@link SunsetSensor} object is successfully constructed with valid parameters.
     */
    @Test
    void constructSunsetSensor() {
        SunsetSensor sunsetSensor = new SunsetSensor(sensorName, deviceNameDouble, sensorFunctionalityIDDouble);
        assertNotNull(sunsetSensor);
    }

    /**
     * Test case to verify that an {@link IllegalArgumentException} is thrown when attempting to construct
     * a {@link SunsetSensor} object with a null sensor name.
     */
    @Test
    void invalidSunsetSensorNullSensorName() {
        assertThrows(IllegalArgumentException.class, () -> new SunsetSensor(null, deviceNameDouble, sensorFunctionalityIDDouble));
    }


    /**
     * Test case to verify that an {@link IllegalArgumentException} is thrown when attempting to construct
     * a {@link SunsetSensor} object with a null Functionality.
     */
    @Test
    void invalidSunsetSensorNullFunctionality() {
        assertThrows(IllegalArgumentException.class, () -> new SunsetSensor(sensorName, deviceNameDouble, null));
    }

    /**
     * Test case to verify that an {@link IllegalArgumentException} is thrown when attempting to construct
     * a {@link SunsetSensor} object with a null DeviceID object.
     */

    @Test
    void invalidSunsetSensorNullDeviceID() {
        assertThrows(IllegalArgumentException.class, () -> new SunsetSensor(sensorName, null, sensorFunctionalityIDDouble));
    }

    /**
     * Test case to verify that the {@code getSensorName()} method returns the correct sensor name.
     */
    @Test
    void getSensorName() {
        when(sensorName.toString()).thenReturn("SunsetSensor");
        SunsetSensor sunsetSensor = new SunsetSensor(sensorName, deviceNameDouble, sensorFunctionalityIDDouble);
        String result = sunsetSensor.identity().toString();
        assertEquals("SunsetSensor", result);
    }

    /**
     * Test case to verify that the {@code getSensorFunctionality()} method returns the correct sensor functionality.
     */
    @Test
    void getSensorFunctionality() {
        SunsetSensor sunsetSensor = new SunsetSensor(sensorName, deviceNameDouble, sensorFunctionalityIDDouble);
        SensorFunctionalityID result = sunsetSensor.getSensorFunctionalityID();
        assertEquals(sensorFunctionalityIDDouble, result);
    }

    /**
     * Test case to verify that the {@code identity()} method returns the correct sensor ID (sensorName).
     */
    @Test
    void getIdentityOfSensor() {
        SunsetSensor sunsetSensor = new SunsetSensor(sensorName, deviceNameDouble, sensorFunctionalityIDDouble);
        assertEquals(sensorName,sunsetSensor.identity());
    }

    /**
     * Checks successfully if Sensor instances are the same when they have the same ID (sensorName)
     */
    @Test
    void checkIfSensorIsSameAs() {
        SunsetSensor sensor1 = new SunsetSensor(sensorName, deviceNameDouble, sensorFunctionalityIDDouble);
        SunsetSensor sensor2 = new SunsetSensor(sensorName, deviceNameDouble, sensorFunctionalityIDDouble);
        assertTrue(sensor1.isSameAs(sensor2));
    }

    /**
     * Fails to check if Sensor instance is the same as Object instance due to Object not being a Sensor
     */
    @Test
    void failCheckIfSensorIsSameAs() {
        SunsetSensor sensor = new SunsetSensor(sensorName, deviceNameDouble, sensorFunctionalityIDDouble);
        Object object = new Object();
        assertFalse(sensor.isSameAs(object));
    }

    /**
     * Successfully returns DeviceID of the sensor
     */
    @Test
    void getDeviceID () {
        SunsetSensor sensor = new SunsetSensor(sensorName, deviceNameDouble, sensorFunctionalityIDDouble);
        DeviceID deviceID = sensor.getDeviceID();
        assertEquals(deviceNameDouble, deviceID);
    }
}
