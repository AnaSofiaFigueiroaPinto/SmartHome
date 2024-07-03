package smarthome.service.internaldto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Unit tests for the {@link SunriseSunsetDTO} class.
 */
public class SunriseSunsetDTOTest {

    /**
     * Tests the default constructor of {@link SunriseSunsetDTO}.
     * Ensures that a new instance is created and is not null.
     */
    @Test
    public void testDefaultConstructor() {
        SunriseSunsetDTO dto = new SunriseSunsetDTO();
        assertNotNull(dto);
    }

    /**
     * Tests the parameterized constructor of {@link SunriseSunsetDTO}.
     * Ensures that the fields are correctly initialized.
     */
    @Test
    public void testParameterizedConstructor() {
        double measurement = 21.30;
        String unit = "h";
        String info = "Sunset";

        SunriseSunsetDTO dto = new SunriseSunsetDTO(measurement, unit, info);

        assertEquals(measurement, dto.measurement);
        assertEquals(unit, dto.unit);
        assertEquals(info, dto.info);
    }

    /**
     * Tests the JSON serialization of {@link SunriseSunsetDTO}.
     * Ensures that the object is correctly serialized to a JSON string.
     *
     * @throws Exception if the serialization fails
     */
    @Test
    public void testJsonSerialization() throws Exception {
        double measurement = 06.30;
        String unit = "h";
        String info = "Sunrise";

        SunriseSunsetDTO dto = new SunriseSunsetDTO(measurement, unit, info);
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = objectMapper.writeValueAsString(dto);

        String expectedJson = "{\"measurement\":6.3,\"unit\":\"h\",\"info\":\"Sunrise\"}";
        assertEquals(expectedJson, jsonString);
    }

    /**
     * Tests the JSON deserialization of {@link SunriseSunsetDTO}.
     * Ensures that the JSON string is correctly deserialized to an object.
     *
     * @throws Exception if the deserialization fails
     */
    @Test
    public void testJsonDeserialization() throws Exception {
        String jsonString = "{\"measurement\":6.3,\"unit\":\"h\",\"info\":\"Sunrise\"}";

        ObjectMapper objectMapper = new ObjectMapper();
        SunriseSunsetDTO dto = objectMapper.readValue(jsonString, SunriseSunsetDTO.class);

        assertEquals(06.30, dto.measurement);
        assertEquals("h", dto.unit);
        assertEquals("Sunrise", dto.info);
    }
}
