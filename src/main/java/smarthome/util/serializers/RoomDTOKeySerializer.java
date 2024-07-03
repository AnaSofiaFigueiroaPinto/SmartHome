package smarthome.util.serializers;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.context.annotation.Configuration;
import smarthome.mapper.RoomDTO;

import java.io.IOException;

/**
 * A serializer for RoomDTO objects that serializes them by their actuator functionality name.
 */
@Configuration
public class RoomDTOKeySerializer extends JsonSerializer<RoomDTO> {

    /**
     * Serializes an RoomDTO object by writing its room name as a JSON field name.
     *
     * @param value       The RoomDTO object to be serialized.
     * @param gen         The JSON generator used for writing JSON content.
     * @param serializers The serializer provider.
     * @throws IOException If an I/O error occurs while writing JSON content.
     */
    @Override
    public void serialize(RoomDTO value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeFieldName(value.roomName);
    }
}
