package smarthome.mapper;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test cases for the {@code ActuatorFunctionalityDTO} class.
 */
class ActuatorFunctionalityDTOTest {

    /**
     * Test case for the {@code ActuatorFunctionalityDTO} constructor.
     */
    @Test
    void validConstructorActuatorFunctionalityDTO() {
        ActuatorFunctionalityDTO actuatorFunctionalityDTO = new ActuatorFunctionalityDTO("switchActuator");
        assertEquals("switchActuator", actuatorFunctionalityDTO.actuatorFunctionalityName);
    }

    /**
     * Test case for the {@code ActuatorFunctionalityDTO} empty constructor.
     */
    @Test
    void validEmptyConstructorActuatorFunctionalityDTO() {
        ActuatorFunctionalityDTO actuatorFunctionalityDTO = new ActuatorFunctionalityDTO();
        assertNull(actuatorFunctionalityDTO.actuatorFunctionalityName);
    }

    /**
     * Test for the equals method with two ActuatorFunctionalityDTO objects that have the same attributes.
     */
    @Test
    void successEquals_SameObjects() {
        ActuatorFunctionalityDTO functionality1 = new ActuatorFunctionalityDTO("Function1");
        ActuatorFunctionalityDTO functionality2 = new ActuatorFunctionalityDTO("Function1");

        assertTrue(functionality1.equals(functionality2));
    }

    /**
     * Test for the equals method with the same ActuatorFunctionalityDTO object.
     */
    @Test
    void successEquals_Reflexivity() {
        ActuatorFunctionalityDTO functionality1 = new ActuatorFunctionalityDTO("Function1");
        assertTrue(functionality1.equals(functionality1));
    }

    /**
     * Test for the equals method with a null object.
     */
    @Test
    void successEquals_NullObject() {
        ActuatorFunctionalityDTO functionality1 = new ActuatorFunctionalityDTO("Function1");
        assertFalse(functionality1.equals(null));
    }

    /**
     * Test for the equals method with an object of a different class.
     */
    @Test
    void successEquals_DifferentClass() {
        ActuatorFunctionalityDTO functionality1 = new ActuatorFunctionalityDTO("Function1");
        assertFalse(functionality1.equals(new Object()));
    }

    /**
     * Test for the equals method with two ActuatorFunctionalityDTO objects with different functionality names.
     */
    @Test
    void successEquals_DifferentFunctionalityName() {
        ActuatorFunctionalityDTO functionality1 = new ActuatorFunctionalityDTO("Function1");
        ActuatorFunctionalityDTO functionality2 = new ActuatorFunctionalityDTO("Function2");

        assertFalse(functionality1.equals(functionality2));

    }

    /**
     * Test for the hashCode method with two ActuatorFunctionalityDTO objects that have the same attributes.
     */
    @Test
    void successHashCode() {
        ActuatorFunctionalityDTO functionality1 = new ActuatorFunctionalityDTO("Function1");
        ActuatorFunctionalityDTO functionality2 = new ActuatorFunctionalityDTO("Function1");

        assertEquals(functionality1.hashCode(), functionality2.hashCode());
    }

    /**
     * Test for the hashCode method with two ActuatorFunctionalityDTO objects with different functionality names.
     */
    @Test
    void successHashCode_DifferentFunctionalityName() {
        ActuatorFunctionalityDTO functionality1 = new ActuatorFunctionalityDTO("Function1");
        ActuatorFunctionalityDTO functionality2 = new ActuatorFunctionalityDTO("Function2");

        assertNotEquals(functionality1.hashCode(), functionality2.hashCode());
    }
}


