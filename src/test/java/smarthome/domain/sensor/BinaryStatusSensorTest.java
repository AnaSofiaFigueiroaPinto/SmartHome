package smarthome.domain.sensor;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import smarthome.domain.valueobjects.DeviceID;
import smarthome.domain.valueobjects.SensorFunctionalityID;
import smarthome.domain.valueobjects.SensorID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class BinaryStatusSensorTest {
    private DeviceID deviceNameDouble;
    private SensorFunctionalityID sensorFunctionalityIDDouble;
    private SensorID sensorName;

    /**
     * Setting up the BinaryStatusSensor to use in tests
     */
    @BeforeEach
    void setUp() {
        deviceNameDouble = mock(DeviceID.class);
        sensorFunctionalityIDDouble = mock(SensorFunctionalityID.class);
        sensorName = mock(SensorID.class);
    }

    /**
     * Test successful construction of Sensor implementation using valid parameters
     */
    @Test
    void constructWithValidParameters () {
        assertDoesNotThrow(() -> new BinaryStatusSensor(sensorName, deviceNameDouble, sensorFunctionalityIDDouble));
    }

    /**
     * Test case to verify that the {@code getSensorName()} method returns the correct sensor name.
     */
    @Test
    void getSensorName() {
        when(sensorName.toString()).thenReturn("StatusSensor");
        BinaryStatusSensor binaryStatusSensor = new BinaryStatusSensor(sensorName, deviceNameDouble, sensorFunctionalityIDDouble);
        String result = binaryStatusSensor.identity().toString();
        assertEquals("StatusSensor", result);
    }
    /**
     * Test case to verify that an {@link IllegalArgumentException} is thrown when attempting to construct
     * a {@link BinaryStatusSensor} object with a null sensor name.
     */
    @Test
    void invalidBinaryStatusSensorNullSensorName() {
        assertThrows(IllegalArgumentException.class, () -> new BinaryStatusSensor(null, deviceNameDouble, sensorFunctionalityIDDouble));
    }

    /**
     * Test case to verify that an {@link IllegalArgumentException} is thrown when attempting to construct
     * a {@link BinaryStatusSensor} object with a null deviceID.
     */
    @Test
    void invalidDevice() {
        assertThrows(IllegalArgumentException.class, () -> new BinaryStatusSensor(sensorName, null, sensorFunctionalityIDDouble));
    }

    /**
     * Test case to verify that an {@link IllegalArgumentException} is thrown when attempting to construct
        * a {@link BinaryStatusSensor} object with a null functionallity.
        */

    @Test
    void invalidFunctionalityID() {
        assertThrows(IllegalArgumentException.class, () -> new BinaryStatusSensor(sensorName, deviceNameDouble, null));
    }


    /**
     * Test case to verify that the {@code getSensorFunctionality()} method returns the correct sensor functionality.
     */
    @Test
    void getSensorFunctionality() {
        BinaryStatusSensor binaryStatusSensor = new BinaryStatusSensor(sensorName, deviceNameDouble, sensorFunctionalityIDDouble);
        SensorFunctionalityID result = binaryStatusSensor.getSensorFunctionalityID();
        assertEquals(sensorFunctionalityIDDouble, result);
    }

    /**
     * Test case to verify that the {@code identity()} method returns the correct sensor ID (sensorName).
     */
    @Test
    void getIdentityOfSensor () {
        BinaryStatusSensor binaryStatusSensor = new BinaryStatusSensor(sensorName, deviceNameDouble, sensorFunctionalityIDDouble);
        assertEquals(sensorName, binaryStatusSensor.identity());
    }

    /**
     * Checks successfully if Sensor instances are the same when they have the same ID (sensorName)
     */
    @Test
    void checkIfSensorIsSameAs () {
        BinaryStatusSensor sensor1 = new BinaryStatusSensor(sensorName, deviceNameDouble, sensorFunctionalityIDDouble);
        BinaryStatusSensor sensor2 = new BinaryStatusSensor(sensorName, deviceNameDouble, sensorFunctionalityIDDouble);
        assertTrue(sensor1.isSameAs(sensor2));
    }

    /**
     * Fails to check if Sensor instance is the same as Object instance due to Object not being a Sensor
     */
    @Test
    void failCheckIfSensorIsSameAs () {
        BinaryStatusSensor sensor = new BinaryStatusSensor(sensorName, deviceNameDouble, sensorFunctionalityIDDouble);
        Object object = new Object();
        assertFalse(sensor.isSameAs(object));
    }

    /**
     * Successfully returns DeviceID of the sensor
     */
    @Test
    void getDeviceID () {
        BinaryStatusSensor sensor = new BinaryStatusSensor(sensorName, deviceNameDouble, sensorFunctionalityIDDouble);
        DeviceID deviceID = sensor.getDeviceID();
        assertEquals(deviceNameDouble, deviceID);
    }

}
