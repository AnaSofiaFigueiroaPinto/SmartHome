package smarthome.domain.actuators;

import smarthome.domain.valueobjects.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties.ShowSummary.OFF;


class SwitchActuatorTest {

    private SwitchActuator switchActuator;
    private ActuatorID actuatorName;
    private ActuatorProperties actuatorPropertiesDouble;
    private DeviceID deviceNameDouble;
    private ActuatorFunctionalityID functionalityIDDouble;

    /**
     * Setting up the SwitchActuator to use in tests
     */
    @BeforeEach
    void setUp() {
        actuatorName = mock(ActuatorID.class);
        when(actuatorName.toString()).thenReturn("switchActuator");
        actuatorPropertiesDouble = mock(ActuatorProperties.class);
        deviceNameDouble = mock(DeviceID.class);
        functionalityIDDouble = mock(ActuatorFunctionalityID.class);
        switchActuator = new SwitchActuator(actuatorName, functionalityIDDouble, actuatorPropertiesDouble, deviceNameDouble);
    }

    /**
     * Test case to verify that the {@code getActuatorName()} method returns the correct actuator name
     */
    @Test
    void getValidActuatorName() {
        String expected = "switchActuator";
        String result = switchActuator.getActuatorName();
        assertEquals(expected, result);
    }

    /**
     * Test case to verify that an {@link IllegalArgumentException} is thrown when attempting to construct
     * a {@link SwitchActuator} object with a null actuator name
     */
    @Test
    void getNullActuatorName() {
        assertThrows(IllegalArgumentException.class, () -> new SwitchActuator(null, functionalityIDDouble, actuatorPropertiesDouble, deviceNameDouble));
    }

    /**
     * Test case to verify that an {@link IllegalArgumentException} is thrown when attempting to construct
     * a {@link SwitchActuator} object with a null device name
     */
    @Test
    void getNullDeviceName() {
        assertThrows(IllegalArgumentException.class, () -> new SwitchActuator(actuatorName, functionalityIDDouble, actuatorPropertiesDouble, null));
    }

    /**
     * Test case to verify that an {@link IllegalArgumentException} is thrown when attempting to construct
     * a {@link SwitchActuator} object with a null functionality if
     */
    @Test
    void getNullFunctionalityID() {
        assertThrows(IllegalArgumentException.class, () -> new SwitchActuator(actuatorName, null, actuatorPropertiesDouble, deviceNameDouble));
    }

    /**
     * Test case to verify that the {@code getActuatorFunctionalityID()} method returns the correct actuator functionality ID
     */
    @Test
    void getValidActuatorFunctionalityID() {
        assertNotNull(switchActuator.getActuatorFunctionalityID());
    }

    /**
     * Test case to verify that the {@code getActuatorProperties()} method returns the correct actuator properties
     */
    @Test
    void getActuatorProperties() {
        ActuatorProperties result = switchActuator.getActuatorProperties();
        assertEquals(actuatorPropertiesDouble, result);
    }

    /**
     * Test case to verify that the {@code getDeviceName()} method returns the correct device name
     */
    @Test
    void getValidDeviceName() {
        when(deviceNameDouble.toString()).thenReturn("deviceName");
        assertEquals("deviceName", switchActuator.getDeviceName().toString());
    }

    /**
     * Test case to verify that the {@code setActuatorSpecificValue()} method returns the correct actuator value
     */
    @Test
    void setActuatorSpecificValueOn() {
        assertTrue(switchActuator.setActuatorSpecificValue(1));
        assertEquals(SwitchActuatorStatus.ON, switchActuator.getStatus());
    }

    /**
     * Test case to verify that the {@code setActuatorSpecificValue()} method returns the correct actuator value
     */
    @Test
    void setActuatorSpecificValueOff() {
        switchActuator.setActuatorSpecificValue(1);
        assertTrue(switchActuator.setActuatorSpecificValue(0));
        assertEquals(SwitchActuatorStatus.OFF, switchActuator.getStatus());
    }

    /**
     * Test case to verify that the {@code setActuatorSpecificValue()} method returns invalid actuator value
     */
    @Test
    void setActuatorSpecificValueInvalid() {
        assertFalse(switchActuator.setActuatorSpecificValue(2));
    }

    /**
     * Test case to verify that the {@code identity()} method returns the correct actuator ID (actuatorName)
     */
    @Test
    void getIdentityOfActuator() {
        ActuatorID actuatorID = switchActuator.identity();
        assertEquals(actuatorName, actuatorID);
    }

    /**
     * Checks successfully if Actuator instances are the same when they have the same ID (actuatorName)
     */
    @Test
    void checkIfActuatorIsSameAs() {
        SwitchActuator switchActuator1 = new SwitchActuator(actuatorName, functionalityIDDouble, actuatorPropertiesDouble, deviceNameDouble);
        assertTrue(switchActuator.isSameAs(switchActuator1));
    }

    /**
     * Fails to check if Actuator instance is the same as Object instance due to Object not being an Actuator
     */
    @Test
    void failCheckIfActuatorIsSameAs() {
        SwitchActuator actuator = new SwitchActuator(actuatorName, functionalityIDDouble, actuatorPropertiesDouble, deviceNameDouble);
        Object object = new Object();
        assertFalse(actuator.isSameAs(object));
    }
}