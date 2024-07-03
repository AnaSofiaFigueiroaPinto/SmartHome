package smarthome.domain.valueobjects;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ActuatorIDTest {
    private ActuatorID actuatorID1;
    private ActuatorID actuatorID2;
    private ActuatorID actuatorID3;


    /**
     * Sets up the ActuatorID objects for testing.
     */
    @BeforeEach
    void setUp() {
        actuatorID1 = new ActuatorID("SwitchActuator");
        actuatorID2 = new ActuatorID("IntegerSetterActuator");
        actuatorID3 = new ActuatorID("SwitchActuator");
    }

    /**
     * Tests the creation of a valid ActuatorID object
     */
    @Test
    void validActuatorID() {
        assertNotNull(actuatorID1);
    }

    /**
     * Tests the creation of an ActuatorID object with a null name
     *
     * @throws IllegalArgumentException if the actuator name is null
     */
    @Test
    void invalidActuatorIDNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            new ActuatorID(null);
        });
    }

    @Test
    void invalidActuatorIDEmpty() {
        assertThrows(IllegalArgumentException.class, () -> {
            new ActuatorID("");
        });
    }

    /**
     * Tests the creation of an ActuatorID object with a blank name
     *
     * @throws IllegalArgumentException if the actuator name is blank
     */
    @Test
    void invalidActuatorIDBlank() {
        assertThrows(IllegalArgumentException.class, () -> {
            new ActuatorID("  ");
        });
    }

    /**
     * Tests the equality of ActuatorID objects
     * Return true if the object is the same or have the same name.
     */

    @Test
    void actuatorIDIsEqualsSameObject() {
        assertEquals(actuatorID1, actuatorID3);
        assertEquals(actuatorID1, actuatorID1);
    }

    /**
     * Tests the equality of ActuatorID objects
     * Checks if two ActuatorID objects with different names are not considered equal
     */
    @Test
    void actuatorIDIsNotEqualsDifferentName() {
        assertNotEquals(actuatorID1, actuatorID2);
    }

    /**
     * Tests the equality of ActuatorID objects
     * Checks if ActuatorID object is different from an object of a different class.
     */
    @Test
    void actuatorIDIsNotEqualsDifferentClass() {
        Object obj = new Object();
        assertNotEquals(actuatorID1, obj);
    }

    /**
     * Tests the equality of ActuatorID objects
     * Checks if ActuatorID object is different from a null object.
     */
    @Test
    void actuatorIDIsNullEquals() {
        assertNotEquals(actuatorID1, null);
    }

    /**
     * Tests the equality of hash codes of ActuatorID objects
     * Checks if the hash code of two ActuatorID objects with the same name are equal
     */
    @Test
    void actuatorIDHasCodeIsTheSame() {

        assertEquals(actuatorID1.hashCode(), actuatorID3.hashCode());
    }

    /**
     * Tests the equality of hash codes of ActuatorID objects
     * Checks if the hash code of two ActuatorID objects with different names are not equal
     */
    @Test
    void actuatorIDHasCodeIsNotTheSame() {

        assertNotEquals(actuatorID1.hashCode(), actuatorID2.hashCode());
    }

    /**
     * Tests the string representation of ActuatorID objects
     * Checks if the string representation of an ActuatorID object matches the expected value
     */
    @Test
    void testToString() {
        assertEquals("SwitchActuator", actuatorID1.toString());
    }
}