package smarthome.service.internaldto;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Unit tests for the {@link InstWindSpeedAndDirectionDTO} class.
 */
public class InstWindSpeedAndDirectionDTOTest {

    /**
     * Tests the default constructor of {@link InstWindSpeedAndDirectionDTO}.
     * Ensures that a new instance is created and is not null.
     */
    @Test
    public void testDefaultConstructor() {
        InstWindSpeedAndDirectionDTO dto = new InstWindSpeedAndDirectionDTO();
        assertNotNull(dto);
    }

    /**
     * Tests the parameterized constructor of {@link InstWindSpeedAndDirectionDTO}.
     * Ensures that the fields are correctly initialized.
     */
    @Test
    public void testParameterizedConstructor() {
        double measurement = 15.0;
        String unit = "km/h";
        String info = "North wind";

        InstWindSpeedAndDirectionDTO dto = new InstWindSpeedAndDirectionDTO(measurement, unit, info);

        assertEquals(measurement, dto.measurement);
        assertEquals(unit, dto.unit);
        assertEquals(info, dto.info);
    }

    /**
     * Tests the JSON serialization of {@link InstWindSpeedAndDirectionDTO}.
     * Ensures that the object is correctly serialized to a JSON string.
     *
     * @throws Exception if the serialization fails
     */
    @Test
    public void testJsonSerialization() throws Exception {
        double measurement = 15.0;
        String unit = "km/h";
        String info = "North wind";

        InstWindSpeedAndDirectionDTO dto = new InstWindSpeedAndDirectionDTO(measurement, unit, info);
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = objectMapper.writeValueAsString(dto);

        String expectedJson = "{\"measurement\":15.0,\"unit\":\"km/h\",\"info\":\"North wind\"}";
        assertEquals(expectedJson, jsonString);
    }

    /**
     * Tests the JSON deserialization of {@link InstWindSpeedAndDirectionDTO}.
     * Ensures that the JSON string is correctly deserialized to an object.
     *
     * @throws Exception if the deserialization fails
     */
    @Test
    public void testJsonDeserialization() throws Exception {
        String jsonString = "{\"measurement\":15.0,\"unit\":\"km/h\",\"info\":\"North wind\"}";

        ObjectMapper objectMapper = new ObjectMapper();
        InstWindSpeedAndDirectionDTO dto = objectMapper.readValue(jsonString, InstWindSpeedAndDirectionDTO.class);

        assertEquals(15.0, dto.measurement);
        assertEquals("km/h", dto.unit);
        assertEquals("North wind", dto.info);
    }
}
