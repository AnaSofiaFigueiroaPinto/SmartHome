package smarthome.mapper;

import smarthome.domain.valueobjects.DeviceID;
import smarthome.domain.valueobjects.SensorFunctionalityID;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test cases for the {@code SensorDTO} class.
 */
class SensorDTOTest {

    /**
     * Test to get the Sensor Name from the SensorDTO object.
     */
    @Test
    void getSensorID() {
        DeviceID deviceID = new DeviceID("deviceID");
        SensorFunctionalityID sensorFunctionalityID = new SensorFunctionalityID("TemperatureCelsius");

        SensorDTO sensorDTO = new SensorDTO("TemperatureSensor", deviceID.toString(), sensorFunctionalityID.toString());
        assertEquals("TemperatureSensor", sensorDTO.sensorName);
    }

    /**
     * Test to get the DeviceID from the SensorDTO object.
     */
    @Test
    void getDeviceID() {
        DeviceID deviceID = new DeviceID("deviceID");
        SensorFunctionalityID sensorFunctionalityID = new SensorFunctionalityID("TemperatureCelsius");

        SensorDTO sensorDTO = new SensorDTO("TemperatureSensor", deviceID.toString(), sensorFunctionalityID.toString());
        assertEquals(deviceID.toString(), sensorDTO.deviceID);
    }

    /**
     * Test to get the SensorFunctionalityID from the SensorDTO object.
     */
    @Test
    void getSensorFunctionalityID() {
        DeviceID deviceID = new DeviceID("deviceID");
        SensorFunctionalityID sensorFunctionalityID = new SensorFunctionalityID("TemperatureCelsius");

        SensorDTO sensorDTO = new SensorDTO("TemperatureSensor", deviceID.toString(), sensorFunctionalityID.toString());
        assertEquals(sensorFunctionalityID.toString(), sensorDTO.functionalityID);
    }

    /**
     * Test to get the Sensor Name from the SensorDTO object using the second constructor.
     */
    @Test
    void getSensorIDSecondConstructor() {
        SensorDTO sensorDTO = new SensorDTO("TemperatureSensor");
        assertEquals("TemperatureSensor", sensorDTO.sensorName);
    }

    /**
     * Tests the equals method when both objects are exactly the same.
     */
    @Test
    void testEqualsSameObject() {
        SensorDTO sensorDTO = new SensorDTO("TemperatureSensor", "deviceID", "TemperatureCelsius");
        assertTrue(sensorDTO.equals(sensorDTO));
    }

    /**
     * Tests the equals method when the other object is null.
     */
    @Test
    void testEqualsNull() {
        SensorDTO sensorDTO = new SensorDTO("TemperatureSensor", "deviceID", "TemperatureCelsius");
        assertFalse(sensorDTO.equals(null));
    }

    /**
     * Tests the equals method when the other object is of a different class.
     */
    @Test
    void testEqualsDifferentClass() {
        SensorDTO sensorDTO = new SensorDTO("TemperatureSensor", "deviceID", "TemperatureCelsius");
        assertFalse(sensorDTO.equals(new Object()));
    }

    /**
     * Tests the equals method when both objects are of the same class but have different attributes.
     */
    @Test
    void testEqualsDifferentAttributes() {
        SensorDTO sensorDTO1 = new SensorDTO("TemperatureSensor", "deviceID", "TemperatureCelsius");
        SensorDTO sensorDTO2 = new SensorDTO("HumiditySensor", "deviceID2", "HumidityPercentage");
        assertFalse(sensorDTO1.equals(sensorDTO2));
        assertFalse(sensorDTO2.equals(sensorDTO1));
    }

    /**
     * Tests the equals method when both objects are of the same class and have the same attributes.
     */
    @Test
    void testEqualsSameAttributes() {
        SensorDTO sensorDTO1 = new SensorDTO("TemperatureSensor", "deviceID", "TemperatureCelsius");
        SensorDTO sensorDTO2 = new SensorDTO("TemperatureSensor", "deviceID", "TemperatureCelsius");
        assertTrue(sensorDTO1.equals(sensorDTO2));
        assertTrue(sensorDTO2.equals(sensorDTO1));
    }

    /**
     * Tests the hashCode method to ensure it returns the same value for two equal objects.
     */
    @Test
    void testHashCode() {
        SensorDTO sensorDTO1 = new SensorDTO("TemperatureSensor", "deviceID", "TemperatureCelsius");
        SensorDTO sensorDTO2 = new SensorDTO("TemperatureSensor", "deviceID", "TemperatureCelsius");
        assertEquals(sensorDTO1.hashCode(), sensorDTO2.hashCode());
        assertEquals(sensorDTO2.hashCode(), sensorDTO1.hashCode());
    }

    /**
     * Tests the hashCode method when both objects are of the same class but have different attributes.
     */
    @Test
    void testHashCodeDifferentAttributes() {
        SensorDTO sensorDTO1 = new SensorDTO("TemperatureSensor", "deviceID", "TemperatureCelsius");
        SensorDTO sensorDTO2 = new SensorDTO("HumiditySensor", "deviceID2", "HumidityPercentage");
        assertNotEquals(sensorDTO1.hashCode(), sensorDTO2.hashCode());
        assertNotEquals(sensorDTO2.hashCode(), sensorDTO1.hashCode());
    }

    /**
     * Tests the equals method when both objects have the same sensorName, but different deviceID and functionalityID.
     */
    @Test
    void testEqualsSameSensorNameDifferentDeviceIDAndFunctionalityID() {
        SensorDTO sensorDTO1 = new SensorDTO("TemperatureSensor", "deviceID1", "TemperatureCelsius");
        SensorDTO sensorDTO2 = new SensorDTO("TemperatureSensor", "deviceID2", "HumidityPercentage");
        assertFalse(sensorDTO1.equals(sensorDTO2));
        assertFalse(sensorDTO2.equals(sensorDTO1));
    }

    /**
     * Tests the equals method when both objects have the same deviceID, but different sensorName and functionalityID.
     */
    @Test
    void testEqualsSameDeviceIDDifferentSensorNameAndFunctionalityID() {
        SensorDTO sensorDTO1 = new SensorDTO("TemperatureSensor", "deviceID", "TemperatureCelsius");
        SensorDTO sensorDTO2 = new SensorDTO("HumiditySensor", "deviceID", "HumidityPercentage");
        assertFalse(sensorDTO1.equals(sensorDTO2));
        assertFalse(sensorDTO2.equals(sensorDTO1));
    }

    /**
     * Tests the equals method when both objects have the same functionalityID, but different sensorName and deviceID.
     */
    @Test
    void testEqualsSameFunctionalityIDDifferentSensorNameAndDeviceID() {
        SensorDTO sensorDTO1 = new SensorDTO("TemperatureSensor", "deviceID1", "TemperatureCelsius");
        SensorDTO sensorDTO2 = new SensorDTO("HumiditySensor", "deviceID2", "TemperatureCelsius");
        assertFalse(sensorDTO1.equals(sensorDTO2));
        assertFalse(sensorDTO2.equals(sensorDTO1));
    }

    /**
     * Tests to empty constructor to ensure that all attributes are null.
     */
    @Test
    void emptyConstructor() {
        SensorDTO sensorDTO = new SensorDTO();
        assertNull(sensorDTO.sensorName);
        assertNull(sensorDTO.deviceID);
        assertNull(sensorDTO.functionalityID);
    }
}