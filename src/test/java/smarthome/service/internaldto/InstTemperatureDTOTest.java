package smarthome.service.internaldto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Unit tests for the {@link InstTemperatureDTO} class.
 */
public class InstTemperatureDTOTest {

    /**
     * Tests the default constructor of {@link InstTemperatureDTO}.
     * Ensures that a new instance is created and is not null.
     */
    @Test
    public void testDefaultConstructor() {
        InstTemperatureDTO dto = new InstTemperatureDTO();
        assertNotNull(dto);
    }

    /**
     * Tests the parameterized constructor of {@link InstTemperatureDTO}.
     * Ensures that the fields are correctly initialized.
     */
    @Test
    public void testParameterizedConstructor() {
        double measurement = 23.5;
        String unit = "Celsius";
        String info = "Living room temperature";

        InstTemperatureDTO dto = new InstTemperatureDTO(measurement, unit, info);

        assertEquals(measurement, dto.measurement);
        assertEquals(unit, dto.unit);
        assertEquals(info, dto.info);
    }

    /**
     * Tests the JSON serialization of {@link InstTemperatureDTO}.
     * Ensures that the object is correctly serialized to a JSON string.
     *
     * @throws Exception if the serialization fails
     */
    @Test
    public void testJsonSerialization() throws Exception {
        double measurement = 23;
        String unit = "ºC";
        String info = "Living room temperature";

        InstTemperatureDTO dto = new InstTemperatureDTO(measurement, unit, info);
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = objectMapper.writeValueAsString(dto);

        String expectedJson = "{\"measurement\":23.0,\"unit\":\"ºC\",\"info\":\"Living room temperature\"}";
        assertEquals(expectedJson, jsonString);
    }

    /**
     * Tests the JSON deserialization of {@link InstTemperatureDTO}.
     * Ensures that the JSON string is correctly deserialized to an object.
     *
     * @throws Exception if the deserialization fails
     */
    @Test
    public void testJsonDeserialization() throws Exception {
        String jsonString = "{\"measurement\":23,\"unit\":\"ºC\",\"info\":\"Living room temperature\"}";

        ObjectMapper objectMapper = new ObjectMapper();
        InstTemperatureDTO dto = objectMapper.readValue(jsonString, InstTemperatureDTO.class);

        assertEquals(23, dto.measurement);
        assertEquals("ºC", dto.unit);
        assertEquals("Living room temperature", dto.info);
    }
}
