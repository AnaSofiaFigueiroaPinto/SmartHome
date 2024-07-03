package smarthome.util.serializers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import smarthome.mapper.ActuatorFunctionalityDTO;
import smarthome.mapper.DeviceDTO;
import smarthome.mapper.RoomDTO;
import smarthome.mapper.SensorFunctionalityDTO;

import java.nio.charset.StandardCharsets;

/**
 * Configuration class for Jackson ObjectMapper customization.
 */
@Configuration
public class JacksonConfiguration {

    /**
     * Configures and returns an ObjectMapper bean with custom serializers for DTO classes.
     *
     * @return The configured ObjectMapper instance.
     */
    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addKeySerializer(DeviceDTO.class, new DeviceDTOKeySerializer());
        module.addKeySerializer(RoomDTO.class, new RoomDTOKeySerializer());
        module.addKeySerializer(SensorFunctionalityDTO.class, new SensorFunctionalityDTOKeySerializer());
        module.addKeySerializer(ActuatorFunctionalityDTO.class, new ActuatorFunctionalityDTOKeySerializer());
        objectMapper.registerModule(module);
        return objectMapper;
    }

    @Bean
    public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setObjectMapper(objectMapper());
        // Set UTF-8 encoding
        converter.setDefaultCharset(StandardCharsets.UTF_8);
        return converter;
    }
}
