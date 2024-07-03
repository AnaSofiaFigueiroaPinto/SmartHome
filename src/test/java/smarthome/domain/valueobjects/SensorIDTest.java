package smarthome.domain.valueobjects;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SensorIDTest {

    /**
     * Verifies that a {@link SensorID} object is successfully constructed with a valid sensor name.
     */
    @Test
    void validConstructorSensorID() {
        SensorID sensorID = new SensorID("TemperatureSensor");
        assertNotNull(sensorID);
    }

    /**
     * Verifies that an {@link IllegalArgumentException} is thrown when attempting to construct a {@link SensorID} object with a null sensor name.
     */
    @Test
    void invalidConstructorSensorIDNullSensorName() {
        assertThrows(IllegalArgumentException.class, () -> new SensorID(null));
    }

    /**
     * Verifies that an {@link IllegalArgumentException} is thrown when attempting to construct a {@link SensorID} object with an empty sensor name.
     */
    @Test
    void invalidConstructorSensorIDEmptySensorName() {
        assertThrows(IllegalArgumentException.class, () -> new SensorID(""));
    }

    /**
     * Verifies that an {@link IllegalArgumentException} is thrown when attempting to construct a {@link SensorID} object with a blank sensor name.
     */
    @Test
    void invalidConstructorSensorIDBlankSensorName() {
        assertThrows(IllegalArgumentException.class, () -> new SensorID("  "));
    }

    /**
     * Verifies that an {@link IllegalArgumentException} is thrown when attempting to construct a {@link SensorID} object with a whitespace sensor name.
     */
    @Test
    void invalidConstructorSensorIDWhitespaceSensorName() {
        assertThrows(IllegalArgumentException.class, () -> new SensorID("  "));
    }

    /**
     * Verifies if two {@link SensorID} objects with the same sensor name are considered equal.
     * Returns true if object is the same or have the same ID.
     */
    @Test
    void successEquals() {
        SensorID sensorID1 = new SensorID("TemperatureSensor");
        SensorID sensorID2 = new SensorID("TemperatureSensor");
        assertEquals(sensorID1, sensorID2);
        assertEquals(sensorID1, sensorID1);
    }

    /**
     * Verifies that two {@link SensorID} objects with different sensor names are not considered equal.
     */
    @Test
    void failEqualsDifferentSensorID() {
        SensorID sensorID1 = new SensorID("TemperatureSensor");
        SensorID sensorID2 = new SensorID("HumiditySensor");
        assertNotEquals(sensorID1, sensorID2);
    }

    @Test
    void failEqualsDifferentClass() {
        SensorID sensorID1 = new SensorID("TemperatureSensor");
        Object obj = new Object();
        assertNotEquals(sensorID1, obj);
    }

    /**
     * Test the equals method of the SensorID class with a null object.
     * The test should return false if the object is null.
     */
    @Test
    void failEqualsObjectNull() {
        SensorID id1 = new SensorID("Temperature");
        assertNotEquals(id1, null);
    }

    /**
     * Test for the toString method.
     */
    @Test
    void testToString() {
        SensorID sensorID = new SensorID("Temperature");
        assertEquals("Temperature", sensorID.toString());
    }

    /**
     * Test for the hashCode method.
     */
    @Test
    void testHashCode() {
        SensorID sensorID1 = new SensorID("TemperatureSensor");
        SensorID sensorID2 = new SensorID("TemperatureSensor");
        assertEquals(sensorID1.hashCode(), sensorID2.hashCode());
    }

    /**
     * Test for the hashCode method with different SensorID.
     */
    @Test
    void testHashCodeDifference() {
        SensorID sensorID1 = new SensorID("TemperatureSensor");
        SensorID sensorID2 = new SensorID("HumiditySensor");
        assertNotEquals(sensorID1.hashCode(), sensorID2.hashCode());
    }

}
