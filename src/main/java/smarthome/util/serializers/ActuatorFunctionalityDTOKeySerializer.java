package smarthome.util.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.context.annotation.Configuration;
import smarthome.mapper.ActuatorFunctionalityDTO;

import java.io.IOException;

/**
 * A serializer for ActuatorFunctionalityDTO objects that serializes them by their actuator functionality name.
 */
@Configuration
public class ActuatorFunctionalityDTOKeySerializer extends JsonSerializer<ActuatorFunctionalityDTO> {

    /**
     * Serializes an ActuatorFunctionalityDTO object by writing its actuator functionality name as a JSON field name.
     *
     * @param value       The ActuatorFunctionalityDTO object to be serialized.
     * @param gen         The JSON generator used for writing JSON content.
     * @param serializers The serializer provider.
     * @throws IOException If an I/O error occurs while writing JSON content.
     */
    @Override
    public void serialize(ActuatorFunctionalityDTO value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeFieldName(value.actuatorFunctionalityName);
    }
}
