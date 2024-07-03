package smarthome.domain.sensor;

import smarthome.domain.valueobjects.DeviceID;
import smarthome.domain.valueobjects.SensorFunctionalityID;
import smarthome.domain.valueobjects.SensorID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SpecificTimePowerConsumptionSensorTest {
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
     * Construct valid SpecificTimePowerConsumptionSensor object using valid paramters
     */
    @Test
    void constructValidSpecificTimePowerConsumptionSensor() {
        SpecificTimePowerConsumptionSensor sensor = new SpecificTimePowerConsumptionSensor(sensorName, deviceNameDouble, sensorFunctionalityIDDouble);
        assertNotNull(sensor);
    }


    /**
     * Test case to verify that the {@code getSensorName()} method returns the correct sensor name.
     */
    @Test
    void getSensorName() {
        when(sensorName.toString()).thenReturn("EnergySensor");
        SpecificTimePowerConsumptionSensor sensor = new SpecificTimePowerConsumptionSensor(sensorName, deviceNameDouble, sensorFunctionalityIDDouble);
        assertEquals("EnergySensor", sensor.identity().toString());
    }

    /**
     * Test case to verify that an {@link IllegalArgumentException} is thrown when attempting to construct
     * a {@link SpecificTimePowerConsumptionSensor} object with a null sensor name.
     */
    @Test
    void getSensorNameInvalidNullName() {
        assertThrows(IllegalArgumentException.class, () -> new SpecificTimePowerConsumptionSensor(null, deviceNameDouble, sensorFunctionalityIDDouble));
    }

    /**
     * Test case to verify that an {@link IllegalArgumentException} is thrown when attempting to construct
     * a {@link SpecificTimePowerConsumptionSensor} object with a null deviceName.
     */

    @Test
    void invalidDeviceName() {
        assertThrows(IllegalArgumentException.class, () -> new SpecificTimePowerConsumptionSensor(sensorName, null, sensorFunctionalityIDDouble));
    }

    /**
     * Test case to verify that an {@link IllegalArgumentException} is thrown when attempting to construct
     * a {@link SpecificTimePowerConsumptionSensor} object with a null deviceName.
     */

    @Test
    void invalidFunctionality() {
        assertThrows(IllegalArgumentException.class, () -> new SpecificTimePowerConsumptionSensor(sensorName, deviceNameDouble, null));
    }

    /**
     * Test case to verify that the {@code getSensorFunctionality()} method returns the correct sensor functionality.
     */
    @Test
    void getSensorFunctionality() {
        SpecificTimePowerConsumptionSensor sensor = new SpecificTimePowerConsumptionSensor(sensorName, deviceNameDouble, sensorFunctionalityIDDouble);
        assertEquals(sensorFunctionalityIDDouble, sensor.getSensorFunctionalityID());
    }

    /**
     * Test case to verify that the {@code identity()} method returns the correct sensor ID (sensorName).
     */
    @Test
    void getIdentityOfSensor() {
        SpecificTimePowerConsumptionSensor specificTimePowerConsumptionSensor = new SpecificTimePowerConsumptionSensor(sensorName, deviceNameDouble, sensorFunctionalityIDDouble);
        assertEquals(sensorName, specificTimePowerConsumptionSensor.identity());
    }

    /**
     * Checks successfully if Sensor instances are the same when they have the same ID (sensorName)
     */
    @Test
    void checkIfSensorIsSameAs() {
        SpecificTimePowerConsumptionSensor sensor1 = new SpecificTimePowerConsumptionSensor(sensorName, deviceNameDouble, sensorFunctionalityIDDouble);
        SpecificTimePowerConsumptionSensor sensor2 = new SpecificTimePowerConsumptionSensor(sensorName, deviceNameDouble, sensorFunctionalityIDDouble);
        assertTrue(sensor1.isSameAs(sensor2));
    }

    /**
     * Fails to check if Sensor instance is the same as Object instance due to Object not being a Sensor
     */
    @Test
    void failCheckIfSensorIsSameAs() {
        SpecificTimePowerConsumptionSensor sensor = new SpecificTimePowerConsumptionSensor(sensorName, deviceNameDouble, sensorFunctionalityIDDouble);
        Object object = new Object();
        assertFalse(sensor.isSameAs(object));
    }
    /**
     * Successfully returns DeviceID of the sensor
     */
    @Test
    void getDeviceID () {
        SpecificTimePowerConsumptionSensor sensor = new SpecificTimePowerConsumptionSensor(sensorName, deviceNameDouble, sensorFunctionalityIDDouble);
        DeviceID deviceID = sensor.getDeviceID();
        assertEquals(deviceNameDouble, deviceID);
    }
}