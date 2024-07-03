package smarthome.util.serializers;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import smarthome.mapper.DeviceDTO;

import java.io.IOException;

import static org.mockito.Mockito.verify;

/**
 * Test class for {@link DeviceDTOKeySerializer}.
 */
class DeviceDTOKeySerializerTest {

    /**
     * Tests the {@link DeviceDTOKeySerializer#serialize(DeviceDTO, JsonGenerator, SerializerProvider)} method.
     *
     * @throws IOException if an I/O error occurs during the test.
     */
    @Test
    void testSerialize() throws IOException {
        // Arrange
        DeviceDTOKeySerializer serializer = new DeviceDTOKeySerializer();
        JsonGenerator gen = Mockito.mock(JsonGenerator.class);
        SerializerProvider serializers = Mockito.mock(SerializerProvider.class);
        DeviceDTO deviceDTO = new DeviceDTO("TestDevice");

        // Act
        serializer.serialize(deviceDTO, gen, serializers);

        // Assert
        verify(gen).writeFieldName("TestDevice");
    }
}
