package smarthome.util.serializers;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import smarthome.mapper.SensorFunctionalityDTO;

import java.io.IOException;

import static org.mockito.Mockito.verify;

/**
 * Test class for {@link SensorFunctionalityDTOKeySerializer}.
 */
class SensorFunctionalityDTOKeySerializerTest {

    /**
     * Tests the {@link SensorFunctionalityDTOKeySerializer#serialize(SensorFunctionalityDTO, JsonGenerator, SerializerProvider)} method.
     *
     * @throws IOException if an I/O error occurs during the test.
     */
    @Test
    void testSerialize() throws IOException {
        // Arrange
        SensorFunctionalityDTOKeySerializer serializer = new SensorFunctionalityDTOKeySerializer();
        JsonGenerator gen = Mockito.mock(JsonGenerator.class);
        SerializerProvider serializers = Mockito.mock(SerializerProvider.class);
        SensorFunctionalityDTO functionalityDTO = new SensorFunctionalityDTO("TestSensorFunctionality");

        // Act
        serializer.serialize(functionalityDTO, gen, serializers);

        // Assert
        verify(gen).writeFieldName("TestSensorFunctionality");
    }
}
