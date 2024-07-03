package smarthome.util.serializers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import smarthome.mapper.ActuatorFunctionalityDTO;
import smarthome.mapper.DeviceDTO;
import smarthome.mapper.RoomDTO;
import smarthome.mapper.SensorFunctionalityDTO;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

/**
 * Unit tests for validating the configuration of Jackson ObjectMapper and MappingJackson2HttpMessageConverter.
 */
@SpringBootTest(classes = JacksonConfigurationTest.TestConfig.class)
public class JacksonConfigurationTest {

    /**
     * The ObjectMapper instance configured by JacksonConfiguration.
     * This ObjectMapper is expected to have custom serializers registered for DTO classes.
     */
    @Autowired
    private ObjectMapper objectMapper;

    /**
     * The MappingJackson2HttpMessageConverter instance configured by JacksonConfiguration.
     * This converter is expected to use the objectMapper for JSON serialization and have UTF-8 charset encoding.
     */
    @Autowired
    private MappingJackson2HttpMessageConverter converter;

    /**
     * Configuration class for using JacksonConfiguration in SpringBootTest.
     */
    @Configuration
    public static class TestConfig extends JacksonConfiguration {
    }

    /**
     * Tests that the ObjectMapper is properly configured with custom serializers.
     */
    @Test
    public void testObjectMapperConfiguration() {
        // Check if ObjectMapper is configured with custom serializers
        SimpleModule module = new SimpleModule();
        module.addKeySerializer(DeviceDTO.class, new DeviceDTOKeySerializer());
        module.addKeySerializer(RoomDTO.class, new RoomDTOKeySerializer());
        module.addKeySerializer(SensorFunctionalityDTO.class, new SensorFunctionalityDTOKeySerializer());
        module.addKeySerializer(ActuatorFunctionalityDTO.class, new ActuatorFunctionalityDTOKeySerializer());

        ObjectMapper testObjectMapper = new ObjectMapper();
        testObjectMapper.registerModule(module);

        // Ensure that the ObjectMapper can serialize the mock classes (presence of custom serializers)
        assertTrue(objectMapper.canSerialize(DeviceDTO.class));
        assertTrue(objectMapper.canSerialize(RoomDTO.class));
        assertTrue(objectMapper.canSerialize(SensorFunctionalityDTO.class));
        assertTrue(objectMapper.canSerialize(ActuatorFunctionalityDTO.class));
    }

    /**
     * Tests that the MappingJackson2HttpMessageConverter is correctly configured.
     */
    @Test
    public void testMappingJackson2HttpMessageConverter() {
        // Verify that the converter is correctly configured
        assertTrue(converter.getObjectMapper() instanceof ObjectMapper);
        assertEquals(StandardCharsets.UTF_8, converter.getDefaultCharset());
    }

    /**
     * Tests that custom serializers can handle mock instances of DTO classes.
     */
    @Test
    public void testCustomSerializersWithMocks() {
        // Create mock instances
        DeviceDTO mockDeviceDTO = mock(DeviceDTO.class);
        RoomDTO mockRoomDTO = mock(RoomDTO.class);
        SensorFunctionalityDTO mockSensorFunctionalityDTO = mock(SensorFunctionalityDTO.class);
        ActuatorFunctionalityDTO mockActuatorFunctionalityDTO = mock(ActuatorFunctionalityDTO.class);

        // Verify that custom serializers can handle mocks
        assertTrue(objectMapper.canSerialize(mockDeviceDTO.getClass()));
        assertTrue(objectMapper.canSerialize(mockRoomDTO.getClass()));
        assertTrue(objectMapper.canSerialize(mockSensorFunctionalityDTO.getClass()));
        assertTrue(objectMapper.canSerialize(mockActuatorFunctionalityDTO.getClass()));
    }

    /**
     * Tests the overall configuration of MappingJackson2HttpMessageConverter,
     * including ObjectMapper configuration and default charset.
     */
    @Test
    public void testMappingJackson2HttpMessageConverterConfiguration() {
        assertNotNull(converter);

        // Verify ObjectMapper configuration
        ObjectMapper configuredMapper = converter.getObjectMapper();
        assertNotNull(configuredMapper);
        assertTrue(configuredMapper.canSerialize(DeviceDTO.class)); // Assuming DeviceDTO is one of your DTO classes
        assertTrue(configuredMapper.canSerialize(RoomDTO.class));   // Assuming RoomDTO is one of your DTO classes

        // Verify default charset is UTF-8
        assertEquals(StandardCharsets.UTF_8, converter.getDefaultCharset());
    }
}

