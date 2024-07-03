package smarthome.util.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.context.annotation.Configuration;
import smarthome.mapper.DeviceDTO;

import java.io.IOException;

/**
 * A serializer for DeviceDTO objects that serializes them by their actuator functionality name.
 */
@Configuration
public class DeviceDTOKeySerializer extends JsonSerializer<DeviceDTO> {

    /**
     * Serializes an DeviceDTO object by writing its device name as a JSON field name.
     *
     * @param value       The DeviceDTO object to be serialized.
     * @param gen         The JSON generator used for writing JSON content.
     * @param serializers The serializer provider.
     * @throws IOException If an I/O error occurs while writing JSON content.
     */
    @Override
    public void serialize(DeviceDTO value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeFieldName(value.deviceName);
    }
}
