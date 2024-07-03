package smarthome.domain.valueobjects;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ActuatorTargetTest {

    /**
     * Test case to verify the successful creation of an ActuatorTarget object
     */
    @Test
    void successActuatorTargetCreation() {
        double targetValue = 2.2;
        ActuatorTarget actuatorTarget = new ActuatorTarget(targetValue);
        assertNotNull(actuatorTarget);
    }

    /**
     * Test the getTargetValue method of ActuatorTarget object
     */
    @Test
    void getTargetValue() {
        double targetValue = 2.2;
        ActuatorTarget actuatorTarget = new ActuatorTarget(targetValue);
        assertEquals(targetValue, actuatorTarget.getTargetValue());
    }
}