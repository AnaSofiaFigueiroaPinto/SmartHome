package smarthome.domain.valueobjects;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SensorFunctionalityIDTest {

    private static final String validId1 = "Temperature";
    private static final String validId2 = "Humidity";
    private static final String invalidIdEmpty = "";


    /**
     * Valid constructor test.
     * Verifies that the SensorFunctionalityID class can be instantiated with a valid ID.
     */

    @Test
    void validConstructor() {
        new SensorFunctionalityID(validId1);
    }

    /**
     * Invalid constructor test with an empty ID.
     * Verifies that an IllegalArgumentException is thrown when attempting to create a SensorFunctionalityID with an empty ID.
     */

    @Test
    void invalidConstructorEmptyId() {
        assertThrows(IllegalArgumentException.class, () -> new SensorFunctionalityID(invalidIdEmpty));
    }

    /**
     * Invalid constructor test with a blank ID.
     * Verifies that an IllegalArgumentException is thrown when attempting to create a SensorFunctionalityID with a blank ID.
     */
    @Test
    void invalidConstructorBlankId() {
        assertThrows(IllegalArgumentException.class, () -> new SensorFunctionalityID("  "));
    }

    /**
     * Invalid constructor test with a null ID.
     * Verifies that an IllegalArgumentException is thrown when attempting to create a SensorFunctionalityID with a null ID.
     */

    @Test
    void invalidConstructorNullId() {
        assertThrows(IllegalArgumentException.class, () -> new SensorFunctionalityID(null));
    }

    /**
     * Test for successful equality between SensorFunctionalityID instances.
     * Returns true if object is the same or have the same ID.
     */

    @Test
    void successEquals() {
        SensorFunctionalityID sensorFunctionalityID1 = new SensorFunctionalityID(validId1);
        SensorFunctionalityID sensorFunctionalityID2 = new SensorFunctionalityID(validId1);
        assertEquals(sensorFunctionalityID1, sensorFunctionalityID2);
        assertEquals(sensorFunctionalityID1, sensorFunctionalityID1);
    }

    /**
     * Test for unsuccessful equality between SensorFunctionalityID instances.
     * Verifies that when an instance of SensorFunctionalityID is null it retrieves false.
     */

    @Test
    void failEqualsObjectNull() {
        SensorFunctionalityID sensorFunctionalityID = new SensorFunctionalityID(validId1);
        assertFalse(sensorFunctionalityID.equals(null));
    }

    /**
     * Test for unsuccessful equality between SensorFunctionalityID instances.
     * Verifies that two SensorFunctionalityID instances with different IDs are not considered equal.
     */

    @Test
    void failEqualsDifferentSensorFunctionalityID() {
        SensorFunctionalityID sensorFunctionalityID1 = new SensorFunctionalityID(validId1);
        SensorFunctionalityID sensorFunctionalityID2 = new SensorFunctionalityID(validId2);
        assertNotEquals(sensorFunctionalityID1, sensorFunctionalityID2);
    }

    /**
     * Test for unsuccessful equality between SensorFunctionalityID instances.
     * Verifies that when an instance of SensorFunctionalityID is compared to an object of a different class it retrieves false.
     */
    @Test
    void failEqualsDifferentClass() {
        SensorFunctionalityID sensorFunctionalityID1 = new SensorFunctionalityID(validId1);
        Object obj = new Object();
        assertNotEquals(sensorFunctionalityID1, obj);
    }

    /**
     * Test for the toString method.
     * Verifies that the toString method returns the correct ID.
     */
    @Test
    void toStringTest() {
        SensorFunctionalityID sensorFunctionalityID = new SensorFunctionalityID(validId1);
        assertEquals(validId1, sensorFunctionalityID.toString());
    }
}





