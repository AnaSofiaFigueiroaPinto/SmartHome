package smarthome.util.serializers;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import smarthome.mapper.RoomDTO;

import java.io.IOException;

import static org.mockito.Mockito.verify;

/**
 * Test class for {@link RoomDTOKeySerializer}.
 */
class RoomDTOKeySerializerTest {

    /**
     * Tests the {@link RoomDTOKeySerializer#serialize(RoomDTO, JsonGenerator, SerializerProvider)} method.
     *
     * @throws IOException if an I/O error occurs during the test.
     */
    @Test
    void testSerialize() throws IOException {
        // Arrange
        RoomDTOKeySerializer serializer = new RoomDTOKeySerializer();
        JsonGenerator gen = Mockito.mock(JsonGenerator.class);
        SerializerProvider serializers = Mockito.mock(SerializerProvider.class);
        RoomDTO roomDTO = new RoomDTO("TestRoom");

        // Act
        serializer.serialize(roomDTO, gen, serializers);

        // Assert
        verify(gen).writeFieldName("TestRoom");
    }
}
