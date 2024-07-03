package smarthome.domain.sensor;


import smarthome.domain.valueobjects.DeviceID;
import smarthome.domain.valueobjects.SensorFunctionalityID;
import smarthome.domain.valueobjects.SensorID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


class ScaleSensorTest {
    private DeviceID deviceNameDouble;
    private SensorFunctionalityID sensorFunctionalityIDDouble;
    private SensorID sensorName;

    /**
     * Set up method to initialize test fixtures.
     */
    @BeforeEach
    void setUp() {
        deviceNameDouble = mock(DeviceID.class);
        sensorFunctionalityIDDouble = mock(SensorFunctionalityID.class);
        sensorName = mock(SensorID.class);

    }

    /**
     * Construct valid ScaleSensor object using valid parameters
     */
    @Test
    void constructScaleSensor() {
        assertDoesNotThrow(() -> new ScaleSensor(sensorName, deviceNameDouble, sensorFunctionalityIDDouble));
    }

    /**
     * Test case for creating a ScaleSensor with a null sensor name.
     */
    @Test
    void invalidScaleSensorNullSensorName() {
        assertThrows(IllegalArgumentException.class, () -> new ScaleSensor(null, deviceNameDouble, sensorFunctionalityIDDouble));
    }

    /**
     * Test case for creating a ScaleSensor with a null device name.
     */
    @Test
    void nullDeviceName() {
        assertThrows(IllegalArgumentException.class, () -> new ScaleSensor(sensorName, null, sensorFunctionalityIDDouble));
    }

    /**
     * Test case for creating a ScaleSensor with a null functionality.
     */
    @Test
    void nullFunctionality() {
        assertThrows(IllegalArgumentException.class, () -> new ScaleSensor(sensorName, deviceNameDouble, null));
    }

    /**
     * Test case for retrieving the sensor name.
     */
    @Test
    void getSensorName() {
        when(sensorName.toString()).thenReturn("ScaleSensor");
        ScaleSensor scaleSensor = new ScaleSensor(sensorName, deviceNameDouble, sensorFunctionalityIDDouble);
        String result = scaleSensor.identity().toString();
        assertEquals("ScaleSensor", result);
    }

    /**
     * Test case for retrieving the sensor functionality.
     */
    @Test
    void getSensorFunctionality() {
        ScaleSensor scaleSensor = new ScaleSensor(sensorName, deviceNameDouble, sensorFunctionalityIDDouble);
        SensorFunctionalityID result = scaleSensor.getSensorFunctionalityID();
        assertEquals(sensorFunctionalityIDDouble, result);
    }

    /**
     * Test case to verify that the {@code identity()} method returns the correct sensor ID (sensorName).
     */
    @Test
    void getIdentityOfSensor() {
        ScaleSensor scaleSensor = new ScaleSensor(sensorName, deviceNameDouble, sensorFunctionalityIDDouble);
        assertEquals(sensorName, scaleSensor.identity());
    }

    /**
     * Checks successfully if Sensor instances are the same when they have the same ID (sensorName)
     */
    @Test
    void checkIfSensorIsSameAs() {
        ScaleSensor sensor1 = new ScaleSensor(sensorName, deviceNameDouble, sensorFunctionalityIDDouble);
        ScaleSensor sensor2 = new ScaleSensor(sensorName, deviceNameDouble, sensorFunctionalityIDDouble);
        assertTrue(sensor1.isSameAs(sensor2));
    }

    /**
     * Fails to check if Sensor instance is the same as Object instance due to Object not being a Sensor
     */
    @Test
    void failCheckIfSensorIsSameAs() {
        ScaleSensor sensor = new ScaleSensor(sensorName, deviceNameDouble, sensorFunctionalityIDDouble);
        Object object = new Object();
        assertFalse(sensor.isSameAs(object));
    }

    /**
     * Successfully returns DeviceID of the sensor
     */
    @Test
    void getDeviceID() {
        ScaleSensor sensor = new ScaleSensor(sensorName, deviceNameDouble, sensorFunctionalityIDDouble);
        DeviceID deviceID = sensor.getDeviceID();
        assertEquals(deviceNameDouble, deviceID);
    }
}