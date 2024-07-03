package smarthome.domain.actuatorfunctionality;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import smarthome.domain.valueobjects.ActuatorFunctionalityID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

@SpringBootTest (classes = {ImpFactoryActuatorFunctionality.class})
class ImpFactoryActuatorFunctionalityTest {

    /**
     * Autowired instance of the {@code FactoryActuatorFunctionality} factory interface.
     */
    @InjectMocks
    ImpFactoryActuatorFunctionality factory;

    /**
     * Test the creation of an ActuatorFunctionality object with a valid ID.
     */
    @Test
    void testCreateActuatorFunctionality_ValidId() {
        // Arrange
        ActuatorFunctionalityID validId = mock(ActuatorFunctionalityID.class);

        // Act
        ActuatorFunctionality functionality = factory.createActuatorFunctionality(validId);

        // Assert
        assertNotNull(functionality);
    }

    /**
     * Test the creation of an ActuatorFunctionality object with a null ID.
     */
    @Test
    void testCreateActuatorFunctionalityNullId() {
        // Act
        ActuatorFunctionality functionality = factory.createActuatorFunctionality(null);

        // Assert
        assertNull(functionality);
    }

}