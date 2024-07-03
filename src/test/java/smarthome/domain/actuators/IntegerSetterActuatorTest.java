package smarthome.domain.actuators;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import smarthome.domain.valueobjects.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class IntegerSetterActuatorTest {
    private IntegerSetterActuator integerSetterActuator;
    private ActuatorID actuatorName;
    private ActuatorProperties actuatorPropertiesDouble;
    private RangeInt rangeInt;
    private DeviceID deviceNameDouble;
    private ActuatorFunctionalityID functionalityIDDouble;

    /**
     * Setting up a new IntSetterActuator under test
     */
    @BeforeEach
    void setUp() {
        actuatorName = mock(ActuatorID.class);
        when(actuatorName.toString()).thenReturn("integerSetterActuator");
        int upperLimit = 30;
        int lowerLimit = 20;
        rangeInt = mock(RangeInt.class);
        when(rangeInt.getLowerLimitInt()).thenReturn(lowerLimit);
        when(rangeInt.getUpperLimitInt()).thenReturn(upperLimit);

        actuatorPropertiesDouble = mock(ActuatorProperties.class);
        when(actuatorPropertiesDouble.getRangeInt()).thenReturn(rangeInt);

        deviceNameDouble = mock(DeviceID.class);
        functionalityIDDouble = mock(ActuatorFunctionalityID.class);

        integerSetterActuator = new IntegerSetterActuator(actuatorName, functionalityIDDouble, actuatorPropertiesDouble, deviceNameDouble);
    }

    /**
     * Test case to verify that an {@link IllegalArgumentException} is thrown when attempting to construct
     * a {@link IntegerSetterActuator} object with a null actuator name
     */
    @Test
    void getNullActuatorName() {
        assertThrows(IllegalArgumentException.class, () -> new IntegerSetterActuator(null, functionalityIDDouble, actuatorPropertiesDouble, deviceNameDouble));
    }

    /**
     * Test case to verify that an {@link IllegalArgumentException} is thrown when attempting to construct
     * a {@link IntegerSetterActuator} object with a null device name
     */
    @Test
    void getNullDeviceName() {
        assertThrows(IllegalArgumentException.class, () -> new IntegerSetterActuator(actuatorName, functionalityIDDouble, actuatorPropertiesDouble, null));
    }

    /**
     * Test case to verify that an {@link IllegalArgumentException} is thrown when attempting to construct
     * a {@link IntegerSetterActuator} object with a null functionality ID
     */
    @Test
    void getNullFunctionalityID() {
        assertThrows(IllegalArgumentException.class, () -> new IntegerSetterActuator(actuatorName, null, actuatorPropertiesDouble, deviceNameDouble));
    }

    /**
     * Test case to verify that the {@code getActuatorName()} method returns the correct actuator name
     */
    @Test
    void getValidActuatorName() {
        String expected = "integerSetterActuator";
        String result = integerSetterActuator.getActuatorName();
        assertEquals(expected, result);
    }

    /**
     * Test case to verify that the {@code getActuatorFunctionalityID()} method returns the correct actuator functionality ID
     */

    @Test
    void getValidActuatorFunctionalityID() {
        ActuatorFunctionalityID result = integerSetterActuator.getActuatorFunctionalityID();
        assertNotNull(result);
    }

    /**
     * Test case to verify that the {@code getActuatorProperties()} method returns the correct actuator properties
     */
    @Test
    void getActuatorProperties() {
        ActuatorProperties result = integerSetterActuator.getActuatorProperties();
        assertEquals(actuatorPropertiesDouble, result);
    }

    /**
     * Test case to verify that the {@code getDeviceName()} method returns the correct device name
     */

    @Test
    void getValidDeviceName() {
        when(deviceNameDouble.toString()).thenReturn("deviceName");
        assertEquals("deviceName", integerSetterActuator.getDeviceName().toString());
    }

    /**
     * Test cases to verify if the IntegerSetterActuator can successfully set integer values.
     */
    @Test
    void setIntegerSetterActuatorValid() {
        int specificInteger = 25;

        assertTrue(integerSetterActuator.setActuatorSpecificValue(specificInteger));
    }

    /**
     * Test case to verify if the IntegerSetterActuator can successfully set the upper boundary integer value.
     */
    @Test
    void setIntegerSetterActuatorValidUpperBoundary() {
        int specificInteger = 30;
        assertTrue(integerSetterActuator.setActuatorSpecificValue(specificInteger));
    }

    /**
     * Test case to verify if the IntegerSetterActuator can successfully set the lower boundary integer value.
     */
    @Test
    void setIntegerSetterActuatorValidLowerBoundary() {
        int specificInteger = 20;
        assertTrue(integerSetterActuator.setActuatorSpecificValue(specificInteger));
    }

    /**
     * Test case to verify if the IntegerSetterActuator fails to set an out-of-bounds (higher) integer value.
     */
    @Test
    void setIntegerSetterActuatorFailOutsideUpperLimit() {
        int specificInteger = 31;
        assertFalse(integerSetterActuator.setActuatorSpecificValue(specificInteger));
    }

    /**
     * Test case to verify if the IntegerSetterActuator fails to set an out-of-bounds (lower) integer value.
     */
    @Test
    void setIntegerSetterActuatorFailOutsideLowerLimit() {
        int specificInteger = 18;
        assertFalse(integerSetterActuator.setActuatorSpecificValue(specificInteger));
    }


    /**
     * Test case to verify that the {@code identity()} method returns the correct actuator ID (actuatorName)
     */
    @Test
    void getIdentityOfActuator() {
        ActuatorID actuatorID = integerSetterActuator.identity();
        assertEquals(actuatorName, actuatorID);
    }

    /**
     * Checks successfully if Actuator instances are the same when they have the same ID (actuatorName)
     */
    @Test
    void checkIfActuatorIsSameAs() {
        IntegerSetterActuator integerSetterActuator1 = new IntegerSetterActuator(actuatorName, functionalityIDDouble, actuatorPropertiesDouble, deviceNameDouble);
        assertTrue(integerSetterActuator.isSameAs(integerSetterActuator1));
    }

    /**
     * Fails to check if Actuator instance is the same as Object instance due to Object not being an Actuator
     */
    @Test
    void failCheckIfActuatorIsSameAs() {
        IntegerSetterActuator actuator = new IntegerSetterActuator(actuatorName, functionalityIDDouble, actuatorPropertiesDouble, deviceNameDouble);
        Object object = new Object();
        assertFalse(actuator.isSameAs(object));
    }


}