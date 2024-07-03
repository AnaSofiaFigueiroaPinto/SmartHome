package smarthome.util.serializers;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import smarthome.mapper.ActuatorFunctionalityDTO;

import java.io.IOException;

import static org.mockito.Mockito.verify;

/**
 * Test class for {@link ActuatorFunctionalityDTOKeySerializer}.
 */
class ActuatorFunctionalityDTOKeySerializerTest {

    /**
     * Tests the {@link ActuatorFunctionalityDTOKeySerializer#serialize(ActuatorFunctionalityDTO, JsonGenerator, SerializerProvider)} method.
     *
     * @throws IOException if an I/O error occurs during the test.
     */
    @Test
    void testSerialize() throws IOException {
        // Arrange
        ActuatorFunctionalityDTOKeySerializer serializer = new ActuatorFunctionalityDTOKeySerializer();
        JsonGenerator gen = Mockito.mock(JsonGenerator.class);
        SerializerProvider serializers = Mockito.mock(SerializerProvider.class);
        ActuatorFunctionalityDTO functionalityDTO = new ActuatorFunctionalityDTO("TestFunctionality");

        // Act
        serializer.serialize(functionalityDTO, gen, serializers);

        // Assert
        verify(gen).writeFieldName("TestFunctionality");
    }
}