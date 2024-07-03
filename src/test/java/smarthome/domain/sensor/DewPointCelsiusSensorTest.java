package smarthome.domain.sensor;

import smarthome.domain.valueobjects.DeviceID;
import smarthome.domain.valueobjects.SensorFunctionalityID;
import smarthome.domain.valueobjects.SensorID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class DewPointCelsiusSensorTest {

    private DeviceID deviceNameDouble;
    private SensorFunctionalityID sensorFunctionalityIDDouble;
    private SensorID sensorName;

    /**
     * Setting up the DewPoint Sensor under test.
     */
    @BeforeEach
    void setUp() {
        deviceNameDouble = mock(DeviceID.class);
        sensorFunctionalityIDDouble = mock(SensorFunctionalityID.class);
        sensorName = mock(SensorID.class);

    }

    /**
     * Test to create Dew Point sensor object with valid parameters.
     * Verifies that no exception is thrown.
     */
    @Test
    void constructorSensorWithValidParameters() {
        assertDoesNotThrow(() -> new DewPointCelsiusSensor(sensorName, deviceNameDouble, sensorFunctionalityIDDouble));
    }

    /**
     * Test to create Dew Point sensor object with null sensorName
     * Verifies that an exception is thrown.
     */
    @Test
    void constructorSensorWithNullParameters() {
        assertThrows(IllegalArgumentException.class, () -> new DewPointCelsiusSensor(null, deviceNameDouble, sensorFunctionalityIDDouble));
    }

    /**
     * Test if the method returns the name.
     */
    @Test
    void getSensorNameValidName() {
        when(sensorName.toString()).thenReturn("DewPointCelsiusSensor");
        DewPointCelsiusSensor dewPointCelsiusSensor = new DewPointCelsiusSensor(sensorName, deviceNameDouble, sensorFunctionalityIDDouble);
        String result = dewPointCelsiusSensor.identity().toString();
        assertEquals("DewPointCelsiusSensor", result);
    }

    /**
     * Test to create Dew Point sensor object with null functionality.
     * Verifies that an exception is thrown.
     */

    @Test
    void failToCreateSensorNullFunctionalityID() {
        assertThrows(IllegalArgumentException.class, () -> new DewPointCelsiusSensor(sensorName, deviceNameDouble, null));
    }

    /**
     * Test to create Dew Point sensor object with null deviceName.
     * Verifies that an exception is thrown.
     */

    @Test
    void failToCreateSensorNullDeviceName() {
        assertThrows(IllegalArgumentException.class, () -> new DewPointCelsiusSensor(sensorName, null, sensorFunctionalityIDDouble));
    }

    /**
     * Test if the method returns the correct functionality.
     */
    @Test
    void getSensorFunctionality() {
        DewPointCelsiusSensor dewPointCelsiusSensor = new DewPointCelsiusSensor(sensorName, deviceNameDouble, sensorFunctionalityIDDouble);
        SensorFunctionalityID result = dewPointCelsiusSensor.getSensorFunctionalityID();
        assertEquals(sensorFunctionalityIDDouble, result);
    }

    /**
     * Test case to verify that the {@code identity()} method returns the correct sensor ID (sensorName).
     */
    @Test
    void getIdentityOfSensor () {
        DewPointCelsiusSensor binaryStatusSensor = new DewPointCelsiusSensor(sensorName, deviceNameDouble, sensorFunctionalityIDDouble);
        assertEquals(sensorName, binaryStatusSensor.identity());
    }

    /**
     * Checks successfully if Sensor instances are the same when they have the same ID (sensorName)
     */
    @Test
    void checkIfSensorIsSameAs () {
        DewPointCelsiusSensor sensor1 = new DewPointCelsiusSensor(sensorName, deviceNameDouble, sensorFunctionalityIDDouble);
        DewPointCelsiusSensor sensor2 = new DewPointCelsiusSensor(sensorName, deviceNameDouble, sensorFunctionalityIDDouble);
        assertTrue(sensor1.isSameAs(sensor2));
    }

    /**
     * Fails to check if Sensor instance is the same as Object instance due to Object not being a Sensor
     */
    @Test
    void failCheckIfSensorIsSameAs () {
        DewPointCelsiusSensor sensor = new DewPointCelsiusSensor(sensorName, deviceNameDouble, sensorFunctionalityIDDouble);
        Object object = new Object();
        assertFalse(sensor.isSameAs(object));
    }

    /**
     * Successfully returns DeviceID of the sensor
     */
    @Test
    void getDeviceID () {
        DewPointCelsiusSensor sensor = new DewPointCelsiusSensor(sensorName, deviceNameDouble, sensorFunctionalityIDDouble);
        DeviceID deviceID = sensor.getDeviceID();
        assertEquals(deviceNameDouble, deviceID);
    }

}