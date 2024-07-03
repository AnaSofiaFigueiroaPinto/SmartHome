package smarthome.mapper;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AssemblerActuatorPropertiesTest {
    /**
     * Test to verify if the conversion of ActuatorPropertiesDTO in Domain was successful.
     */
    @Test
    void successCreateActuatorPropertiesEmpty() {
        ActuatorDTO actuatorDTO = mock(ActuatorDTO.class);

        AssemblerActuatorProperties assemblerActuatorProperties = new AssemblerActuatorProperties();
        assertNotNull(assemblerActuatorProperties.createActuatorPropertiesFromActuatorDTO(actuatorDTO));
    }

    /**
     * Test to verify failing attempt of creating ActuatorProperties when ActuatorDTO is null.
     */
    @Test
    void failCreateActuatorPropertiesNullActuator() {
            AssemblerActuatorProperties assemblerActuatorProperties = new AssemblerActuatorProperties();
            assertNotNull(assemblerActuatorProperties.createActuatorPropertiesFromActuatorDTO(new ActuatorDTO(null, null, null, 0, 0, 0, 0, 0)));
        }
}