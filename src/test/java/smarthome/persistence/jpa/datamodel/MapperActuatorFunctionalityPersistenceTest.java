package smarthome.persistence.jpa.datamodel;

import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;
import smarthome.domain.actuatorfunctionality.ActuatorFunctionality;
import smarthome.domain.actuatorfunctionality.FactoryActuatorFunctionality;
import smarthome.domain.valueobjects.ActuatorFunctionalityID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Test class for the MapperActuatorFunctionalityPersistence class.
 */
class MapperActuatorFunctionalityPersistenceTest {

    /**
     * Test case for successfully convert of a String to an ActuatorFunctionality object.
     */
    @Test
    void successConvertToDomain() {
        FactoryActuatorFunctionality factoryDouble = mock(FactoryActuatorFunctionality.class);
        MapperActuatorFunctionalityPersistence mapper = new MapperActuatorFunctionalityPersistence(factoryDouble);

        ActuatorFunctionality actuatorFunctionalityDouble = mock(ActuatorFunctionality.class);

        try (MockedConstruction<ActuatorFunctionalityID> actuatorfunctIDMockedConstruction = mockConstruction(ActuatorFunctionalityID.class, (mock, context) -> {
            when(actuatorFunctionalityDouble.identity()).thenReturn(mock);
            when(actuatorFunctionalityDouble.identity().toString()).thenReturn("switch");
            when(factoryDouble.createActuatorFunctionality(mock)).thenReturn(actuatorFunctionalityDouble);
        })) {
            ActuatorFunctionality result = mapper.toDomain("switch");
            assertEquals("switch", result.identity().toString());
        }
    }

    /**
     * Test case for failing to convert a null String to an ActuatorFunctionality object.
     */
    @Test
    void failToConvertStringToDomainNullString() {
        FactoryActuatorFunctionality factoryDouble = mock(FactoryActuatorFunctionality.class);
        MapperActuatorFunctionalityPersistence mapper = new MapperActuatorFunctionalityPersistence(factoryDouble);

        try (MockedConstruction<ActuatorFunctionalityID> actuatorfunctIDMockedConstruction = mockConstruction(ActuatorFunctionalityID.class, (mock, context) -> {
        })) {
            ActuatorFunctionality result = mapper.toDomain(null);
            assertNull(result);
        }
    }
}