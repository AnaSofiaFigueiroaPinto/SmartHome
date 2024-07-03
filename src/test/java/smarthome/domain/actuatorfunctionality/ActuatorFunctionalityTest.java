package smarthome.domain.actuatorfunctionality;

import smarthome.domain.valueobjects.ActuatorFunctionalityID;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ActuatorFunctionalityTest {

    private ActuatorFunctionalityID actuatorFunctionalityIDDouble = mock(ActuatorFunctionalityID.class);
    private ActuatorFunctionality actuatorFunctionality;
    private ActuatorFunctionality actuatorFunctionality1;

    /**
     * Test case to verify that the {@code ActuatorFunctionality} constructor returns an instance of {@code ActuatorFunctionality}
     */
    @Test
    void successCreateActuatorFunctionality() {
        actuatorFunctionality = new ActuatorFunctionality(actuatorFunctionalityIDDouble);
        assertNotNull(actuatorFunctionality);
    }

    /**
     * Test case to verify that the {@code identity()} method returns the correct actuatorFunctionalityID (id)
     */
    @Test
    void getIdentityOfActuatorFunctionality() {
        when(actuatorFunctionalityIDDouble.toString()).thenReturn("Switch");
        actuatorFunctionality = new ActuatorFunctionality(actuatorFunctionalityIDDouble);
        assertEquals(actuatorFunctionalityIDDouble, actuatorFunctionality.identity());
    }

    /**
     * Checks successfully if ActuatorFunctionality instances are the same when they have the same ID (id)
     */
    @Test
    void checkIfActuatorFunctionalityIsSameAs() {
        actuatorFunctionality = new ActuatorFunctionality(actuatorFunctionalityIDDouble);
        actuatorFunctionality1 = new ActuatorFunctionality(actuatorFunctionalityIDDouble);
        assertTrue(actuatorFunctionality.isSameAs(actuatorFunctionality1));
        assertTrue (actuatorFunctionality.equals(actuatorFunctionality));
    }

    /**
     * Fails to check if ActuatorFunctionality instances are the same when they have different IDs (id)
     */
    @Test
    void failCheckIfActuatorFunctionalityIsSameAsDifferentObject() {
        actuatorFunctionality = new ActuatorFunctionality(actuatorFunctionalityIDDouble);
        actuatorFunctionality1 = mock(ActuatorFunctionality.class);

        assertFalse(actuatorFunctionality.isSameAs(actuatorFunctionality1));
    }

    /**
     * Fails to check if ActuatorFunctionality instance is the same as Object instance due to Object not being an ActuatorFunctionality
     */
    @Test
    void failCheckIfActuatorFunctionalityIsSameAs() {
        ActuatorFunctionalityID actuatorFunctionalityID2 = mock(ActuatorFunctionalityID.class);
        ActuatorFunctionality actuatorFunctionality2 = new ActuatorFunctionality(actuatorFunctionalityID2);
        Object object = new Object();
        assertFalse(actuatorFunctionality2.isSameAs(object));
    }

}