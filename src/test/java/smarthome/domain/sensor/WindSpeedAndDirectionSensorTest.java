package smarthome.domain.sensor;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import smarthome.domain.valueobjects.DeviceID;
import smarthome.domain.valueobjects.SensorFunctionalityID;
import smarthome.domain.valueobjects.SensorID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class WindSpeedAndDirectionSensorTest {
    private DeviceID deviceNameDouble;
    private SensorFunctionalityID sensorFunctionalityIDDouble;
    private SensorID sensorNameDouble;

    @BeforeEach
    void setUp() {
        deviceNameDouble = mock(DeviceID.class);
        sensorFunctionalityIDDouble = mock(SensorFunctionalityID.class);
        sensorNameDouble = mock(SensorID.class);
    }

    /**
     * Test creation of WindSpeedKmhAndDirection sensor with valid parameters
     */
    @Test
    void constructSensorWithValidParameters() {
        assertDoesNotThrow(() -> new WindSpeedAndDirectionSensor(sensorNameDouble, deviceNameDouble, sensorFunctionalityIDDouble));
    }

    /**
     * Test creation of WindSpeedKmhAndDirection sensor with null sensorName
     */
    @Test
    void constructSensorWithNullSensorName() {
        assertThrows(IllegalArgumentException.class, () -> new WindSpeedAndDirectionSensor(null, deviceNameDouble, sensorFunctionalityIDDouble));
    }

    /**
     * Test creation of WindSpeedKmhAndDirection sensor with null deviceName
     */

    @Test
    void invalidDeviceName() {
        assertThrows(IllegalArgumentException.class, () -> new WindSpeedAndDirectionSensor(sensorNameDouble, null, sensorFunctionalityIDDouble));
    }

    /**
     * Test creation of WindSpeedKmhAndDirection sensor with null functionality
     */

    @Test
    void invalidFunctionality() {
        assertThrows(IllegalArgumentException.class, () -> new WindSpeedAndDirectionSensor(sensorNameDouble, deviceNameDouble, null));
    }

    /**
     * Test if retrieval of created sensor's name is successful
     */
    @Test
    void getSensorName() {
        when(sensorNameDouble.toString()).thenReturn("WindSensor");
        WindSpeedAndDirectionSensor windSpeedAndDirectionSensor = new WindSpeedAndDirectionSensor(sensorNameDouble, deviceNameDouble, sensorFunctionalityIDDouble);
        assertEquals("WindSensor", windSpeedAndDirectionSensor.identity().toString());
    }

    /**
     * Test if the method returns the correct functionality.
     */
    @Test
    void getSensorFunctionality() {
        WindSpeedAndDirectionSensor windSpeedAndDirectionSensor = new WindSpeedAndDirectionSensor(sensorNameDouble, deviceNameDouble, sensorFunctionalityIDDouble);
        assertEquals(sensorFunctionalityIDDouble, windSpeedAndDirectionSensor.getSensorFunctionalityID());
    }

    /**
     * Test case to verify that the {@code identity()} method returns the correct sensor ID (sensorName).
     */
    @Test
    void getIdentityOfSensor() {
        WindSpeedAndDirectionSensor windSpeedAndDirectionSensor = new WindSpeedAndDirectionSensor(sensorNameDouble, deviceNameDouble, sensorFunctionalityIDDouble);
        assertEquals(sensorNameDouble, windSpeedAndDirectionSensor.identity());
    }

    /**
     * Checks successfully if Sensor instances are the same when they have the same ID (sensorName)
     */
    @Test
    void checkIfSensorIsSameAs() {
        WindSpeedAndDirectionSensor sensor1 = new WindSpeedAndDirectionSensor(sensorNameDouble, deviceNameDouble, sensorFunctionalityIDDouble);
        WindSpeedAndDirectionSensor sensor2 = new WindSpeedAndDirectionSensor(sensorNameDouble, deviceNameDouble, sensorFunctionalityIDDouble);
        assertTrue(sensor1.isSameAs(sensor2));
    }

    /**
     * Fails to check if Sensor instance is the same as Object instance due to Object not being a Sensor
     */
    @Test
    void failCheckIfSensorIsSameAs() {
        WindSpeedAndDirectionSensor sensor = new WindSpeedAndDirectionSensor(sensorNameDouble, deviceNameDouble, sensorFunctionalityIDDouble);
        Object object = new Object();
        assertFalse(sensor.isSameAs(object));
    }

    /**
     * Successfully returns DeviceID of the sensor
     */
    @Test
    void getDeviceID () {
        WindSpeedAndDirectionSensor sensor = new WindSpeedAndDirectionSensor(sensorNameDouble, deviceNameDouble, sensorFunctionalityIDDouble);
        DeviceID deviceID = sensor.getDeviceID();
        assertEquals(deviceNameDouble, deviceID);
    }
}