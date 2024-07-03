package smarthome.domain.valueobjects;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ActuatorFunctionalityIDTest {
    private ActuatorFunctionalityID actuatorFunctionalityID1;
    private ActuatorFunctionalityID actuatorFunctionalityID2;
    private ActuatorFunctionalityID actuatorFunctionalityID3;

    /**
     * Set up the actuatorFunctionalityID for the tests
     */
    @BeforeEach
    void setUp() {
        actuatorFunctionalityID1 = new ActuatorFunctionalityID("SwitchActuator");
        actuatorFunctionalityID2 = new ActuatorFunctionalityID("IntegerSetterActuator");
        actuatorFunctionalityID3 = new ActuatorFunctionalityID("SwitchActuator");

    }

    /**
     * Tests the constructor of the ActuatorFunctionalityID object with valid arguments
     */
    @Test
    void validActuatorFunctionalityID() {
        assertNotNull(actuatorFunctionalityID1);
    }

    /**
     * Tests the creation of an ActuatorFunctionalityID object with an empty name
     * @throws IllegalArgumentException actuatorFunctionalityID is empty
     */
    @Test
    void invalidActuatorFunctionalityIDBlank() {
        assertThrows(IllegalArgumentException.class, () -> {
            new ActuatorFunctionalityID("  ");
        });
    }

    /**
     * Tests the creation of an ActuatorFunctionalityID object with a blank name
     * @throws IllegalArgumentException actuatorFunctionalityID is blank
     */
    @Test
    void invalidActuatorFunctionalityIDEmpty() {
        assertThrows(IllegalArgumentException.class, () -> {
            new ActuatorFunctionalityID("");
        });
    }

    /**
     * Tests the creation of an ActuatorFunctionalityID object with a null name
     * @throws IllegalArgumentException actuatorFunctionalityID is null
     */
    @Test
    void invalidActuatorFunctionalityIDNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            new ActuatorFunctionalityID(null);
        });
    }

    /**
     * Tests the equality of ActuatorFunctionalityID objects.
     * Returns true if object is the same or have the same ID.
     */
    @Test
    void actuatorFunctionalityIDIsEquals() {
        assertEquals(actuatorFunctionalityID1, actuatorFunctionalityID3);
        assertEquals(actuatorFunctionalityID1, actuatorFunctionalityID1);
    }

    /**
     * Tests the equality of ActuatorFunctionalityID objects.
     * Checks if two ActuatorFunctionalityID objects with different names are considered not equal.
     */
    @Test
    void actuatorFunctionalityIDIsNotEqualsDifferentName() {
        assertNotEquals(actuatorFunctionalityID1, actuatorFunctionalityID2);
    }

    /**
     * Tests the equality of ActuatorFunctionalityID objects.
     * Checks if an instance of a different class is not equal to an ActuatorFunctionalityID object.
     */
    @Test
    void actuatorFunctionalityIDIsNotEqualsDifferentClass() {
        Object obj = new Object();
        assertNotEquals(actuatorFunctionalityID1, obj);
    }

    /**
     * Tests the equality of ActuatorFunctionalityID objects.
     * Checks if an ActuatorFunctionalityID object is not equal to a null object.
     */
    @Test
    void actuatorFunctionalityIDIsNotEqualsNullObject() {
        assertNotEquals(actuatorFunctionalityID1, null);
    }

    /**
     * Tests the equality of hash codes of ActuatorFunctionalityID objects
     * Checks if the hash code of two ActuatorFunctionalityID objects with the same name are equal
     */

    @Test
    void actuatorFunctionalityIDHashCodeIsTheSame() {
        assertEquals(actuatorFunctionalityID1.hashCode(), actuatorFunctionalityID3.hashCode());

    }

    /**
     * Tests the equality of hash codes of ActuatorFunctionalityID objects
     * Checks if the hash code of two ActuatorFunctionalityID objects with different names are not equal
     */
    @Test
    void actuatorFunctionalityIDHashCodeIsNotTheSame() {
        assertNotEquals(actuatorFunctionalityID1.hashCode(), actuatorFunctionalityID2.hashCode());
    }

    /**
     * Tests the toString method of the ActuatorFunctionalityID object
     */
    @Test
    void testToString() {
        assertEquals("SwitchActuator", actuatorFunctionalityID1.toString());
    }
}