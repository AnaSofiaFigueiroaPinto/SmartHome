package smarthome.domain.sensor;

import smarthome.domain.valueobjects.DeviceID;
import smarthome.domain.valueobjects.SensorFunctionalityID;
import smarthome.domain.valueobjects.SensorID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TemperatureCelsiusSensorTest {
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
     * Test creation of TemperatureCelsius sensor with valid parameters
     */
    @Test
    void validSensor() {
        assertDoesNotThrow(() -> new TemperatureCelsiusSensor(sensorNameDouble, deviceNameDouble, sensorFunctionalityIDDouble));
    }

    /**
     * Test creation of TemperatureCelsius sensor with null sensorName
     */
    @Test
    void invalidSensorNullParameters() {
        assertThrows(IllegalArgumentException.class, () -> new TemperatureCelsiusSensor(null, deviceNameDouble, sensorFunctionalityIDDouble));
    }

    /**
     * Test creation of TemperatureCelsiusSensor with null deviceName
     */

    @Test
    void invalidDeviceName() {
        assertThrows(IllegalArgumentException.class, () -> new TemperatureCelsiusSensor(sensorNameDouble, null, sensorFunctionalityIDDouble));
    }

    /**
     * Test creation of TemperatureCelsiusSensor with null Functionality
     */

    @Test
    void invalidFunctionality() {
        assertThrows(IllegalArgumentException.class, () -> new TemperatureCelsiusSensor(sensorNameDouble, deviceNameDouble, null));
    }

    /**
     * Test if the method returns sensor name.
     */
    @Test
    void getSensorName() {
        when(sensorNameDouble.toString()).thenReturn("TemperatureSensor");
        TemperatureCelsiusSensor temperatureCelsiusSensor = new TemperatureCelsiusSensor(sensorNameDouble, deviceNameDouble, sensorFunctionalityIDDouble);
        assertEquals("TemperatureSensor", temperatureCelsiusSensor.identity().toString());
    }

    /**
     * Test if the method returns the correct functionality.
     */
    @Test
    void getSensorFunctionality() {
        TemperatureCelsiusSensor temperatureCelsiusSensor = new TemperatureCelsiusSensor(sensorNameDouble, deviceNameDouble, sensorFunctionalityIDDouble);
        assertEquals(sensorFunctionalityIDDouble, temperatureCelsiusSensor.getSensorFunctionalityID());
    }

    /**
     * Test case to verify that the {@code identity()} method returns the correct sensor ID (sensorName).
     */
    @Test
    void getIdentityOfSensor() {
        TemperatureCelsiusSensor temperatureCelsiusSensor = new TemperatureCelsiusSensor(sensorNameDouble, deviceNameDouble, sensorFunctionalityIDDouble);
        assertEquals(sensorNameDouble, temperatureCelsiusSensor.identity());
    }

    /**
     * Checks successfully if Sensor instances are the same when they have the same ID (sensorName)
     */
    @Test
    void checkIfSensorIsSameAs() {
        TemperatureCelsiusSensor sensor1 = new TemperatureCelsiusSensor(sensorNameDouble, deviceNameDouble, sensorFunctionalityIDDouble);
        TemperatureCelsiusSensor sensor2 = new TemperatureCelsiusSensor(sensorNameDouble, deviceNameDouble, sensorFunctionalityIDDouble);
        assertTrue(sensor1.isSameAs(sensor2));
    }

    /**
     * Fails to check if Sensor instance is the same as Object instance due to Object not being a Sensor
     */
    @Test
    void failCheckIfSensorIsSameAs() {
        TemperatureCelsiusSensor sensor = new TemperatureCelsiusSensor(sensorNameDouble, deviceNameDouble, sensorFunctionalityIDDouble);
        Object object = new Object();
        assertFalse(sensor.isSameAs(object));
    }

    /**
     * Successfully returns DeviceID of the sensor
     */
    @Test
    void getDeviceID() {
        TemperatureCelsiusSensor sensor = new TemperatureCelsiusSensor(sensorNameDouble, deviceNameDouble, sensorFunctionalityIDDouble);
        DeviceID deviceID = sensor.getDeviceID();
        assertEquals(deviceNameDouble, deviceID);
    }
}



