package smarthome.mapper;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test cases for the {@code ActuatorDTO} class.
 */
class ActuatorDTOTest {

    /**
     * Test case for the constructor with all actuator attributes, of the {@code ActuatorDTO} class.
     */
    @Test
    void validConstructorActuatorDTO() {
        String actuatorName = "SwitchActuator";
        String actuatorFunctionalityName = "Switch";
        String deviceName = "deviceID";
        int upperLimitInt = 0;
        int lowerLimitInt = 0;
        double upperLimitDecimal = 0;
        double lowerLimitDecimal = 0;
        int precision = 0;

        ActuatorDTO actuatorDTO = new ActuatorDTO(actuatorName, actuatorFunctionalityName, deviceName, upperLimitInt, lowerLimitInt, upperLimitDecimal, lowerLimitDecimal, precision);
        assertEquals("SwitchActuator", actuatorDTO.actuatorName);
        assertEquals("Switch", actuatorDTO.actuatorFunctionalityName);
        assertEquals("deviceID", actuatorDTO.deviceName);
    }

    /**
     * Tests if two ActuatorDTO objects with the same attributes are considered equal.
     */
    @Test
    void successEqualsSameObjects() {
        ActuatorDTO actuator1 = new ActuatorDTO("Actuator1", "Function1", "Device1", 2,1, 0, 0,0);
        ActuatorDTO actuator2 = new ActuatorDTO("Actuator1", "Function1", "Device1", 2, 1, 0, 0, 0);
        assertTrue(actuator1.equals(actuator2));
    }

    /**
     * Tests if an ActuatorDTO object is considered equal to itself.
     */
    @Test
    void successEqualsHimself() {
        ActuatorDTO actuator1 = new ActuatorDTO("Actuator1", "Function1", "Device1", 0, 0, 2, 1, 1);
        assertTrue(actuator1.equals(actuator1));
    }

    /**
     * Tests if two different ActuatorDTO objects with the same device name are considered equal.
     */
    @Test
    void successEqualsDifferentObjectSameDevice() {
        ActuatorDTO actuator1 = new ActuatorDTO("Actuator1", "Function1", "Device1", 2, 1, 0, 0, 0);
        ActuatorDTO actuator2 = new ActuatorDTO("Actuator1", "Function1", "Device1", 2, 1, 0, 0, 0);
        assertTrue(actuator2.equals(actuator1));
    }

    /**
     * Tests if an ActuatorDTO object is not considered equal to null.
     */
    @Test
    void successEqualsNullObject() {
        ActuatorDTO actuator1 = new ActuatorDTO("Actuator1", "Function1", "Device1", 1, 0, 0, 0, 0);
        assertFalse(actuator1.equals(null));
    }

    /**
     * Tests if an ActuatorDTO object is not considered equal to an object of a different class.
     */
    @Test
    void successEqualsDifferentClass() {
        ActuatorDTO actuator1 = new ActuatorDTO("Actuator1", "Function1", "Device1", 0, 0, 0, 0, 0);
        assertFalse(actuator1.equals(new Object()));
    }

    /**
     * Tests if two ActuatorDTO objects with different actuator names are not considered equal.
     */
    @Test
    void successEqualsDifferentActuatorName() {
        ActuatorDTO actuator1 = new ActuatorDTO("Actuator1", "Function1", "Device1",0, 0, 0, 0, 0);
        ActuatorDTO actuator2 = new ActuatorDTO("Actuator2", "Function1", "Device1", 0, 0, 0, 0, 0);
        assertFalse(actuator1.equals(actuator2));
    }

    /**
     * Tests if two ActuatorDTO objects with different functionality names are not considered equal.
     */
    @Test
    void successEqualsDifferentFunctionalityName() {
        ActuatorDTO actuator1 = new ActuatorDTO("Actuator1", "Function1", "Device1", 0, 0, 0, 0, 0);
        ActuatorDTO actuator2 = new ActuatorDTO("Actuator1", "Function2", "Device1", 0, 0, 0, 0, 0);
        assertFalse(actuator1.equals(actuator2));
    }

    /**
     * Tests if two ActuatorDTO objects with different device names are not considered equal.
     */
    @Test
    void successEqualsDifferentDeviceName() {
        ActuatorDTO actuator1 = new ActuatorDTO("Actuator1", "Function1", "Device1", 0, 0, 0, 0, 0);
        ActuatorDTO actuator2 = new ActuatorDTO("Actuator1", "Function1", "Device2", 0, 0, 0, 0, 0);
        assertFalse(actuator1.equals(actuator2));
    }

    /**
     * Tests if two ActuatorDTO objects with different upperLimitInt are not considered equal.
     */
    @Test
    void successEqualsDifferentUpperLimitInt() {
        ActuatorDTO actuator1 = new ActuatorDTO("Actuator1", "Function1", "Device1", 0, 0, 0, 0, 0);
        ActuatorDTO actuator2 = new ActuatorDTO("Actuator1", "Function1", "Device1", 1, 0, 0, 0, 0);
        assertFalse(actuator1.equals(actuator2));
    }

    /**
     * Tests if two ActuatorDTO objects with different lowerLimitInt are not considered equal.
     */
    @Test
    void successEqualsDifferenLowerLimitInt() {
        ActuatorDTO actuator1 = new ActuatorDTO("Actuator1", "Function1", "Device1", 2, 1, 0, 0, 0);
        ActuatorDTO actuator2 = new ActuatorDTO("Actuator1", "Function1", "Device1", 2, 0, 0, 0, 0);
        assertFalse(actuator1.equals(actuator2));
    }

    /**
     * Tests if two ActuatorDTO objects with different upperLimitDecimal are not considered equal.
     */
    @Test
    void successEqualsDifferentUpperLimitDecimal() {
        ActuatorDTO actuator1 = new ActuatorDTO("Actuator1", "Function1", "Device1", 0, 0, 1.0, 0, 0);
        ActuatorDTO actuator2 = new ActuatorDTO("Actuator1", "Function1", "Device1", 0, 0, 0, 0, 0);
        assertFalse(actuator1.equals(actuator2));
    }

    /**
     * Tests if two ActuatorDTO objects with different lowerLimitDecimal are not considered equal.
     */
    @Test
    void successEqualsDifferentLowerLimitDecimal() {
        ActuatorDTO actuator1 = new ActuatorDTO("Actuator1", "Function1", "Device1", 0, 0, 2.0, 1.0, 0);
        ActuatorDTO actuator2 = new ActuatorDTO("Actuator1", "Function1", "Device1", 0, 0, 2.0, 0, 0);
        assertFalse(actuator1.equals(actuator2));
    }

    /**
     * Tests if two ActuatorDTO objects with different precision are not considered equal.
     */
    @Test
    void successEqualsDifferentPrecision() {
        ActuatorDTO actuator1 = new ActuatorDTO("Actuator1", "Function1", "Device1", 0, 0, 0, 0, 1);
        ActuatorDTO actuator2 = new ActuatorDTO("Actuator1", "Function1", "Device1", 0, 0, 0, 0, 0);
        assertFalse(actuator1.equals(actuator2));
    }


    /**
     * Tests if the hash code of two ActuatorDTO objects with the same attributes are equal.
     */
    @Test
    void successHashCode() {
        ActuatorDTO actuator1 = new ActuatorDTO("Actuator1", "Function1", "Device1", 0, 0, 0, 0, 0);
        ActuatorDTO actuator2 = new ActuatorDTO("Actuator1", "Function1", "Device1", 0, 0, 0, 0, 0);
        assertEquals(actuator1.hashCode(), actuator2.hashCode());
    }

    /**
     * Tests if the hash code of two ActuatorDTO objects with different actuator names are different.
     */
    @Test
    void failHashCode() {
        ActuatorDTO actuator1 = new ActuatorDTO("Actuator1", "Function1", "Device1", 0, 0, 0, 0, 0);
        ActuatorDTO actuator2 = new ActuatorDTO("Actuator2", "Function1", "Device1", 0, 0, 0, 0, 0);
        assertNotEquals(actuator1.hashCode(), actuator2.hashCode());
    }

    /**
     * Test case that checks that the constructor with only the actuator name can be constructed successfully.
     */
    @Test
    void testSuccessConstructActuatorDTOWithActuatorName() {
        ActuatorDTO actuatorDTO = new ActuatorDTO("Actuator1");
        assertEquals("Actuator1", actuatorDTO.actuatorName);
    }
}