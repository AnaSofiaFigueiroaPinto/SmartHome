package smarthome.domain.sensorfunctionality;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import smarthome.domain.valueobjects.SensorFunctionalityID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {ImpFactorySensorFunctionalityTest.class})
class ImpFactorySensorFunctionalityTest {

    /**
     * Autowired instance of the {@code ImpFactorySensorFunctionality} factory interface implementation.
     */
    @InjectMocks
    private ImpFactorySensorFunctionality factory;

    /**
     * Test case for the {@code createSensorFunctionality} method of {@code ImpFactorySensorFunctionality} class.
     */
    @Test
    void successfulCreateSensorFunctionality_ValidID() {
        // Arrange
        SensorFunctionalityID validID = mock(SensorFunctionalityID.class);
        when(validID.toString()).thenReturn("TemperatureSensor");

        // Act
        SensorFunctionality functionality = factory.createSensorFunctionality(validID);

        // Assert
        assertNotNull(functionality);
        assertEquals("TemperatureSensor", functionality.identity().toString());
    }

    /**
     * Test case for the {@code createSensorFunctionality} method of {@code ImpFactorySensorFunctionality} class.
     */
    @Test
    void failedCreateSensorFunctionality_NullID() {
        // Act
        SensorFunctionality functionality = factory.createSensorFunctionality(null);

        // Assert
        assertNull(functionality);
    }
}