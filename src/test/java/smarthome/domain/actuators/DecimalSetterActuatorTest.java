package smarthome.domain.actuators;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import smarthome.domain.valueobjects.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class DecimalSetterActuatorTest {
    private DecimalSetterActuator decimalSetterActuator;
    private ActuatorID actuatorName;
    private ActuatorProperties actuatorPropertiesDouble;
    private ActuatorProperties actuatorPropertiesDoubleInvalid;
    private RangeDecimal rangeDecimal;
    private DeviceID deviceNameDouble;
    private ActuatorFunctionalityID functionalityIDDouble;

    /**
     * Setting up a new Double Setter Actuator under test
     */
    @BeforeEach
    void setUp() {
        actuatorName = mock(ActuatorID.class);
        when(actuatorName.toString()).thenReturn("decimalSetterActuator");
        double upperLimit = 30.0;
        double lowerLimit = 20.0;
        int precision = 1;


        rangeDecimal = mock(RangeDecimal.class);
        when(rangeDecimal.getLowerLimitDecimal()).thenReturn(lowerLimit);
        when(rangeDecimal.getUpperLimitDecimal()).thenReturn(upperLimit);
        when(rangeDecimal.getPrecision()).thenReturn(precision);

        actuatorPropertiesDouble = mock(ActuatorProperties.class);
        when(actuatorPropertiesDouble.getRangeDecimal()).thenReturn(rangeDecimal);

        deviceNameDouble = mock(DeviceID.class);
        functionalityIDDouble = mock(ActuatorFunctionalityID.class);

        decimalSetterActuator = new DecimalSetterActuator(actuatorName, functionalityIDDouble, actuatorPropertiesDouble, deviceNameDouble);
    }

    /**
     * Test case to verify that an {@link IllegalArgumentException} is thrown when attempting to construct
     * a {@link DecimalSetterActuator} object with a null actuator name
     */
    @Test
    void getNullActuatorName() {
        assertThrows(IllegalArgumentException.class, () -> new DecimalSetterActuator(null, functionalityIDDouble, actuatorPropertiesDouble, deviceNameDouble));
    }

    /**
     * Test case to verify that an {@link IllegalArgumentException} is thrown when attempting to construct
     * a {@link DecimalSetterActuator} object with a null device name
     */
    @Test
    void getNullDeviceName() {
        assertThrows(IllegalArgumentException.class, () -> new DecimalSetterActuator(actuatorName, functionalityIDDouble, actuatorPropertiesDouble, null));
    }

    /**
     * Test case to verify that an {@link IllegalArgumentException} is thrown when attempting to construct
     * a {@link DecimalSetterActuator} object with a null functionality id
     */
    @Test
    void getNullActuatorFunctionalityID() {
        assertThrows(IllegalArgumentException.class, () -> new DecimalSetterActuator(actuatorName, null, actuatorPropertiesDouble, deviceNameDouble));
    }

    /**
     * Tests the retrieval of the actuator properties
     */
    @Test
    void getActuatorProperties() {
        ActuatorProperties result = decimalSetterActuator.getActuatorProperties();
        assertEquals(actuatorPropertiesDouble, result);
    }

    /**
     * Test case to verify that the {@code getDeviceName()} method returns the correct device name
     */
    @Test
    void getValidDeviceName() {
        when(deviceNameDouble.toString()).thenReturn("deviceName");
        assertEquals("deviceName", decimalSetterActuator.getDeviceName().toString());
    }

    /**
     * Test case to verify that an {@link IllegalArgumentException} is thrown when attempting to construct
     * a {@link DecimalSetterActuator} object with a NAN lower limit
     */
    @Test
    void invalidDoubleSetterActuatorNANLowerLimit() {
        rangeDecimal = mock(RangeDecimal.class);
        when(rangeDecimal.getLowerLimitDecimal()).thenReturn(Double.NaN);
        actuatorPropertiesDoubleInvalid = mock(ActuatorProperties.class);
        when(actuatorPropertiesDoubleInvalid.getRangeDecimal()).thenReturn(rangeDecimal);
        assertThrows(IllegalArgumentException.class, () -> new DecimalSetterActuator(actuatorName, functionalityIDDouble, actuatorPropertiesDoubleInvalid, deviceNameDouble));
    }

    /**
     * Test case to verify that an {@link IllegalArgumentException} is thrown when attempting to construct
     * a {@link DecimalSetterActuator} object with a NAN upper limit
     */
    @Test
    void invalidDoubleSetterActuatorNANUpperLimit() {
        rangeDecimal = mock(RangeDecimal.class);
        when(rangeDecimal.getUpperLimitDecimal()).thenReturn(Double.NaN);
        actuatorPropertiesDoubleInvalid = mock(ActuatorProperties.class);
        when(actuatorPropertiesDoubleInvalid.getRangeDecimal()).thenReturn(rangeDecimal);
        assertThrows(IllegalArgumentException.class, () -> new DecimalSetterActuator(actuatorName, functionalityIDDouble, actuatorPropertiesDoubleInvalid, deviceNameDouble));
    }

    /**
     * Test case to verify that the {@code getActuatorName()} method returns the correct actuator name
     */
    @Test
    void getValidActuatorName() {
        String expected = "decimalSetterActuator";
        String result = decimalSetterActuator.getActuatorName();
        assertEquals(expected, result);
    }

    /**
     * Test case to verify that the {@code getActuatorFunctionalityID()} method returns the correct actuator functionality ID
     */
    @Test
    void getValidActuatorFunctionalityID() {
        when(functionalityIDDouble.toString()).thenReturn("functionalityID");
        ActuatorFunctionalityID result = decimalSetterActuator.getActuatorFunctionalityID();
        assertEquals("functionalityID", result.toString());
    }

    /**
     * Test case to verify if the actuator is able to set a decimal value successfully
     */
    @Test
    void setDoubleSetterActuatorValid() {
        double newValue = 25.57;
        double roundValue = 25.6;
        assertTrue(decimalSetterActuator.setDecimalValue(newValue));
        assertEquals(roundValue, decimalSetterActuator.getActuatorTarget().getTargetValue(), 0.01);
    }

    /**
     * Test case to verify if the actuator is able to set a decimal value successfully
     */
    @Test
    void setDoubleSetterActuatorValidUpperBoundary() {
        double newValue = 30.00;
        double roundValue = 30.0;
        assertTrue(decimalSetterActuator.setDecimalValue(newValue));
        assertEquals(roundValue, decimalSetterActuator.getActuatorTarget().getTargetValue(), 0.01);
    }

    /**
     * Test case to verify if the actuator is able to set a decimal value successfully
     */
    @Test
    void setDoubleSetterActuatorValidLowerBoundary() {
        double newValue = 20.00;
        double roundValue = 20.0;
        assertTrue(decimalSetterActuator.setDecimalValue(newValue));
        assertEquals(roundValue, decimalSetterActuator.getActuatorTarget().getTargetValue(), 0.01);
    }

    /**
     * Test case to verify if the DecimalSetterActuator fails to set an out-of-bounds (higher) integer value.
     */
    @Test
    void setDoubleSetterActuatorFailOutsideUpperLimit() {
        double newValue = 31.0;
        assertFalse(decimalSetterActuator.setDecimalValue(newValue));
    }

    /**
     * Test case to verify if the DecimalSetterActuator fails to set an out-of-bounds (lower) integer value.
     */
    @Test
    void setDoubleSetterActuatorFailOutsideLowerLimit() {
        double newValue = 19.0;
        assertFalse(decimalSetterActuator.setDecimalValue(newValue));
    }

    /**
     * Test case to verify that the {@code identity()} method returns the correct actuator ID (actuatorName)
     */
    @Test
    void getIdentityOfActuator() {
        ActuatorID actuatorID = decimalSetterActuator.identity();
        assertEquals(actuatorName, actuatorID);
    }

    /**
     * Checks successfully if Actuator instances are the same when they have the same ID (actuatorName)
     */
    @Test
    void checkIfActuatorIsSameAs() {
        DecimalSetterActuator decimalSetterActuator1 = new DecimalSetterActuator(actuatorName, functionalityIDDouble, actuatorPropertiesDouble, deviceNameDouble);
        assertTrue(decimalSetterActuator.isSameAs(decimalSetterActuator1));
    }

    /**
     * Fails to check if Actuator instance is the same as Object instance due to Object not being an Actuator
     */
    @Test
    void failCheckIfActuatorIsSameAs() {
        DecimalSetterActuator actuator = new DecimalSetterActuator(actuatorName, functionalityIDDouble, actuatorPropertiesDouble, deviceNameDouble);
        Object object = new Object();
        assertFalse(actuator.isSameAs(object));
    }


}