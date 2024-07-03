package smarthome.domain.sensor;

import smarthome.domain.valueobjects.DeviceID;
import smarthome.domain.valueobjects.SensorFunctionalityID;
import smarthome.domain.valueobjects.SensorID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SolarIrradianceSensorTest {
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
     * Test the constructor of Solar Irradiance Sensor when valid arguments are provided.
     */
    @Test
    void validSolarIrradianceSensorInstantiation() {
        SolarIrradianceSensor sensor = new SolarIrradianceSensor(sensorName, deviceNameDouble, sensorFunctionalityIDDouble);
        assertNotNull(sensor);
    }

    /**
     * Test the constructor of Solar Irradiance Sensor when null sensor name is provided.
     */
    @Test
    void invalidSolarIrradianceSensorNullSensorName() {
        assertThrows(IllegalArgumentException.class, () -> new SolarIrradianceSensor(null, deviceNameDouble, sensorFunctionalityIDDouble));
    }

    /**
     * Test to check if getSensorName method returns the correct sensor name.
     */
    @Test
    void getSensorName() {
        when(sensorName.toString()).thenReturn("SolarIrradianceSensor");
        SolarIrradianceSensor sensor = new SolarIrradianceSensor(sensorName, deviceNameDouble, sensorFunctionalityIDDouble);
        assertEquals("SolarIrradianceSensor", sensor.identity().toString());
    }

    /**
     * Test the constructor of Solar Irradiance Sensor when null deviceName is provided.
     */

    @Test
    void invalidDeviceName() {
        assertThrows(IllegalArgumentException.class, () -> new SolarIrradianceSensor(sensorName, null, sensorFunctionalityIDDouble ));
    }

    /**
     * Test the constructor of Solar Irradiance Sensor when null functionality is provided.
     */

    @Test
    void invalidFunctionality() {
        assertThrows(IllegalArgumentException.class, () -> new SolarIrradianceSensor(sensorName, deviceNameDouble, null ));
    }

    /**
     * Test to check if getSensorFunctionalityID method returns the correct functionality.
     */
    @Test
    void getSensorFunctionality() {
        SolarIrradianceSensor sensor = new SolarIrradianceSensor(sensorName, deviceNameDouble, sensorFunctionalityIDDouble);
        SensorFunctionalityID result = sensor.getSensorFunctionalityID();
        assertEquals(sensorFunctionalityIDDouble, result);
    }

    /**
     * Test case to verify that the {@code identity()} method returns the correct sensor ID (sensorName).
     */
    @Test
    void getIdentityOfSensor() {
        SolarIrradianceSensor solarIrradianceSensor = new SolarIrradianceSensor(sensorName, deviceNameDouble, sensorFunctionalityIDDouble);
        assertEquals(sensorName, solarIrradianceSensor.identity());
    }

    /**
     * Checks successfully if Sensor instances are the same when they have the same ID (sensorName)
     */
    @Test
    void checkIfSensorIsSameAs() {
        SolarIrradianceSensor sensor1 = new SolarIrradianceSensor(sensorName, deviceNameDouble, sensorFunctionalityIDDouble);
        SolarIrradianceSensor sensor2 = new SolarIrradianceSensor(sensorName, deviceNameDouble, sensorFunctionalityIDDouble);
        assertTrue(sensor1.isSameAs(sensor2));
    }

    /**
     * Fails to check if Sensor instance is the same as Object instance due to Object not being a Sensor
     */
    @Test
    void failCheckIfSensorIsSameAs() {
        SolarIrradianceSensor sensor = new SolarIrradianceSensor(sensorName, deviceNameDouble, sensorFunctionalityIDDouble);
        Object object = new Object();
        assertFalse(sensor.isSameAs(object));
    }
    /**
     * Successfully returns DeviceID of the sensor
     */
    @Test
    void getDeviceID () {
        SolarIrradianceSensor sensor = new SolarIrradianceSensor(sensorName, deviceNameDouble, sensorFunctionalityIDDouble);
        DeviceID deviceID = sensor.getDeviceID();
        assertEquals(deviceNameDouble, deviceID);
    }
}