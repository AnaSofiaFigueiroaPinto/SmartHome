package smarthome.domain.actuators;

import smarthome.domain.valueobjects.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class BlindSetterActuatorTest {
    private BlindSetterActuator blindSetterActuator;
    private ActuatorID actuatorName;
    private ActuatorProperties actuatorPropertiesDouble;
    private DeviceID deviceNameDouble;
    private ActuatorFunctionalityID functionalityIDDouble;

    /**
     * Sets up the BlindSetterActuator instance before each test method is executed
     */
    @BeforeEach
    void setUp() {
        actuatorName = mock(ActuatorID.class);
        when(actuatorName.toString()).thenReturn("blindSetterActuator");
        actuatorPropertiesDouble = mock(ActuatorProperties.class);
        deviceNameDouble = mock(DeviceID.class);
        functionalityIDDouble = mock(ActuatorFunctionalityID.class);
        blindSetterActuator = new BlindSetterActuator(actuatorName, functionalityIDDouble, actuatorPropertiesDouble, deviceNameDouble);
    }

    /**
     * Tests the retrieval of the actuator name
     */
    @Test
    void getValidActuatorName() {
        String expected = "blindSetterActuator";
        String result = blindSetterActuator.getActuatorName();
        assertEquals(expected, result);
    }

    /**
     * Test to verify that an {@link IllegalArgumentException} is thrown
     * a {@link BlindSetterActuator} object with a null actuator name
     */
    @Test
    void getNullActuatorName() {
        assertThrows(IllegalArgumentException.class, () -> new BlindSetterActuator(null, functionalityIDDouble, actuatorPropertiesDouble, deviceNameDouble));
    }

    /**
     * Test to verify that the {@code getDeviceName()} method returns the correct device name
     */
    @Test
    void getValidDeviceName() {
        when(deviceNameDouble.toString()).thenReturn("deviceName");
        assertEquals("deviceName", blindSetterActuator.getDeviceName().toString());
    }

    /**
     * Test to verify that an {@link IllegalArgumentException} is thrown
     * a {@link BlindSetterActuator} object with a null device name
     */
    @Test
    void getNullDeviceName() {
        assertThrows(IllegalArgumentException.class, () -> new BlindSetterActuator(actuatorName, functionalityIDDouble, actuatorPropertiesDouble, null));
    }

    /**
     * Tests the retrieval of the actuator functionality ID
     */
    @Test
    void getValidActuatorFunctionalityID() {
        when(functionalityIDDouble.toString()).thenReturn("functionalityID");
        assertEquals("functionalityID", blindSetterActuator.getActuatorFunctionalityID().toString());
    }

    /**
     * Test to verify that an {@link IllegalArgumentException} is thrown
     * a {@link BlindSetterActuator} object with a null actuator functionality id
     */
    @Test
    void getNullActuatorFunctionalityID() {
        assertThrows(IllegalArgumentException.class, () -> new BlindSetterActuator(actuatorName, null, actuatorPropertiesDouble, deviceNameDouble));
    }

    /**
     * Tests the retrieval of the actuator properties
     */
    @Test
    void getValidActuatorProperties() {
        assertEquals(actuatorPropertiesDouble, blindSetterActuator.getActuatorProperties());
    }


    /**
     * Test to verify if the BlindSetterActuator can successfully set the actuator specific value to the lower limit (0).
     */
    @Test
    void setActuatorSpecificValueLowerLimit() {
        assertTrue(blindSetterActuator.setActuatorSpecificValue(0));
    }

    /**
     * Test to verify if the BlindSetterActuator can successfully set the actuator specific value to the upper limit (100).
     */
    @Test
    void setActuatorSpecificValueUpperLimit() {
        assertTrue(blindSetterActuator.setActuatorSpecificValue(100));
    }

    /**
     * Tests setting an invalid specific value for the actuator, expecting an IllegalArgumentException
     */
    @Test
    void invalidSetActuatorSpecificValue() {
        assertFalse(blindSetterActuator.setActuatorSpecificValue(101));
    }

    /**
     * Tests setting an invalid specific value for the actuator, expecting an IllegalArgumentException
     */
    @Test
    void invalidSetActuatorSpecificValueNegative() {
        assertFalse(blindSetterActuator.setActuatorSpecificValue(-1));
    }

    /**
     * Test case to verify that the {@code identity()} method returns the correct actuator ID (actuatorName)
     */
    @Test
    void getIdentityOfActuator() {
        ActuatorID actuatorID = blindSetterActuator.identity();
        assertEquals(actuatorName, actuatorID);
    }

    /**
     * Checks successfully if Actuator instances are the same when they have the same ID (actuatorName)
     */
    @Test
    void checkIfActuatorIsSameAs() {
        BlindSetterActuator blindSetterActuator1 = new BlindSetterActuator(actuatorName, functionalityIDDouble, actuatorPropertiesDouble, deviceNameDouble);
        assertTrue(blindSetterActuator.isSameAs(blindSetterActuator1));
    }

    /**
     * Fails to check if Actuator instance is the same as Object instance due to Object not being an Actuator
     */
    @Test
    void failCheckIfActuatorIsSameAs() {
        BlindSetterActuator actuator = new BlindSetterActuator(actuatorName, functionalityIDDouble, actuatorPropertiesDouble, deviceNameDouble);
        Object object = new Object();
        assertFalse(actuator.isSameAs(object));
    }
}