package smarthome.mapper;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test cases for the {@code SensorFunctionalityDTO} class.
 */
class SensorFunctionalityDTOTest {

    /**
     * Test method to verify the behavior of the getSensorFunctionality method in SensorFunctionalityDTO.
     * It ensures that the getSensorFunctionality method returns the correct functionality of the sensor.
     */

    @Test
    void validConstructorSensorFunctionalityDTO() {
        SensorFunctionalityDTO sensorFunctionalityDTO = new SensorFunctionalityDTO("Temperature");
        assertEquals("Temperature", sensorFunctionalityDTO.sensorFunctionalityName);
    }

    /**
     * Test to validate the constructor of the SensorFunctionalityDTO class.
     */
    @Test
    void validSecondConstructorSensorFunctionalityDTO() {
        SensorFunctionalityDTO sensorFunctionalityDTO = new SensorFunctionalityDTO();
        assertNull(sensorFunctionalityDTO.sensorFunctionalityName);
    }

    /**
     * Test method to verify the behavior of the equals method in SensorFunctionalityDTO.
     * It ensures that the equals method returns true when comparing two SensorFunctionalityDTO objects with the same attributes.
     */
    @Test
    void testEquals() {
        SensorFunctionalityDTO sensorFunctionalityDTO1 = new SensorFunctionalityDTO("Temperature");
        SensorFunctionalityDTO sensorFunctionalityDTO2 = new SensorFunctionalityDTO("Temperature");
        assertTrue(sensorFunctionalityDTO1.equals(sensorFunctionalityDTO2));
    }

    /**
     * Test method to verify the behavior of the equals method in SensorFunctionalityDTO.
     * It ensures that the equals method returns false when comparing two SensorFunctionalityDTO objects with different attributes.
     */
    @Test
    void testNotEquals() {
        SensorFunctionalityDTO sensorFunctionalityDTO1 = new SensorFunctionalityDTO("Temperature");
        SensorFunctionalityDTO sensorFunctionalityDTO2 = new SensorFunctionalityDTO("Humidity");
        assertFalse(sensorFunctionalityDTO1.equals(sensorFunctionalityDTO2));
    }

    /**
     * Test method to verify the behavior of the equals method in SensorFunctionalityDTO.
     * It ensures that the equals method returns false when comparing a SensorFunctionalityDTO object with a null object.
     */
    @Test
    void testNotEqualsNull() {
        SensorFunctionalityDTO sensorFunctionalityDTO = new SensorFunctionalityDTO("Temperature");
        assertFalse(sensorFunctionalityDTO.equals(null));
    }

    /**
     * Test method to verify the behavior of the equals method in SensorFunctionalityDTO.
     * It ensures that the equals method returns false when comparing a SensorFunctionalityDTO object with an object of a different class.
     */
    @Test
    void testNotEqualsDifferentClass() {
        SensorFunctionalityDTO sensorFunctionalityDTO = new SensorFunctionalityDTO("Temperature");
        assertFalse(sensorFunctionalityDTO.equals(new Object()));
    }

    /**
     * Test method to verify the behavior of the hashCode method in SensorFunctionalityDTO.
     * It ensures that the hashCode method returns the same hash code for two SensorFunctionalityDTO objects with the same attributes.
     */
    @Test
    void testHashCode() {
        SensorFunctionalityDTO sensorFunctionalityDTO1 = new SensorFunctionalityDTO("Temperature");
        SensorFunctionalityDTO sensorFunctionalityDTO2 = new SensorFunctionalityDTO("Temperature");
        assertEquals(sensorFunctionalityDTO1.hashCode(), sensorFunctionalityDTO2.hashCode());
    }

    /**
     * Test method to verify the behavior of the hashCode method in SensorFunctionalityDTO.
     * It ensures that the hashCode method returns different hash codes for two SensorFunctionalityDTO objects with different attributes.
     */
    @Test
    void testDifferentHashCode() {
        SensorFunctionalityDTO sensorFunctionalityDTO1 = new SensorFunctionalityDTO("Temperature");
        SensorFunctionalityDTO sensorFunctionalityDTO2 = new SensorFunctionalityDTO("Humidity");
        assertNotEquals(sensorFunctionalityDTO1.hashCode(), sensorFunctionalityDTO2.hashCode());
    }

    /**
     * Test method to verify the behavior of the hashCode method in SensorFunctionalityDTO.
     * It ensures that the hashCode method returns the same hash code for two SensorFunctionalityDTO objects with the same attributes.
     */
    @Test
    void testSameHashCode() {
        SensorFunctionalityDTO sensorFunctionalityDTO1 = new SensorFunctionalityDTO("Temperature");
        SensorFunctionalityDTO sensorFunctionalityDTO2 = new SensorFunctionalityDTO("Temperature");
        assertEquals(sensorFunctionalityDTO1.hashCode(), sensorFunctionalityDTO2.hashCode());
    }

    /**
     * Test method to verify the behavior of the hashCode method in SensorFunctionalityDTO.
     * It ensures that the hashCode method returns the same hash code for the same SensorFunctionalityDTO object.
     */
    @Test
    void testSameHashCodeForSameObject() {
        SensorFunctionalityDTO sensorFunctionalityDTO = new SensorFunctionalityDTO("Temperature");
        assertEquals(sensorFunctionalityDTO.hashCode(), sensorFunctionalityDTO.hashCode());
    }

    /**
     * Test method to verify the behavior of the equals method in SensorFunctionalityDTO.
     * It ensures that the equals method returns true when comparing a SensorFunctionalityDTO object with itself.
     */
    @Test
    void testEqualsSameObject() {
        SensorFunctionalityDTO sensorFunctionalityDTO = new SensorFunctionalityDTO("Temperature");
        assertTrue(sensorFunctionalityDTO.equals(sensorFunctionalityDTO));
    }


}