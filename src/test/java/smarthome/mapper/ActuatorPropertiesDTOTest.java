package smarthome.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.hateoas.Link;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test cases for the {@code ActuatorPropertiesDTO} class.
 */
class ActuatorPropertiesDTOTest {
    /**
     * Test case for constructing an ActuatorPropertiesDTO without parameters.
     */
    @Test
    void successCreateActuatorPropertiesDTOWithoutAttributes() {
        ActuatorPropertiesDTO propertiesDTO = new ActuatorPropertiesDTO();
        assertNotNull(propertiesDTO);
    }

    /**
     * Test case for constructing an ActuatorPropertiesDTO with integer range parameters.
     */
    @Test
    void successCreateActuatorPropertiesDTOWithIntegerRange() {
        int upperLimitInt = 2;
        int lowerLimitInt = 4;
        ActuatorPropertiesDTO propertiesDTO = new ActuatorPropertiesDTO(upperLimitInt, lowerLimitInt);
        assertNotNull(propertiesDTO);
    }

    /**
     * Test case for constructing an ActuatorPropertiesDTO with decimal range parameters.
     */
    @Test
    void successCreateActuatorPropertiesDTOWithDecimalRange() {
        double upperLimitDecimal = 1.5;
        double lowerLimitDecimal = 5.5;
        int precision = 1;
        ActuatorPropertiesDTO propertiesDTO = new ActuatorPropertiesDTO(upperLimitDecimal, lowerLimitDecimal, precision);
        assertNotNull(propertiesDTO);
    }

    /**
     * Tests if the equals method returns true when comparing an object with itself.
     */
    @Test
    void testEqualsSameObject() {
        ActuatorPropertiesDTO propertiesDTO = new ActuatorPropertiesDTO(1.5, 5.5, 1);
        assertEquals(propertiesDTO, propertiesDTO);
    }

    /**
     * Tests if the equals method returns false when comparing with an object of a different class.
     */
    @Test
    void testEqualsDifferentClass() {
        ActuatorPropertiesDTO propertiesDTO = new ActuatorPropertiesDTO(1.5, 5.5, 1);
        assertNotEquals(propertiesDTO, new Object());
    }

    /**
     * Tests if the equals method returns false when comparing with null.
     */
    @Test
    void testEqualsNull() {
        ActuatorPropertiesDTO propertiesDTO = new ActuatorPropertiesDTO(1.5, 5.5, 1);
        assertNotEquals(propertiesDTO, null);
    }

    /**
     * Tests if the equals method returns false when comparing objects with different attributes.
     */
    @Test
    void testEqualsDifferentAttributes() {
        ActuatorPropertiesDTO propertiesDTO1 = new ActuatorPropertiesDTO(1.5, 5.5, 1);
        ActuatorPropertiesDTO propertiesDTO2 = new ActuatorPropertiesDTO(1.5, 5.5, 2);
        assertNotEquals(propertiesDTO1, propertiesDTO2);
    }

    /**
     * Tests if the equals method returns true when comparing objects with the same attributes.
     */
    @Test
    void testEqualsSameAttributes() {
        ActuatorPropertiesDTO propertiesDTO1 = new ActuatorPropertiesDTO(1.5, 5.5, 1);
        ActuatorPropertiesDTO propertiesDTO2 = new ActuatorPropertiesDTO(1.5, 5.5, 1);
        assertEquals(propertiesDTO1, propertiesDTO2);
    }

    /**
     * Tests if the equals method returns false when comparing objects with different lower limit integer values.
     */
    @Test
    void testEqualsDifferentLowerLimitInt() {
        ActuatorPropertiesDTO propertiesDTO1 = new ActuatorPropertiesDTO(1.5, 5.5, 1);
        ActuatorPropertiesDTO propertiesDTO2 = new ActuatorPropertiesDTO(1.5, 5.5, 1);
        propertiesDTO2.lowerLimitInt = 10;
        assertFalse(propertiesDTO1.equals(propertiesDTO2));
    }

    /**
     * Tests if the equals method returns false when comparing objects with different upper limit decimal values.
     */
    @Test
    void testEqualsDifferentUpperLimitDecimal() {
        ActuatorPropertiesDTO propertiesDTO1 = new ActuatorPropertiesDTO(1.5, 5.5, 1);
        ActuatorPropertiesDTO propertiesDTO2 = new ActuatorPropertiesDTO(1.5, 5.5, 1);
        propertiesDTO2.upperLimitDecimal = 2.5;
        assertFalse(propertiesDTO1.equals(propertiesDTO2));
    }

    /**
     * Tests if the equals method returns false when comparing objects with different lower limit decimal values.
     */
    @Test
    void testEqualsDifferentLowerLimitDecimal() {
        ActuatorPropertiesDTO propertiesDTO1 = new ActuatorPropertiesDTO(1.5, 5.5, 1);
        ActuatorPropertiesDTO propertiesDTO2 = new ActuatorPropertiesDTO(1.5, 5.5, 1);
        propertiesDTO2.lowerLimitDecimal = 6.5;
        assertFalse(propertiesDTO1.equals(propertiesDTO2));
    }

    /**
     * Tests if the equals method returns false when comparing objects with different precision values.
     */
    @Test
    void testEqualsDifferentPrecision() {
        ActuatorPropertiesDTO propertiesDTO1 = new ActuatorPropertiesDTO(1.5, 5.5, 1);
        ActuatorPropertiesDTO propertiesDTO2 = new ActuatorPropertiesDTO(1.5, 5.5, 1);
        propertiesDTO2.precision = 2;
        assertFalse(propertiesDTO1.equals(propertiesDTO2));
    }

    /**
     * Tests if the equals method returns false when comparing objects with different upper limit integer values.
     */
    @Test
    void testEqualsDifferentUpperLimitInt() {
        ActuatorPropertiesDTO propertiesDTO1 = new ActuatorPropertiesDTO(1.5, 5.5, 1);
        ActuatorPropertiesDTO propertiesDTO2 = new ActuatorPropertiesDTO(1.5, 5.5, 1);
        propertiesDTO2.upperLimitInt = 10;
        assertFalse(propertiesDTO1.equals(propertiesDTO2));
    }

    /**
     * Tests if the hashCode method returns the same hash code for objects with the same attributes.
     */
    @Test
    void testHashCodeSameAttributes() {
        ActuatorPropertiesDTO propertiesDTO1 = new ActuatorPropertiesDTO(1.5, 5.5, 1);
        ActuatorPropertiesDTO propertiesDTO2 = new ActuatorPropertiesDTO(1.5, 5.5, 1);
        assertEquals(propertiesDTO1.hashCode(), propertiesDTO2.hashCode());
    }

    /**
     * Tests if the hashCode method returns different hash codes for objects with different attributes.
     */
    @Test
    void testHashCodeDifferentAttributes() {
        ActuatorPropertiesDTO propertiesDTO1 = new ActuatorPropertiesDTO(1.5, 5.5, 1);
        ActuatorPropertiesDTO propertiesDTO2 = new ActuatorPropertiesDTO(1.5, 5.5, 2);
        assertNotEquals(propertiesDTO1.hashCode(), propertiesDTO2.hashCode());
    }

}